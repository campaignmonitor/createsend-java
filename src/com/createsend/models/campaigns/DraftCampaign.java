package com.createsend.models.campaigns;

import java.util.Date;

public class DraftCampaign extends Campaign {
    public Date DateCreated;
    public String PreviewURL;
    
    @Override
    public String toString() {
        return String.format("{ %s, DateCreated: %s, PreviewURL: %s }", super.toString(), DateCreated, PreviewURL);
    }
}
