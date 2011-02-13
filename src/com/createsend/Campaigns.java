package com.createsend;

import java.util.Date;

import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.campaigns.Schedule;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;

public class Campaigns extends BaseClient {
    public String create(String clientID, CampaignForCreation campaign) throws CreateSendException {
        return post(String.class, campaign, "campaigns", clientID + ".json");
    }
    
    public void send(String campaignID, String confirmationEmail, Date sendDate) throws CreateSendException {
        Schedule sched = new Schedule();
        sched.ConfirmationEmail = confirmationEmail;
        sched.SendDate = sendDate == null ? "Immediately" : JsonProvider.ApiDateFormat.format(sendDate);
        
        post(String.class, sched, "campaigns", campaignID, "send.json");
    }
    
    public void test(String campaignID, PreviewData data) throws CreateSendException {
        post(String.class, data, "campaigns", campaignID, "sendpreview.json");
    }
}
