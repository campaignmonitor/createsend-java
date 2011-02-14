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
package com.createsend.util.jersey;

import com.createsend.util.Configuration;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

/**
 * A ClientFilter to set the Java wrappers User-Agent
 */
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
