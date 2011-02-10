package com.createsend.util;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class UserAgentFilter extends ClientFilter {
    
    private static String userAgent;
    
    static {
        userAgent = String.format(
                        "createsend-java v%s. %s v%s. %s v%s", 
                        Configuration.Current.getWrapperVersion(), 
                        System.getProperty("java.runtime.name", "Unknown runtime"),
                        System.getProperty("java.runtime.version", "Unknown version"),
                        System.getProperty("os.name", "Unknown OS"),
                        System.getProperty("os.version", "Unknown version"));
        
    }
    
    @Override
    public ClientResponse handle(ClientRequest cr) throws ClientHandlerException {
        cr.getHeaders().add("User-Agent", userAgent);
        return getNext().handle(cr);
    }

}
