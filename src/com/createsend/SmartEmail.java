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

import com.createsend.models.transactional.request.SmartEmailRequest;
import com.createsend.models.transactional.response.SmartEmailDetails;
import com.createsend.models.transactional.response.SmartEmailItem;
import com.createsend.models.transactional.response.SmartEmailStatus;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/transactional/smartEmail/" target="_blank">
 * Campaign</a> resources in the Campaign Monitor API
 */
public class SmartEmail extends CreateSendBase {

    private static final SmartEmailStatus defaultStatus = SmartEmailStatus.ACTIVE;

    /**
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public SmartEmail(AuthenticationDetails auth) {
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * List SmartEmails.
     * @return Array of SmartEmailItem.
     * @throws CreateSendException
     */
    public SmartEmailItem[] list() throws CreateSendException {
        return list(defaultStatus);
    }

    /**
     * List SmartEmails, filtered by status.
     * @param status
     * @return
     * @throws CreateSendException
     */
    public SmartEmailItem[] list(SmartEmailStatus status) throws CreateSendException {
        return list(status, null);
    }

    /**
     * List SmartEmails, filtered by status for a specific Client.
     * @param status
     * @param clientID
     * @return
     * @throws CreateSendException
     */
    public SmartEmailItem[] list(SmartEmailStatus status, String clientID) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("status", status.toValue());

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        return jerseyClient.get(SmartEmailItem[].class, queryString, "transactional", "smartEmail");
    }

    /**
     * Get a SmartEmail by SmartEmailID.
     * @param smartEmailId
     * @return SmartEmailDetails
     * @throws CreateSendException
     */
    public SmartEmailDetails get(UUID smartEmailId) throws CreateSendException {
        return jerseyClient.get(SmartEmailDetails.class, "transactional", "smartEmail", smartEmailId.toString());
    }

    /**
     * Send a SmartEmail.
     * @param smartEmailRequest The SmartEmailRequest to send.
     * @throws CreateSendException
     */
    public void send(SmartEmailRequest smartEmailRequest) throws CreateSendException {
        jerseyClient.post(String.class, smartEmailRequest, "transactional", "smartEmail", smartEmailRequest.getSmartEmailId().toString(), "send");
    }
}