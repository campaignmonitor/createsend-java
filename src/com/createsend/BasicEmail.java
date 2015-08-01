/**
 * Copyright (c) 2015 Richard Bremner
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
package com.createsend;

import com.createsend.models.transactional.request.BasicEmailRequest;
import com.createsend.models.transactional.response.BasicEmailGroup;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/transactional/basicEmail/" target="_blank">
 * Campaign</a> resources in the Campaign Monitor API
 */
public class BasicEmail extends CreateSendBase {

    /**
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public BasicEmail(AuthenticationDetails auth) {
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * List groups.
     * @return Array of BasicEmailGroup.
     * @throws CreateSendException
     */
    public BasicEmailGroup[] list() throws CreateSendException {
        return list(null);
    }

    /**
     * List BasicEmail groups for a specific Client.
     * @param clientID The ClientID to filter.
     * @return Array of BasicEmailGroup.
     * @throws CreateSendException
     */
    public BasicEmailGroup[] list(String clientID) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        return jerseyClient.get(BasicEmailGroup[].class, queryString, "transactional", "basicEmail", "groups");
    }

    /**
     * Send a BasicEmail.
     * @param basicEmailRequest The BasicEmailRequest to send.
     * @throws CreateSendException
     */
    public void send(BasicEmailRequest basicEmailRequest) throws CreateSendException {
        send(basicEmailRequest, null);
    }

    /**
     * Send a BasicEmail.
     * @param basicEmailRequest The BasicEmailRequest to send.
     * @param clientID The ClientID to filter.
     * @throws CreateSendException
     */
    public void send(BasicEmailRequest basicEmailRequest, String clientID) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        jerseyClient.post(String.class, queryString, basicEmailRequest, "transactional", "basicEmail", "send");
    }
}
