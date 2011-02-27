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

import com.createsend.models.PagedResult;
import com.createsend.models.campaigns.DraftCampaign;
import com.createsend.models.campaigns.SentCampaign;
import com.createsend.models.clients.AccessDetails;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.clients.Template;
import com.createsend.models.lists.ListBasics;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.JerseyClient;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/clients/" target="_blank">
 * Client</a> resources in the Campaign Monitor API
 */
public class Clients {
    private String clientID;
    private JerseyClient jerseyClient;
    
    /**
     * Constructor.
     * Use this for creating new clients.
     */
    public Clients() {
        this(null);
    }
    
    /**
     * Constructor.
     * @param clientID The ID of the client to apply calls to.
     */
    public Clients(String clientID) {
        this(clientID, new JerseyClientImpl());
    }
    
    /**
     * Constructor.
     * @param clientID The ID of the client to apply calls to.
     * @param client The {@link com.createsend.util.JerseyClient} to use to make API calls
     */
    public Clients(String clientID, JerseyClient client) {
        setClientID(clientID);
        this.jerseyClient = client;
    }
    
    /**
     * Sets the current client ID.
     * @param clientID The ID of the client to apply API calls to.
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    
    /**
     * Gets the current client ID
     * @return The current client ID
     */
    public String getClientID() {
        return clientID;
    }
    
    /**
     * Creates a new client based on the provided data. 
     * After a successful call the current clientID property will be set to that
     * of the newly created client.
     * @param client The client information to use during creation
     * @return The ID of the newly created client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#creating_a_client" target="_blank">
     * Creating a client</a>
     */
    public String create(Client client) throws CreateSendException {
        clientID = jerseyClient.post(String.class, client, "clients.json");
        return clientID;
    }
   
    /**
     * Gets the details of the current client
     * @return The details of the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_a_client" target="_blank">
     * Getting a client</a>
     */
    public AllClientDetails details() throws CreateSendException {
        return jerseyClient.get(AllClientDetails.class, "clients", clientID + ".json");
    }
   
    /**
     * Gets all campaigns sent by the current client
     * @return All campaigns which have been sent by the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_campaigns" target="_blank">
     * Getting sent campaigns</a>
     */
    public SentCampaign[] sentCampaigns() throws CreateSendException {
        return jerseyClient.get(SentCampaign[].class, "clients", clientID, "campaigns.json");
    }
   
    /**
     * Gets all draft campaigns created for the current client
     * @return All draft campaigns available to the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_drafts" target="_blank">
     * Getting draft campaigns</a>
     */
    public DraftCampaign[] draftCampaigns() throws CreateSendException {
        return jerseyClient.get(DraftCampaign[].class, "clients", clientID, "drafts.json");
    }
   
    /**
     * Gets all lists for the current client
     * @return All lists available to the current client.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_lists" target="_blank">
     * Getting subscriber lists</a>
     */
    public ListBasics[] lists() throws CreateSendException {
        return jerseyClient.get(ListBasics[].class, "clients", clientID, "lists.json");
    }
   
    /**
     * Gets all segments defined for the current client
     * @return All segments defined for the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_segments" target="_blank">
     * Getting segments</a>
     */
    public Segment[] segments() throws CreateSendException {
        return jerseyClient.get(Segment[].class, "clients", clientID, "segments.json");
    }
   
    /**
     * Gets a paged collection of subscribers who are suppressed from the current clients lists.
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return Subscribers who are suppressed from the current clients lists
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_suppressionlist" target="_blank">
     * Getting client suppression list</a>
     */
    public PagedResult<Subscriber> suppressionList(
        Integer page, Integer pageSize, String orderField, String orderDirection) 
        throws CreateSendException {
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection, null,
            "clients", clientID, "suppressionlist.json");
    }
   
    /**
     * Gets all templates defined for the current client.
     * @return A collection of templates defined for the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_templates" target="_blank">
     * Getting client templates</a>
     */
    public Template[] templates() throws CreateSendException {
        return jerseyClient.get(Template[].class, "clients", clientID, "templates.json");
    }
   
    /**
     * Sets the basic details for the current client.
     * @param client The basic details to use for the current client.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#setting_basic_details" target="_blank">
     * Setting basic details</a>
     */
    public void setBasics(Client client) throws CreateSendException {
        jerseyClient.put(client, "clients", clientID, "setbasics.json");
    }
   
    /**
     * Sets which Campaign Monitor functionality a client may access, including login credentials.
     * @param access The funtionality a client has access to and login credentials for the current client.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#setting_access_details" target="_blank">
     * Setting access details</a>
     */
    public void setAccess(AccessDetails access) throws CreateSendException {
        jerseyClient.put(access, "clients", clientID, "setaccess.json");
    }
   
    /**
     * Sets a client to be billed per campaign sent. Markup values may be provided in the billing options.
     * @param billing The billing options available to the client, including any markup.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#setting_payg_billing" target="_blank">
     * Setting payg billing</a>
     */
    public void setPaygBilling(BillingDetails billing) throws CreateSendException {
        jerseyClient.put(billing, "clients", clientID, "setpaygbilling.json");
    }
   
    /**
     * Sets a client to be billed monthly based on the current number of active subscribers across
     * all lists. Markup rates can be provided in the billing options
     * @param billing The billing options available to the client, including any markup. 
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#setting_monthly_billing" target="_blank">
     * Setting monthly billing</a>
     */
    public void setMonthlyBilling(BillingDetails billing) throws CreateSendException {
        billing.MarkupOnDelivery = null;
        billing.MarkupOnDesignSpamTest = null;
        billing.MarkupPerRecipient = null;
        billing.CanPurchaseCredits = false;
       
        jerseyClient.put(billing, "clients", clientID, "setmonthlybilling.json");
    }
   
    /**
     * Deletes the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#deleting_a_client" target="_blank">
     * Deleting a client</a>
     */
    public void delete() throws CreateSendException {
        jerseyClient.delete("clients", clientID + ".json");
    }
}