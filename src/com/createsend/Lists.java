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
import com.createsend.models.lists.List;
import com.createsend.models.lists.Statistics;
import com.createsend.models.lists.UpdateFieldOptions;
import com.createsend.models.lists.Webhook;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Lists extends BaseClient {
    /**
     * Creates a new empty subscriber list.
     * @param clientID The ID of the client owning the new list
     * @param list The details of the new list
     * @return The ID of the newly created list
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#creating_a_list" target="_blank">
     * Creating a list</a>
     */
    public String create(String clientID, List list) throws CreateSendException {
        return post(String.class, list, "lists", clientID + ".json");
    }
    
    /**
     * Updates the basic list details for an existing subscriber list
     * @param listID The ID of the list to update
     * @param list The new basic details for the list
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#updating_a_list" target="_blank">
     * Updating a list</a>
     */
    public void update(String listID, List list) throws CreateSendException {
        put(list, "lists", listID + ".json");
    }
    
    /**
     * Deletes the list with the specified ID
     * @param listID The ID of the list to delete
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#deleting_a_list" target="_blank">
     * Deleting a list</a>
     */
    public void delete(String listID) throws CreateSendException {
        delete("lists", listID + ".json");
    }
    
    /**
     * Gets the details of the list with the given ID
     * @param listID The ID of the list to get the details of
     * @return The details of the list with the given ID
     * @throws CreateSendException Raised when the API returns a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_list_details" target="_blank">
     * Getting list details</a>
     */
    public List details(String listID) throws CreateSendException {
        return get(List.class, "lists", listID + ".json");
    }
    
    /**
     * Gets subscriber statistics for the list with the specified ID
     * @param listID The ID of the list to get subscriber statistics for
     * @return Subscriber statistics for the list with the specified ID
     * @throws CreateSendException Raised when the API responds with HTTP status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_list_stats" target="_blank">
     * Getting list stats</a>
     */
    public Statistics stats(String listID) throws CreateSendException {
        return get(Statistics.class, "lists", listID, "stats.json");
    }
    
    /**
     * Gets the custom fields available for the list with the specified ID
     * @param listID The ID of the list to get the custom fields for
     * @return The custom fields available for the specified list
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_list_custom_fields" target="_blank">
     * Getting list custom fields</a>
     */
    public CustomField[] customFields(String listID) throws CreateSendException {
        return get(CustomField[].class, "lists", listID, "customfields.json");
    }
    
    /**
     * Gets the segments available in the list with the specified ID
     * @param listID The ID of the list to get the segments for.
     * @return The segments available in the specified list
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_list_segments" target="_blank">
     * Getting list segments</a>
     */
    public Segment[] segments(String listID) throws CreateSendException {
        return get(Segment[].class, "lists", listID, "segments.json");
    }
    
    /**
     * Gets a paged collection of active subscribers who have subscribed to the list 
     * since the provided date.
     * @param listID The ID of the list to get the subcribers from.
     * @param subscribedFrom The API will only return subscribers who became active on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_active_subscribers" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(String listID, Date subscribedFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", JsonProvider.ApiDateFormat.format(subscribedFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "active.json");
    }
    
    /**
     * Gets a paged collection of unsubscribed subscribers who have unsubscribed from the list 
     * since the provided date.
     * @param listID The ID of the list to get the subcribers from.
     * @param subscribedFrom The API will only return subscribers who unsubscribed on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_unsubscribed_subscribers" target="_blank">
     * Getting unsubscribed subscribers</a>
     */
    public PagedResult<Subscriber> unsubscribed(String listID, Date subscribedFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", JsonProvider.ApiDateFormat.format(subscribedFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "unsubscribed.json");
    }
    
    /**
     * Gets a paged collection of bounced subscribers who have bounced out of the list 
     * since the provided date.
     * @param listID The ID of the list to get the subcribers from.
     * @param subscribedFrom The API will only return subscribers who bounced out on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_bounced_subscribers" target="_blank">
     * Getting bounced subscribers</a>
     */
    public PagedResult<Subscriber> bounced(String listID, Date subscribedFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", JsonProvider.ApiDateFormat.format(subscribedFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "lists", listID, "bounced.json");
    }
    
    /**
     * Creates a new custom field with the specified data
     * @param listID The ID of the list to create the custom field on
     * @param customField The custom field options
     * @return The Key of the newly created custom field
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#creating_a_custom_field" target="_blank">
     * Creating a custom field</a>
     */
    public String createCustomField(String listID, CustomField customField) throws CreateSendException {
        return post(String.class, customField, "lists", listID, "customfields.json");
    }
    
    /**
     * Updates the available options for a Multi-Valued custom field.
     * @param listID The ID of the list owning the custom field
     * @param fieldKey The <em>Key</em> of the custom field to update. This must be surrounded by [].
     * @param options The new options to use for the field. 
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#updating_custom_field_options" target="_blank">
     * Updating custom field options</a>
     */
    public void updateCustomFieldOptions(String listID, String fieldKey, UpdateFieldOptions options) 
        throws CreateSendException {
        put(options, "lists", listID, "customFields", fieldKey, "options.json");
    }
    
    /**
     * Deletes the custom field with the specified key
     * @param listID The ID of the list owning the custom field
     * @param fieldKey The <em>Key</em> of the custom field to delete. This must be surrounded by [].
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#deleting_a_custom_field" target="_blank">
     * Deleting a custom field</a>
     */
    public void deleteCustomField(String listID, String fieldKey) throws CreateSendException {
        delete("lists", listID, "customFields", fieldKey + ".json");
    }
    
    /**
     * Gets all webhooks which have been attached to events on the specified list
     * @param listID The ID of the list to get webhooks for.
     * @return The webhooks which have been attached to events for the specified list
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#getting_list_webhooks" target="_blank">
     * Getting list webhooks</a>
     */
    public Webhook[] webhooks(String listID) throws CreateSendException {
        return get(Webhook[].class, "lists", listID, "webhooks.json");
    }
    
    /**
     * Creates a new webhook on the specified list.
     * @param listID The ID of the list to attach the webhook to.
     * @param webhook The webhook details
     * @return The ID of the newly created webhook
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#creating_a_webhook" target="_blank">
     * Creating a webhook</a>
     */
    public String createWebhook(String listID, Webhook webhook) throws CreateSendException {
        webhook.Status = null;
        webhook.WebhookID = null;
        
        return post(String.class, webhook, "lists", listID, "webhooks.json");
    }
    
    /**
     * Tests the specified webhook
     * @param listID The ID of the list on which the webhook is attached
     * @param webhookID The ID of the webhook
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400. I.e the test fails
     * @see <a href="http://www.campaignmonitor.com/api/lists/#testing_a_webhook" target="_blank">
     * Testing a webhook</a>
     */
    public void testWebhook(String listID, String webhookID) throws CreateSendException {
        get(String.class, "lists", listID, "webhooks", webhookID, "test.json");
    }
    
    /**
     * Deletes the specified webhook
     * @param listID The ID of the list to which the webhook is attached
     * @param webhookID The ID of the webhook to delete
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#deleting_a_webhook" target="_blank">
     * Deleting a webhook</a>
     */
    public void deleteWebhook(String listID, String webhookID) throws CreateSendException {
        delete("lists", listID, "webhooks", webhookID + ".json");
    }
    
    /**
     * Activates the specified webhook.
     * @param listID The ID of the list to which the webhook is attached
     * @param webhookID The ID of the webhook to activate
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/lists/#activating_a_webhook" target="_blank">
     * Activating a webhook</a>
     */
    public void activateWebhook(String listID, String webhookID) throws CreateSendException {
        put("lists", listID, "webhooks", webhookID, "activate.json");
    }
}
