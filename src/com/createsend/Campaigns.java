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

import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.CampaignSummary;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.campaigns.Schedule;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;

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
}
