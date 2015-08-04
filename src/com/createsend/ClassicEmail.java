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

import com.createsend.models.transactional.request.ClassicEmailRequest;
import com.createsend.models.transactional.response.ClassicEmailGroup;
import com.createsend.models.transactional.response.MessageSent;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/transactional/classicEmail/" target="_blank">
 * Transactional Classic Email</a> resources in the Campaign Monitor API
 */
public class ClassicEmail extends CreateSendBase {

    /**
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public ClassicEmail(AuthenticationDetails auth) {
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * List groups.
     * @return Array of ClassicEmailGroup.
     * @throws CreateSendException
     */
    public ClassicEmailGroup[] list() throws CreateSendException {
        return list(null);
    }

    /**
     * List ClassicEmailGroup for a specific Client.
     * @param clientID The ClientID to filter.
     * @return Array of ClassicEmailGroup.
     * @throws CreateSendException
     */
    public ClassicEmailGroup[] list(String clientID) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        return jerseyClient.get(ClassicEmailGroup[].class, queryString, "transactional", "classicEmail", "groups");
    }

    /**
     * Send a ClassicEmail.
     * @param classicEmailRequest The ClassicEmailRequest to send.
     * @throws CreateSendException
     */
    public void send(ClassicEmailRequest classicEmailRequest) throws CreateSendException {
        send(classicEmailRequest, null);
    }

    /**
     * Send a ClassicEmail.
     * @param classicEmailRequest The ClassicEmailRequest to send.
     * @param clientID The ClientID to filter.
     * @return Message sent acknowledgement.
     * @throws CreateSendException
     */
    public MessageSent[] send(ClassicEmailRequest classicEmailRequest, String clientID) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        return jerseyClient.post(MessageSent[].class, queryString, classicEmailRequest, "transactional", "classicEmail", "send");
    }
}
