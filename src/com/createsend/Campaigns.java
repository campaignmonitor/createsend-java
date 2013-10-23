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
import com.createsend.models.campaigns.CampaignClick;
import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.CampaignForCreationFromTemplate;
import com.createsend.models.campaigns.CampaignOpen;
import com.createsend.models.campaigns.CampaignSummary;
import com.createsend.models.campaigns.EmailClient;
import com.createsend.models.campaigns.ListsAndSegments;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.campaigns.Schedule;
import com.createsend.models.subscribers.BouncedSubscriber;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.ErrorDeserialiser;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/campaigns/" target="_blank">
 * Campaign</a> resources in the Campaign Monitor API
 */
public class Campaigns extends CreateSendBase {
    private String campaignID;

    /**
     * Constructor used to create new campaigns.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public Campaigns(AuthenticationDetails auth) {
        this(auth, null);
    }

    /**
     * Constructor used for working with existing campaigns.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     * @param campaignID The campaign ID to use when making API calls.
     */
    public Campaigns(AuthenticationDetails auth, String campaignID) {
    	this.setCampaignID(campaignID);
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * Gets the current campaign ID.
     * @return The current campaign ID.
     */
    public String getCampaignID() {
        return campaignID;
    }

    /**
     * Sets the ID of the campaign to apply API calls to.
     * @param campaignID The ID of the campaign to apply API calls to.
     */
    public void setCampaignID(String campaignID) {
        this.campaignID = campaignID;
    }

    /**
     * Creates a new campaign for the specified client based on the provided campaign data.
     * @param clientID The ID of the client to create the campaign for
     * @param campaign The campaign information used for the new campaign
     * @return The ID of the newly created campaign
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#creating_a_draft_campaign" target="_blank">
     * Creating a campaign</a>
     */
    public String create(String clientID, CampaignForCreation campaign) throws CreateSendException {
        campaignID = jerseyClient.post(String.class, campaign, "campaigns", clientID + ".json");
        return campaignID;
    }

    /**
     * Creates a new campaign for the specified client, based on the template
     * and campaign data specified.
     * @param clientID The ID of the client to create the campaign for
     * @param campaign The campaign information used for the new campaign
     * @return The ID of the newly created campaign
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#creating_a_campaign_from_template" target="_blank">
     * Creating a campaign</a>
     */
    public String createFromTemplate(String clientID, CampaignForCreationFromTemplate campaign) throws CreateSendException {
        campaignID = jerseyClient.post(String.class, campaign, "campaigns", clientID + "/fromtemplate.json");
        return campaignID;
    }

    /**
     * Sends an existing draft campaign using the provided confirmation email and send date.
     * To schedule a campaign for immediate delivery use a <code>null</code> sendDate.
     * @param confirmationEmail An email address to send the delivery confirmation to.
     * @param sendDate The date to send the campaign at. This date should be in the clients timezone
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#sending_a_campaign" target="_blank">
     * Sending a campaign</a>
     */
    public void send(String confirmationEmail, Date sendDate) throws CreateSendException {
        Schedule sched = new Schedule();
        sched.ConfirmationEmail = confirmationEmail;
        sched.SendDate = sendDate == null ? "Immediately" : JsonProvider.ApiDateFormat.format(sendDate);
        
        jerseyClient.post(String.class, sched, "campaigns", campaignID, "send.json");
    }
    
    /**
     * Unschedules a campaign and moves it back to the drafts. If a campaign has already been sent or 
     * is in the process of sending, this api request will fail.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#unscheduling_a_campaign" target="_blank">
     * Sending a campaign</a>
     */
    public void unschedule() throws CreateSendException {
        jerseyClient.post(String.class, "", "campaigns", campaignID, "unschedule.json");
    }
    
    /**
     * Sends a preview of an existing draft campaign to the recipients specified in the preview data.
     * @param data The recipients and personalisation scheme to use for the preview
     * @throws CreateSendException Raised when the API responds with a HTTP status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#sending_a_campaign_preview" target="_blank">
     * Sending a campaign preview</a>
     */
    public void test(PreviewData data) throws CreateSendException {
        jerseyClient.post(String.class, data, new ErrorDeserialiser<String[]>(), 
            "campaigns", campaignID, "sendpreview.json");
    }
    
    /**
     * Get summary reporting data for the specified campaign
     * @return Summary reporting data for the specified campaign
     * @throws CreateSendException Raised if the API responds with a HTTP Status >= 400
     */
    public CampaignSummary summary() throws CreateSendException {
        return jerseyClient.get(CampaignSummary.class, "campaigns", campaignID, "summary.json");
    }

    /**
     * Gets the email clients that subscribers used to open the campaign
     * @return Array of email clients used to open the specified campaign
     * @throws CreateSendException Raised if the API responds with a HTTP Status >= 400
     */
    public EmailClient[] emailClientUsage() throws CreateSendException {
        return jerseyClient.get(EmailClient[].class, "campaigns", campaignID, "emailclientusage.json");
    }

    /**
     * Gets the lists and segments that a campaign is to be, or has been sent to.
     * @return The lists and segments that the campaign is to be, or has been sent to
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_listsandsegments" target="_blank">
     * Getting lists and segments</a>
     */
    public ListsAndSegments listsAndSegments() throws CreateSendException {
        return jerseyClient.get(ListsAndSegments.class, "campaigns", campaignID, "listsandsegments.json");
    }
    
    /**
     * Gets a paged list of recipients for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged recipients returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_recipients" target="_blank">
     * Getting campaign recipients</a>
     */
    public PagedResult<Subscriber> recipients(
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection, null, 
            "campaigns", campaignID, "recipients.json");
    }

