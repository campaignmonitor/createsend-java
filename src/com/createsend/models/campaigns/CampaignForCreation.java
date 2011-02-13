package com.createsend.models.campaigns;

import java.net.URI;

public class CampaignForCreation extends Campaign {
    public String FromName;
    public String FromEmail;
    public String ReplyTo;
    public URI HtmlUrl;
    public URI TextUrl;
    public String[] ListIDs;
    public String[] SegmentIDs;
}
