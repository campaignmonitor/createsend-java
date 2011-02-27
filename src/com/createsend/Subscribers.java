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

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.subscribers.EmailToUnsubscribe;
import com.createsend.models.subscribers.HistoryItem;
import com.createsend.models.subscribers.ImportResult;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.models.subscribers.SubscriberToAdd;
import com.createsend.models.subscribers.SubscribersToAdd;
import com.createsend.util.ErrorDeserialiser;
import com.createsend.util.JerseyClient;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/subscribers/" target="_blank">
 * Subscriber</a> resources in the Campaign Monitor API
 */
public class Subscribers {
    private String listID;
    private JerseyClient client;
    
    /**
     * Constructor
     * @param listID The ID of the list to apply any calls to
     */
    public Subscribers(String listID) {
        this(listID, new JerseyClientImpl());
    }
    
    /**
     * Constructor
     * @param listID The ID of the list to apply any calls to
     * @param client The {@link com.createsend.util.JerseyClient} to use for any API requests
     */
    public Subscribers(String listID, JerseyClient client) {
        setListID(listID);
        this.client = client;
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
     * Adds a single subscriber to the specified list
     * @param subscriber The subscriber to add to the list
     * @return The email addresss of the newly added subscriber
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#adding_a_subscriber" target="_blank">
     * Adding a subscriber</a>
     */
    public String add(SubscriberToAdd subscriber) throws CreateSendException {
        return client.post(String.class, subscriber, "subscribers", listID + ".json");
    }
    
    /**
     * Import many subscribers into the specified list
     * @param subscribers The subscribers to add to the list
     * @return The results of the import. 
     * This will detail how many of the subscribers were new, already subscribed to the list
     * or duplicated in the submission     
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#importing_subscribers" target="_blank">
     * Importing subscribers</a>
     */
    public ImportResult addMany(SubscribersToAdd subscribers) throws CreateSendException {
        return client.post(ImportResult.class, subscribers, 
            new ErrorDeserialiser<ImportResult>(), "subscribers", listID, "import.json");
    }
    
    /**
     * Gets the details for the subscriber with the given email address in the specified list
     * @param emailAddress The email address to get the subscriber details for
     * @return The details of the subscriber
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#getting_subscriber_details" target="_blank">
     * Getting subscriber details</a>
     */
    public Subscriber details(String emailAddress) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        return client.get(Subscriber.class, queryString, "subscribers", listID + ".json");
    }
    
    /**
     * Gets the complete history for a given subscriber in the specified list
     * @param emailAddress The email address of the subscriber to get the history for
     * @return The complete history for the given subscriber
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#getting_subscriber_history" target="_blank">
     * Getting subscriber history</a>
     */
    public HistoryItem[] history(String emailAddress) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        return client.get(HistoryItem[].class, queryString, "subscribers", listID, "history.json");
    }
    
    /**
     * Unsubscribes the given email address from the specified list
     * @param emailAddress The email address to unsubscibe
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#unsubscribing_a_subscriber" target="_blank">
     * Unsubscribing a subscriber</a>
     */
    public void unsubscribe(String emailAddress) throws CreateSendException {
        client.post(String.class, EmailToUnsubscribe.fromString(emailAddress), 
            "subscribers", listID, "unsubscribe.json");
    }
}
