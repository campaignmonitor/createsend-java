package com.createsend.util;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.createsend.models.ApiErrorResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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
            cc.getClasses().add(JacksonJsonProvider.class); 
            
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
