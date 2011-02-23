package com.createsend.util.jersey;

import com.createsend.util.Configuration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

public abstract class ResourceFactory {
    public WebResource getResource(Client client, String... pathElements) {
        WebResource resource = client.resource(Configuration.Current.getApiEndpoint());
        resource.setProperty(ClientConfig.PROPERTY_CHUNKED_ENCODING_SIZE, 64 * 1024);
        resource.setProperty(com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING, "true");

        for(String pathElement : pathElements) {
            resource = resource.path(pathElement);
        }        
        
        return resource;
    }
}
