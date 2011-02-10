package com.createsend.models.campaigns;

import java.util.Date;

public class SentCampaign extends Campaign {
    public String WebVersionURL;
    public Date SentDate;
    public int TotalRecipients;
    
    @Override
    public String toString() {
        return String.format("{ %s, SendDate: %s, WebVersionURL: %s, TotalRecipients: %d }", super.toString(), 
                SentDate, WebVersionURL, TotalRecipients);
    }
}
