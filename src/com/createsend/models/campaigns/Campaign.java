package com.createsend.models.campaigns;

public class Campaign {
    public String CampaignID;
    public String Subject;
    public String Name;
    
    @Override
    public String toString() {
        return String.format("CampaignID: %s, Subject: %s, Name: %s", CampaignID, Subject, Name);
    }
}