    /**
     * Gets a paged list of bounces for the specified campaign
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_bouncelist" target="_blank">
     * Getting campaign bounces</a>
     */
    public PagedResult<BouncedSubscriber> bounces() throws CreateSendException {
    	return bounces(1, 1000, "date", "asc");
    }

    /**
     * Gets a paged list of bounces for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_bouncelist" target="_blank">
     * Getting campaign bounces</a>
     */
    public PagedResult<BouncedSubscriber> bounces(
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return bounces("", page, pageSize, orderField, orderDirection);
    }

    /**
     * Gets a paged list of bounces for the specified campaign
     * @param bouncesFrom The date to start getting bounce results from. This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_bouncelist" target="_blank">
     * Getting campaign bounces</a>
     */
    public PagedResult<BouncedSubscriber> bounces(Date bouncesFrom,
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return bounces(JsonProvider.ApiDateFormat.format(bouncesFrom),
    			page, pageSize, orderField, orderDirection);
    }
    
    private PagedResult<BouncedSubscriber> bounces(String bouncesFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", bouncesFrom);
        
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection, queryString, 
            "campaigns", campaignID, "bounces.json");
    }

    /**
     * Gets a paged list of opens for the specified campaign
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_openslist" target="_blank">
     * Getting campaign opens</a>
     */
    public PagedResult<CampaignOpen> opens() throws CreateSendException {
    	return opens(1, 1000, "date", "asc");
    }
    
    /**
     * Gets a paged list of opens for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_openslist" target="_blank">
     * Getting campaign opens</a>
     */
    public PagedResult<CampaignOpen> opens(
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return opens("", page, pageSize, orderField, orderDirection);
    }

    /**
     * Gets a paged list of opens for the specified campaign
     * @param opensFrom The date to start getting open results from. This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_openslist" target="_blank">
     * Getting campaign opens</a>
     */
    public PagedResult<CampaignOpen> opens(Date opensFrom,
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return opens(JsonProvider.ApiDateFormat.format(opensFrom),
    			page, pageSize, orderField, orderDirection);
    }

    private PagedResult<CampaignOpen> opens(String opensFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", opensFrom);
        
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "opens.json");
    }

    /**
     * Gets a paged list of clicks for the specified campaign
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_clickslist" target="_blank">
     * Getting campaign clicks</a>
     */
    public PagedResult<CampaignClick> clicks() throws CreateSendException {
    	return clicks(1, 1000, "date", "asc");
    }

    /**
     * Gets a paged list of clicks for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_clickslist" target="_blank">
     * Getting campaign clicks</a>
     */
    public PagedResult<CampaignClick> clicks(
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return clicks("", page, pageSize, orderField, orderDirection);
    }

    /**
     * Gets a paged list of clicks for the specified campaign
     * @param clicksFrom The date to start getting click results from. This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_clickslist" target="_blank">
     * Getting campaign clicks</a>
     */
    public PagedResult<CampaignClick> clicks(Date clicksFrom,
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return clicks(JsonProvider.ApiDateFormat.format(clicksFrom),
    			page, pageSize, orderField, orderDirection);
    }
    
    private PagedResult<CampaignClick> clicks(String clicksFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", clicksFrom);
        
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "clicks.json");
    }

    /**
     * Gets a paged list of unsubscribes for the specified campaign
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_unsubscribeslist" target="_blank">
     * Getting campaign unsubscribes</a>
     */
    public PagedResult<Subscriber> unsubscribes() throws CreateSendException {
    	return unsubscribes(1, 1000, "date", "asc");
    }

    /**
     * Gets a paged list of unsubscribes for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_unsubscribeslist" target="_blank">
     * Getting campaign unsubscribes</a>
     */
    public PagedResult<Subscriber> unsubscribes(
			Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return unsubscribes("", page, pageSize, orderField, orderDirection);
    }

    /**
     * Gets a paged list of unsubscribes for the specified campaign
     * @param unsubscribesFrom The date to start getting unsubscribe results from. This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_unsubscribeslist" target="_blank">
     * Getting campaign unsubscribes</a>
     */
    public PagedResult<Subscriber> unsubscribes(Date unsubscribesFrom,
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return unsubscribes(JsonProvider.ApiDateFormat.format(unsubscribesFrom),
    			page, pageSize, orderField, orderDirection);
    }
    
    private PagedResult<Subscriber> unsubscribes(String unsubscribesFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", unsubscribesFrom);
        
        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "unsubscribes.json");
    }

    /**
     * Gets a paged list of spam complaints for the specified campaign
     * @return The paged spam complaints returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_spam_complaints" target="_blank">
     * Campaign spam complaints</a>
     */
    public PagedResult<Subscriber> spamComplaints() throws CreateSendException {
    	return spamComplaints(1, 1000, "date", "asc");
    }

    /**
     * Gets a paged list of spam complaints for the specified campaign
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged spam complaints returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_spam_complaints" target="_blank">
     * Campaign spam complaints</a>
     */
    public PagedResult<Subscriber> spamComplaints(
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return spamComplaints("", page, pageSize, orderField, orderDirection);
    }

    /**
     * Gets a paged list of spam complaints for the specified campaign
     * @param spamComplaintsFrom The date to start getting spam complaints from. This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged spam complaints returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_spam_complaints" target="_blank">
     * Campaign spam complaints</a>
     */
    public PagedResult<Subscriber> spamComplaints(Date spamComplaintsFrom,
            Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
    	return spamComplaints(JsonProvider.ApiDateFormat.format(spamComplaintsFrom),
    			page, pageSize, orderField, orderDirection);
    }

    private PagedResult<Subscriber> spamComplaints(String spamComplaintsFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", spamComplaintsFrom);

        return jerseyClient.getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "spam.json");
    }

    /**
     * Deletes the specified draft campaign
     * @throws CreateSendException Raised when the API responds with a HTTP status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#deleting_a_campaign" target="_blank">
     * Deleting a campaign</a>
     */
    public void delete() throws CreateSendException {
        jerseyClient.delete("campaigns", campaignID + ".json");
    }
}
