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
package com.createsend;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.PagedResult;
import com.createsend.models.lists.CustomField;
import com.createsend.models.lists.CustomFieldForCreate;
import com.createsend.models.lists.CustomFieldForUpdate;
import com.createsend.models.lists.List;
import com.createsend.models.lists.ListForUpdate;
import com.createsend.models.lists.Statistics;
import com.createsend.models.lists.UpdateFieldOptions;
import com.createsend.models.lists.Webhook;
import com.createsend.models.lists.WebhookTestFailureDetails;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.ErrorDeserialiser;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/lists/" target="_blank">
 * List</a> resources in the Campaign Monitor API *
 */
public class Lists extends CreateSendBase {
    private String listID;

    /**
     * Constructor used to create new lists.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public Lists(AuthenticationDetails auth) {
        this(auth, null);
    }

    /**
     * Constructor for working with existing lists.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     * @param listID The List ID to use when making API calls.
     */
    public Lists(AuthenticationDetails auth, String listID) {
        setListID(listID);
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * Sets the current list ID. 
     * @param listID The ID of the list to apply any calls to.
     */
    public void setListID(String listID) {
        this.listID = listID;
    }
    
    /**
     * Gets the current list ID.
     * @return The current list ID.
     */
    public String getListID() {
        return listID;
    }
    
    /**
     * Creates a new empty subscriber list.
     * After a successful call, the current list id property will be set the ID of the 
     * newly created list.
     * @param clientID The ID of the client owning the new list
     * @param list The details of the new list
     * @return The ID of the newly created list
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#creating-a-list" target="_blank">
     * Creating a list</a>
     */
    public String create(String clientID, List list) throws CreateSendException {
        listID = jerseyClient.post(String.class, list, "lists", clientID + ".json");
        return listID;
    }
    
    /**
     * Updates the basic list details for an existing subscriber list
     * @param list The new basic details for the list
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#updating-a-list" target="_blank">
     * Updating a list</a>
     */
    public void update(ListForUpdate list) throws CreateSendException {
        jerseyClient.put(list, "lists", listID + ".json");
    }
    
    /**
     * Deletes the list with the specified ID
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleting-a-list" target="_blank">
     * Deleting a list</a>
     */
    public void delete() throws CreateSendException {
        jerseyClient.delete("lists", listID + ".json");
    }
    
    /**
     * Gets the details of the list with the given ID
     * @return The details of the list with the given ID
     * @throws CreateSendException Raised when the API returns a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#list-details" target="_blank">
     * Getting list details</a>
     */
    public List details() throws CreateSendException {
        return jerseyClient.get(List.class, "lists", listID + ".json");
    }
    
    /**
     * Gets subscriber statistics for the list with the specified ID
     * @return Subscriber statistics for the list with the specified ID
     * @throws CreateSendException Raised when the API responds with HTTP status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#list-stats" target="_blank">
     * Getting list stats</a>
     */
    public Statistics stats() throws CreateSendException {
        return jerseyClient.get(Statistics.class, "lists", listID, "stats.json");
    }
    
    /**
     * Gets the custom fields available for the list with the specified ID
     * @return The custom fields available for the specified list
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#list-custom-fields" target="_blank">
     * Getting list custom fields</a>
     */
    public CustomField[] customFields() throws CreateSendException {
        return jerseyClient.get(CustomField[].class, "lists", listID, "customfields.json");
    }
    
    /**
     * Gets the segments available in the list with the specified ID
     * @return The segments available in the specified list
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#list-segments" target="_blank">
     * Getting list segments</a>
     */
    public Segment[] segments() throws CreateSendException {
        return jerseyClient.get(Segment[].class, "lists", listID, "segments.json");
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active() throws CreateSendException {
        return active(1, 1000, "email", "asc", false);
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(boolean includeTrackingPreference) throws CreateSendException {
        return active(1, 1000, "email", "asc", includeTrackingPreference);
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return active("", page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(Integer page, Integer pageSize, String orderField,
        String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return active("", page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list 
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who became active on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return active(JsonProvider.ApiDateFormat.format(subscribedFrom),
                page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of active subscribers who have subscribed to the list
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who became active on or after this date.
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#active-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return active(JsonProvider.ApiDateFormat.format(subscribedFrom),
            page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }
    
    private PagedResult<Subscriber> active(String subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", subscribedFrom);
        queryString.add("includetrackingpreference", String.valueOf(includeTrackingPreference));

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "active.json");
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed() throws CreateSendException {
        return unconfirmed(1, 1000, "email", "asc", false);
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed(boolean includeTrackingPreference) throws CreateSendException {
        return unconfirmed(1, 1000, "email", "asc", includeTrackingPreference);
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed(Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return unconfirmed("", page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed(Integer page, Integer pageSize, String orderField,
        String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return unconfirmed("", page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list 
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who subscribed on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return unconfirmed(JsonProvider.ApiDateFormat.format(subscribedFrom),
                page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of unconfirmed subscribers who have subscribed to the list
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who subscribed on or after this date.
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unconfirmed-subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> unconfirmed(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return unconfirmed(JsonProvider.ApiDateFormat.format(subscribedFrom),
            page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }
    
    private PagedResult<Subscriber> unconfirmed(String subscribedFrom, Integer page,
        Integer pageSize, String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", subscribedFrom);
        queryString.add("includetrackingpreference", String.valueOf(includeTrackingPreference));

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "unconfirmed.json");
    }

    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed() throws CreateSendException {
        return unsubscribed(1, 1000, "email", "asc", false);
    }

    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(boolean includeTrackingPreference) throws CreateSendException {
        return unsubscribed(1, 1000, "email", "asc", includeTrackingPreference);
    }


    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return unsubscribed("", page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return unsubscribed("", page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list 
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who unsubscribed on or after this date. 
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(Date subscribedFrom, Integer page,
        Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        return unsubscribed(JsonProvider.ApiDateFormat.format(subscribedFrom),
                page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who unsubscribed on or after this date.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#unsubscribed-subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return unsubscribed(JsonProvider.ApiDateFormat.format(subscribedFrom),
            page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }


    private PagedResult<Subscriber> unsubscribed(String subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", subscribedFrom);
        queryString.add("includetrackingpreference", String.valueOf(includeTrackingPreference));

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "unsubscribed.json");
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted() throws CreateSendException {
        return deleted(1, 1000, "email", "asc", false);
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted(boolean includeTrackingPreference) throws CreateSendException {
        return deleted(1, 1000, "email", "asc", includeTrackingPreference);
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted(Integer page, Integer pageSize, String orderField,
        String orderDirection) throws CreateSendException {
        return deleted("", page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted(Integer page, Integer pageSize, String orderField,
        String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return deleted("", page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list 
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who were deleted on or after this date. 
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted(Date subscribedFrom,Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return deleted(JsonProvider.ApiDateFormat.format(subscribedFrom),
                page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of subscribers who have been deleted from the list
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who were deleted on or after this date.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleted-subscribers" target="_blank">
     * Getting deleted subscribers</a>
     */
    public PagedResult<Subscriber> deleted(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return deleted(JsonProvider.ApiDateFormat.format(subscribedFrom),
            page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }
    
    private PagedResult<Subscriber> deleted(String subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", subscribedFrom);
        queryString.add("includetrackingpreference", String.valueOf(includeTrackingPreference));

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "deleted.json");
    }
    
    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced() throws CreateSendException {
        return bounced(1, 1000, "email", "asc", false);
    }

    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(boolean includeTrackingPreference) throws CreateSendException {
        return bounced(1, 1000, "email", "asc", includeTrackingPreference);
    }

    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(Integer page, Integer pageSize,
        String orderField, String orderDirection) throws CreateSendException {
        return bounced("", page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(Integer page, Integer pageSize, String orderField,
        String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return bounced("", page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list 
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who bounced out on or after this date. 
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(Date subscribedFrom, Integer page,
        Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        return bounced(JsonProvider.ApiDateFormat.format(subscribedFrom),
                page, pageSize, orderField, orderDirection, false);
    }

    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list
     * since the provided date.
     * @param subscribedFrom The API will only return subscribers who bounced out on or after this date.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @param includeTrackingPreference To include subscriber consent to track value in the results.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#bounced-subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(Date subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        return bounced(JsonProvider.ApiDateFormat.format(subscribedFrom),
            page, pageSize, orderField, orderDirection, includeTrackingPreference);
    }

    private PagedResult<Subscriber> bounced(String subscribedFrom, Integer page, Integer pageSize,
        String orderField, String orderDirection, boolean includeTrackingPreference) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", subscribedFrom);
        queryString.add("includetrackingpreference", String.valueOf(includeTrackingPreference));

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "bounced.json");
    }
    
    /**
     * Creates a new custom field with the specified data
     * @param customField The custom field options
     * @return The Key of the newly created custom field
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#creating-custom-field" target="_blank">
     * Creating a custom field</a>
     */
    public String createCustomField(CustomFieldForCreate customField) throws CreateSendException {
        return jerseyClient.post(String.class, customField, "lists", listID, "customfields.json");
    }

    /**
     * Updates a new custom field.
     * @param fieldKey The <em>Key</em> of the custom field to update. This must be surrounded by [].
     * @param customField The custom field options
     * @return The Key of the updated custom field
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#updating-custom-field" target="_blank">
     * Creating a custom field</a>
     */
    public String updateCustomField(String fieldKey, CustomFieldForUpdate customField)
        throws CreateSendException {
        return jerseyClient.put(String.class, customField, "lists", listID, "customfields", fieldKey + ".json");
    }

    /**
     * Updates the available options for a Multi-Valued custom field.
     * @param fieldKey The <em>Key</em> of the custom field to update. This must be surrounded by [].
     * @param options The new options to use for the field. 
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#updating-custom-field-options" target="_blank">
     * Updating custom field options</a>
     */
    public void updateCustomFieldOptions(String fieldKey, UpdateFieldOptions options)
        throws CreateSendException {
        jerseyClient.put(options, "lists", listID, "customFields", fieldKey, "options.json");
    }
    
    /**
     * Deletes the custom field with the specified key
     * @param fieldKey The <em>Key</em> of the custom field to delete. This must be surrounded by [].
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleting-custom-field" target="_blank">
     * Deleting a custom field</a>
     */
    public void deleteCustomField(String fieldKey) throws CreateSendException {
        jerseyClient.delete("lists", listID, "customFields", fieldKey + ".json");
    }
    
    /**
     * Gets all webhooks which have been attached to events on the specified list
     * @return The webhooks which have been attached to events for the specified list
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#list-webhooks" target="_blank">
     * Getting list webhooks</a>
     */
    public Webhook[] webhooks() throws CreateSendException {
        return jerseyClient.get(Webhook[].class, "lists", listID, "webhooks.json");
    }
    
    /**
     * Creates a new webhook on the specified list.
     * @param webhook The webhook details
     * @return The ID of the newly created webhook
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#creating-a-webhook" target="_blank">
     * Creating a webhook</a>
     */
    public String createWebhook(Webhook webhook) throws CreateSendException {
        return jerseyClient.post(String.class, webhook, "lists", listID, "webhooks.json");
    }
    
    /**
     * Tests the specified webhook
     * @param webhookID The ID of the webhook
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400. I.e the test fails
     * @see <a href="https://www.campaignmonitor.com/api/lists/#testing-a-webhook" target="_blank">
     * Testing a webhook</a>
     */
    public void testWebhook(String webhookID) throws CreateSendException {
        jerseyClient.get(String.class, new ErrorDeserialiser<WebhookTestFailureDetails>(){},
            "lists", listID, "webhooks", webhookID, "test.json");
    }
    
    /**
     * Deletes the specified webhook
     * @param webhookID The ID of the webhook to delete
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deleting-a-webhook" target="_blank">
     * Deleting a webhook</a>
     */
    public void deleteWebhook(String webhookID) throws CreateSendException {
        jerseyClient.delete("lists", listID, "webhooks", webhookID + ".json");
    }
    
    /**
     * Activates the specified webhook.
     * @param webhookID The ID of the webhook to activate
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#activating-a-webhook" target="_blank">
     * Activating a webhook</a>
     */
    public void activateWebhook(String webhookID) throws CreateSendException {
        jerseyClient.put("", "lists", listID, "webhooks", webhookID, "activate.json");
    }
    
    /**
     * Deactivates the specified webhook.
     * @param webhookID The ID of the webhook to deactivate
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/lists/#deactivating-a-webhook" target="_blank">
     * Activating a webhook</a>
     */
    public void deactivateWebhook(String webhookID) throws CreateSendException {
        jerseyClient.put("", "lists", listID, "webhooks", webhookID, "deactivate.json");
    }
}
