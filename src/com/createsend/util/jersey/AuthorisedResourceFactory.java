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