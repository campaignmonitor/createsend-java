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
import com.createsend.models.campaigns.ScheduledCampaign;
import com.createsend.models.campaigns.SentCampaign;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.clients.CreditsTransferDetails;
import com.createsend.models.clients.CreditsTransferResult;
import com.createsend.models.clients.SuppressionDetails;
import com.createsend.models.clients.Tag;
import com.createsend.models.clients.Template;
import com.createsend.models.journeys.JourneyDetail;
import com.createsend.models.lists.ListBasics;
import com.createsend.models.lists.ListForEmail;
import com.createsend.models.people.Person;
import com.createsend.models.people.PersonResult;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.SuppressedSubscriber;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Date;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/clients/" target="_blank">
 * Client</a> resources in the Campaign Monitor API
 */
public class Clients extends CreateSendBase {
    private String clientID;

    /**
     * Constructor used to create new clients.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public Clients(AuthenticationDetails auth) {
        this(auth, null);
    }

    /**
     * Constructor used for working with existing clients.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     * @param clientID The Client ID to use when making API calls.
     */
    public Clients(AuthenticationDetails auth, String clientID) {
        setClientID(clientID);
        this.jerseyClient = new JerseyClientImpl(auth);
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
     * Gets all tags of the current client
     * @return All client tags
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_tags" target="_blank">
     * Getting tags</a>
     */
    public Tag[] tags() throws CreateSendException {
        return jerseyClient.get(Tag[].class, "clients", clientID, "tags.json");
    }

    /**
     * Gets a paged list of campaigns sent by the current client
     * @return The paged campaigns returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_campaigns" target="_blank">
     * Getting sent campaigns</a>
     */
    public PagedResult<SentCampaign> sentCampaigns() throws CreateSendException {
        return sentCampaigns(1, 1000, "desc");
    }

    /**
     * Gets a paged list of campaigns sent by the current client
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order results by. Use <code>null</code> for the default.
     * @return The paged campaigns returned by the api call
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_campaigns" target="_blank">
     * Getting sent campaigns</a>
     */
    public PagedResult<SentCampaign> sentCampaigns(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return sentCampaigns("", "", null, page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of campaigns sent by the current client
     * @param sentFromDate Campaigns sent on or after the <code>sentFromDate</code> value specified will be returned.
     *                     Must be in the format YYYY-MM-DD. If not provided, results will go back to the beginning of the client’s history.
     *                     Use <code>null</code> for the default
     * @param sentToDate Campaigns sent on or before the <code>sentToDate</code> value specified will be returned.
     *                   Must be in the format YYYY-MM-DD. If not provided, results will include the most recent sent campaigns.
     *                   Use <code>null</code> for the default
     * @param tags An array of tags to filter sent campaigns.
     *             Sent campaigns with all the tags specified will be returned.
     *             Use <code>null</code> for the default
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page.
     *                 The value can be between 1 and 1000.
     *                 Use <code>null</code> for the default.
     * @param orderDirection The direction to order results by.
     *                       The value can be ASC or DESC
     *                       Use <code>null</code> for the default (DESC)
     * @return The paged campaigns returned by the api call
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_campaigns" target="_blank">
     * Getting sent campaigns</a>
     */
    public PagedResult<SentCampaign> sentCampaigns(
            Date sentFromDate, Date sentToDate, String tags,
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return sentCampaigns(
                sentFromDate != null ? JsonProvider.ApiDateFormat.format(sentFromDate) : null,
                sentToDate != null ? JsonProvider.ApiDateFormat.format(sentToDate) : null,
                tags,
                page, pageSize, orderDirection);
    }

    private PagedResult<SentCampaign> sentCampaigns(
            String sentFromDate, String sentToDate, String tags,
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("sentFromDate", sentFromDate);
        queryString.add("sentToDate", sentToDate);
        queryString.add("tags", tags);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection, queryString,
                "clients", clientID, "campaigns.json");
    }
   
    /**
     * Gets all campaigns scheduled to tbe sent by the current client
     * @return All campaigns which are currently scheduled to send for the current client
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_scheduled_campaigns" target="_blank">
     * Getting sent campaigns</a>
     */
    public ScheduledCampaign[] scheduledCampaigns() throws CreateSendException {
        return jerseyClient.get(ScheduledCampaign[].class, "clients", clientID, "scheduled.json");
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
     * @see <a href="http://www.campaignmonitor.com/api/clients/#subscriber_lists" target="_blank">
     * Getting subscriber lists</a>
     */
    public ListBasics[] lists() throws CreateSendException {
        return jerseyClient.get(ListBasics[].class, "clients", clientID, "lists.json");
    }

    /**
     * Gets the lists across a client, to which a subscriber with a particular
     * email address belongs.
     * @param emailAddress A subscriber's email address
     * @return All client lists to which the email address is subscribed.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#lists_for_email" target="_blank">
     * Lists for an email address</a>
     */
    public ListForEmail[] listsForEmailAddress(String emailAddress) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);

        return jerseyClient.get(ListForEmail[].class, queryString, "clients", clientID, "listsforemail.json");
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
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return Subscribers who are suppressed from the current clients lists
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_suppressionlist" target="_blank">
     * Getting client suppression list</a>
     */
    public PagedResult<SuppressedSubscriber> suppressionList(
        Integer page, Integer pageSize, String orderField, String orderDirection) 
        throws CreateSendException {
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection, null,
            "clients", clientID, "suppressionlist.json");
    }

    public void suppress(SuppressionDetails details) throws CreateSendException {
    	jerseyClient.post(String.class, details, "clients", clientID, "suppress.json");
    }
    
    public void unsuppress(String email) throws CreateSendException {
		MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
		queryString.add("email", email);
		jerseyClient.put("", queryString, "clients", clientID, "unsuppress.json");
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
     * Transfer credits to or from this client.
     * @param details The credit transfer details.
     * @return The result of the credits transfer.
     * @throws CreateSendException
     */
    public CreditsTransferResult transferCredits(CreditsTransferDetails details) throws CreateSendException {
    	return jerseyClient.post(CreditsTransferResult.class, details, "clients", clientID, "credits.json");
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
    
    /**
     * returns a list of people associated with the client.
     * @return all people associated with the client
     * @throws CreateSendException
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_people" target="_blank">
     * Getting people</a>
     */
    public Person[] people() throws CreateSendException {
    	return jerseyClient.get(Person[].class, "clients", clientID, "people.json");
    }
    
    /**
     * looks up the primary contact for this client
     * @return the email address of the primary contact for the client
     * @throws CreateSendException
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_primary_contact" target="_blank">
     * Getting primary contact</a>
     */
    public String getPrimaryContact() throws CreateSendException {
    	return jerseyClient.get(PersonResult.class, "clients", clientID, "primarycontact.json").EmailAddress;
    } 
    
    /**
     * sets the primary contact for this client to the person with the specified email address
     * @throws CreateSendException
     * @see <a href="http://www.campaignmonitor.com/api/clients/#setting_primary_contact" target="_blank">
     * Setting primary contact</a>
     */
    public void setPrimaryContact(String emailAddress) throws CreateSendException {
    	MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
    	jerseyClient.put("", queryString, "clients", clientID, "primarycontact.json");
    }

    /**
     * returns a list of journeys associated with the client.
     * @return all journeys associated with the client
     * @throws CreateSendException
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_journeys" target="_blank">
     * Getting journeys</a>
     */
    public JourneyDetail[] journeys() throws CreateSendException {
        return jerseyClient.get(JourneyDetail[].class, "clients", clientID, "journeys.json");
    }
}
