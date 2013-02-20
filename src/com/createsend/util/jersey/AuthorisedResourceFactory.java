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
    private HTTPBasicAuthFilter apiKeyFilter;
    private OAuth2BearerTokenFilter oauthTokenFilter;

    public AuthorisedResourceFactory(String accessToken) {
        oauthTokenFilter = new OAuth2BearerTokenFilter(accessToken);
    }

    public AuthorisedResourceFactory(String username, String password) {
        apiKeyFilter = new HTTPBasicAuthFilter(username, password);
    }

    @Override
    public WebResource getResource(Client client, String... pathElements) {
        WebResource resource = super.getResource(client, pathElements);
        if (apiKeyFilter != null)
	        resource.addFilter(apiKeyFilter);
        if (oauthTokenFilter != null)
        	resource.addFilter(oauthTokenFilter);
        return resource;
    }
}