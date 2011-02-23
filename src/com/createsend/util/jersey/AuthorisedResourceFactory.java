package com.createsend.util.jersey;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class AuthorisedResourceFactory extends ResourceFactory {  
    private HTTPBasicAuthFilter authFilter;
    
    public AuthorisedResourceFactory(String username, String password) {
        authFilter = new HTTPBasicAuthFilter(username, password);
    }

    /**
     * Creates a WebResource instance configured for the Campaign Monitor API, including an 
     * Authorization header with the API Key values specified in the current 
     * {@link com.createsend.util.Configuration}
     * @param pathElements The path to use when configuring the WebResource
     * @return A configured WebResource
     */
    @Override
    public WebResource getResource(Client client, String... pathElements) {
        WebResource resource = super.getResource(client, pathElements);
        resource.addFilter(authFilter);
             
        return resource;
    }
}