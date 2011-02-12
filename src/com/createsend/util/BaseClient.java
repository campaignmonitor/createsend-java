package com.createsend.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.ws.rs.core.MediaType;

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


public abstract class BaseClient {
    
    private static Client client;
        
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
    
    protected <T> PagedResult<T> getPagedResult(String... pathElements) throws CreateSendException {
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
            
            return resource.get(new GenericType<PagedResult<T>>(genericReturnType));
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
