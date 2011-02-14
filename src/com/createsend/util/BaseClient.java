/**
 * Copyright (c) 2011 Toby Brain
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.ApiErrorResponse;
import com.createsend.models.PagedResult;
import com.createsend.util.exceptions.BadRequestException;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.exceptions.CreateSendHttpException;
import com.createsend.util.exceptions.NotFoundException;
import com.createsend.util.exceptions.ServerErrorException;
import com.createsend.util.exceptions.UnauthorisedException;
import com.createsend.util.jersey.JsonProvider;
import com.createsend.util.jersey.UserAgentFilter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * An abstract base class for API calls. This class provides a basic facade to the Jersey API,
 * simplifying the creation of WebResources and standard access to the Campaign Monitor API
 */
public abstract class BaseClient {
    
    /**
     * As per Jersey docs the creation of a Client is expensive. We cache the client
     */
    private static Client client;
        
    /**
     * Performs a HTTP GET on the route specified by the pathElements deserialising the 
     * result to an instance of klass.
     * @param <T> The type of model expected from the API call.
     * @param klass The class of the model to deserialise. 
     * @param pathElements The path of the API resource to access
     * @return The model returned from the API call
     * @throws CreateSendException If the API call results in a HTTP status code >= 400
     */
    protected <T> T get(Class<T> klass, String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        
        try {
            return fixStringResult(klass, resource.get(klass));
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
    /*
    @SuppressWarnings("unchecked")
    protected <T> Collection<T> getCollection(String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        
        try { 
            return Collections.unmodifiableCollection(
                (Collection<T>) resource.get(new GenericType<T>(Collection.class)));
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }*/

    
    /**
     * Performs a HTTP GET on the route specified attempting to deserialise the
     * result to a paged result of the given type.
     * @param <T> The type of paged result data expected from the API call. 
     * @param pathElements The path of the API resource to access
     * @return The model returned from the API call
     * @throws CreateSendException If the API call results in a HTTP status code >= 400
     */
    protected <T> PagedResult<T> getPagedResult(MultivaluedMap<String, String> queryString,
        String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        
        try {            
            String callingMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            ParameterizedType genericReturnType = null;
            
            for(Method method : this.getClass().getMethods()) {
                if(method.getName().equals(callingMethodName)) {
                    genericReturnType = (ParameterizedType)method.getGenericReturnType();
                    break;
                }
            }            
            
            return resource.queryParams(queryString).get(new GenericType<PagedResult<T>>(genericReturnType));
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        
        return null;
    }
        
    protected <T> T post(Class<T> klass, Object entity, String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        
        try { 
            return fixStringResult(klass, resource.
                type(MediaType.APPLICATION_JSON_TYPE).
                entity(entity).
                post(klass));
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
        
    protected void put(Object entity, String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        
        try { 
            resource.
                type(MediaType.APPLICATION_JSON_TYPE).
                put(entity);
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
    
    protected void delete(String... pathElements) throws CreateSendException {
        WebResource resource = getAPIResourceWithAuth(pathElements);
        try { 
            resource.delete();
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
    
    protected MultivaluedMap<String, String> getPagingParams(Integer page, 
        Integer pageSize, String orderField, String orderDirection) {
        MultivaluedMap<String, String> query = new MultivaluedMapImpl();
        
        if(page != null) {
            query.add("page", page.toString());
        }
        
        if(pageSize != null) {
            query.add("pagesize", pageSize.toString());
        }
        
        if(orderField != null) {
            query.add("orderfield", orderField); 
        }
        
        if(orderDirection != null) {
            query.add("orderdirection", orderDirection);
        }
        
        return query.isEmpty() ? null : query;
    }
        
    protected CreateSendException handleErrorResponse(UniformInterfaceException ue) {
        ClientResponse response = ue.getResponse();
        ApiErrorResponse apiResponse;
        
        try { 
            apiResponse = response.getEntity(ApiErrorResponse.class);
        } catch (Throwable t) { 
            apiResponse = null;
        }
        
        switch(response.getClientResponseStatus()) {
            case BAD_REQUEST:
                return new BadRequestException(apiResponse.Code, apiResponse.Message);
            case INTERNAL_SERVER_ERROR:
                return new ServerErrorException(apiResponse.Code, apiResponse.Message);
            case NOT_FOUND:
                return new NotFoundException(apiResponse.Code, apiResponse.Message);
            case UNAUTHORIZED:
                return new UnauthorisedException(apiResponse.Code, apiResponse.Message);
            default:
                return new CreateSendHttpException(response.getClientResponseStatus());
        }
    }
    
    protected WebResource getAPIResource() {
        if(client == null) {
            ClientConfig cc = new DefaultClientConfig(); 
            cc.getClasses().add(JsonProvider.class); 
            
            Map<String, Object> properties = cc.getProperties();
            properties.put(ClientConfig.PROPERTY_CHUNKED_ENCODING_SIZE, 64 * 1024);
            properties.put(com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING, "true");
            
            client = Client.create(cc);
            client.setFollowRedirects(false);
            
            if(Configuration.Current.isLoggingEnabled()) {
                client.addFilter(new LoggingFilter(System.out));
            }

            client.addFilter(new UserAgentFilter());
        }

        WebResource resource = client.resource(Configuration.Current.getApiEndpoint());
        resource.setProperty(ClientConfig.PROPERTY_CHUNKED_ENCODING_SIZE, 64 * 1024);
        resource.setProperty(com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        return resource;
    }
    
    protected WebResource getAPIResourceWithAuth(String... pathElements) {
        WebResource resource = getAPIResource();
        resource.addFilter(new HTTPBasicAuthFilter(Configuration.Current.getApiKey(), "x"));
                
        for(String pathElement : pathElements) {
            resource = resource.path(pathElement);
        }        
        
        return resource;
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T fixStringResult(Class<T> klass, T result) {
        if(klass == String.class) { 
            String strResult = (String)result;
            if(strResult.startsWith("\"")) { 
                strResult = strResult.substring(1);
            }
            
            if(strResult.endsWith("\"")) {
                strResult = strResult.substring(0, strResult.length() - 1);
            }
            
            return (T)strResult;
        }
        
        return result;
    }
}
