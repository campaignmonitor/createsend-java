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
import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.CampaignSummary;
import com.createsend.models.campaigns.ListsAndSegments;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.campaigns.Schedule;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Provides methods for accessing all <a href="http://www.campaignmonitor.com/api/campaigns/" target="_blank">
 * Campaign</a> resources in the Campaign Monitor API
 */
public class Campaigns extends BaseClient {
    /**
     * Creates a new campaign for the specified client based on the provided campaign data.
     * @param clientID The ID of the client to create the campaign for
     * @param campaign The campaign information used for the new campaign
     * @return The ID of the newly created campaign
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#creating_a_campaign" target="_blank">
     * Creating a campaign</a>
     */
    public String create(String clientID, CampaignForCreation campaign) throws CreateSendException {
        return post(String.class, campaign, "campaigns", clientID + ".json");
    }
    
    /**
     * Sends an existing draft campaign using the provided confirmation email and send date.
     * To schedule a campaign for immediate delivery use a <code>null</code> sendDate.
     * @param campaignID The ID of the draft to send
     * @param confirmationEmail An email address to send the delivery confirmation to.
     * @param sendDate The date to send the campaign at. This date should be in the clients timezone
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#sending_a_campaign" target="_blank">
     * Sending a campaign</a>
     */
    public void send(String campaignID, String confirmationEmail, Date sendDate) throws CreateSendException {
        Schedule sched = new Schedule();
        sched.ConfirmationEmail = confirmationEmail;
        sched.SendDate = sendDate == null ? "Immediately" : JsonProvider.ApiDateFormat.format(sendDate);
        
        post(String.class, sched, "campaigns", campaignID, "send.json");
    }
    
    /**
     * Sends a preview of an existing draft campaign to the recipients specified in the preview data.
     * @param campaignID The ID of the campaign to send a preview for
     * @param data The recipients and personalisation scheme to use for the preview
     * @throws CreateSendException Raised when the API responds with a HTTP status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#sending_a_campaign_preview" target="_blank">
     * Sending a campaign preview</a>
     */
    public void test(String campaignID, PreviewData data) throws CreateSendException {
        post(String.class, data, "campaigns", campaignID, "sendpreview.json");
    }
    
    /**
     * Get summary reporting data for the specified campaign
     * @param campaignID The ID of the campaign to get the reporting data for
     * @return Summary reporting data for the specified campaign
     * @throws CreateSendException Raised if the API responds with a HTTP Status >= 400
     */
    public CampaignSummary summary(String campaignID) throws CreateSendException {
        return get(CampaignSummary.class, "campaigns", campaignID, "summary.json");
    }
    
    /**
     * Gets the lists and segments that a campaign is to be, or has been sent to.
     * @param campaignID The ID of the campaign to get the lists and segments for
     * @return The lists and segments that the campaign is to be, or has been sent to
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_listsandsegments" target="_blank">
     * Getting lists and segments</a>
     */
    public ListsAndSegments listsandsegments(String campaignID) throws CreateSendException {
        return get(ListsAndSegments.class, "campaigns", campaignID, "listsandsegments.json");
    }
    
    /**
     * Gets a paged list of recipients for the specified campaign
     * @param campaignID The ID of the campaign to get the recipients for
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged recipients returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_recipients" target="_blank">
     * Getting campaign recipients</a>
     */
    public PagedResult<Subscriber> recipients(String campaignID,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        return getPagedResult(page, pageSize, orderField, orderDirection, null, 
            "campaigns", campaignID, "recipients.json");
    }
    
    /**
     * Gets a paged list of bounces for the specified campaign
     * @param campaignID The ID of the campaign to get the bounces for
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#campaign_bouncelist" target="_blank">
     * Getting campaign bounces</a>
     */
    public PagedResult<Subscriber> bounces(String campaignID,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        return getPagedResult(page, pageSize, orderField, orderDirection, null, 
            "campaigns", campaignID, "bounces.json");
    }
    
    /**
     * Gets a paged list of opens for the specified campaign
     * @param campaignID The ID of the campaign to get the opens for
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
    public PagedResult<Subscriber> opens(String campaignID, Date opensFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", JsonProvider.ApiDateFormat.format(opensFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "opens.json");
    }
    
    /**
     * Gets a paged list of clicks for the specified campaign
     * @param campaignID The ID of the campaign to get the clicks for
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
    public PagedResult<Subscriber> clicks(String campaignID, Date clicksFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", JsonProvider.ApiDateFormat.format(clicksFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "clicks.json");
    }
    
    /**
     * Gets a paged list of unsubscribes for the specified campaign
     * @param campaignID The ID of the campaign to get the unsubscribes for
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
    public PagedResult<Subscriber> unsubscribes(String campaignID, Date unsubscribesFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", JsonProvider.ApiDateFormat.format(unsubscribesFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "campaigns", campaignID, "unsubscribes.json");
    }
    
    /**
     * Deletes the specified draft campaign
     * @param campaignID The ID of the draft to delete
     * @throws CreateSendException Raised when the API responds with a HTTP status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/campaigns/#deleting_a_campaign" target="_blank">
     * Deleting a campaign</a>
     */
    public void delete(String campaignID) throws CreateSendException {
        delete("campaigns", campaignID + ".json");
    }
}
