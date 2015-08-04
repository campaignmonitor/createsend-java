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

import com.createsend.models.transactional.response.Message;
import com.createsend.models.transactional.response.MessageLogItem;
import com.createsend.models.transactional.response.MessageSent;
import com.createsend.models.transactional.response.TransactionalStatistics;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/transactional/messages/" target="_blank">
 * Transactional Message</a> resources in the Campaign Monitor API
 */
public class Messages extends CreateSendBase {

    /**
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public Messages(AuthenticationDetails auth) {
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * @return get message by message id.
     * @throws CreateSendException
     */
    public Message get(UUID messageID) throws CreateSendException {
        return get(messageID, false);
    }

    /**
     * @param messageID
     * @param includeStatistics include statistics.
     * @return get message by message id, optionally including statistics.
     * @throws CreateSendException
     */
    public Message get(UUID messageID, boolean includeStatistics) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("statistics", String.valueOf(includeStatistics));

        return jerseyClient.get(Message.class, queryString, "transactional", "messages", messageID.toString());
    }

    /**
     * Gets statistics for a range of messages.
     * @param clientID optional Client ID to filter.
     * @param smartEmailID optional Smart Email ID.
     * @param group optional Group.
     * @param from optional From address.
     * @param to optional To address.
     * @param timezone optional timezone.
     * @return the message activity statistics.
     * @throws CreateSendException
     */
    public TransactionalStatistics statistics(String clientID, UUID smartEmailID, String group, Date from, Date to, String timezone) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        if (smartEmailID != null) {
            queryString.add("smartEmailID", smartEmailID.toString());
        }

        if (group != null) {
            queryString.add("group", group);
        }

        // TODO Constant somewhere (JsonProvider.ApiDateFormatNoTime?)
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (from != null) {
            String fromStr = dateFormat.format(from);
            queryString.add("from", fromStr);
        }

        if (to != null) {
            String toStr = dateFormat.format(to);
            queryString.add("to", toStr);
        }

        if (timezone != null) {
            queryString.add("timezone", timezone);
        }

        return jerseyClient.get(TransactionalStatistics.class, queryString, "transactional", "statistics");
    }

    /**
     * Resend a message. Message may have a retention limit and might not always be valid to resend.
     * @param messageID the message id to resend.
     * @return Message sent acknowledgement.
     * @throws CreateSendException
     */
    public MessageSent resend(UUID messageID) throws CreateSendException {
        return jerseyClient.post(MessageSent.class, (Object)null, "transactional", "messages", messageID.toString(), "resend");
    }

    /**
     * Perform a sliding window request of the delivery log.
     * @param clientID optional Client ID to filter.
     * @param sentBeforeID optional start range. Find message sent before this id.
     * @param sentAfterID optional end range. Find message sent after this id.
     * @param count optional count, number of messages to retrieve.
     * @param status optional message status filter.
     * @param smartEmailID optional filter to a specific smart email.
     * @param group optional filter to a specific group.
     * @return
     * @throws CreateSendException
     */
    public MessageLogItem[] timeline(String clientID, UUID sentBeforeID, UUID sentAfterID, Integer count, String status, UUID smartEmailID, String group) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();

        if (clientID != null) {
            queryString.add("clientID", clientID);
        }

        if (sentBeforeID != null) {
            queryString.add("sentBeforeID", sentBeforeID.toString());
        }

        if (sentAfterID != null) {
            queryString.add("sentAfterID", sentAfterID.toString());
        }

        if (count != null) {
            queryString.add("count", count.toString());
        }

        if (status != null) {
            queryString.add("status", status);
        }

        if (smartEmailID != null) {
            queryString.add("smartEmailID", smartEmailID.toString());
        }

        if (group != null) {
            queryString.add("group", group);
        }

        return jerseyClient.get(MessageLogItem[].class, queryString, "transactional", "messages");
    }
}
