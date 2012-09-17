package com.createsend.models.campaigns;

public class CampaignClick extends CampaignEventWithGeoData {
	public String URL;
	
    @Override
    public String toString() {
    	return String.format("{ URL: %s, EmailAddress: %s, ListID: %s, Date: %s, IPAddress: %s, Latitude: %s, Longitude: %s, City: %s, Region: %s, CountryCode: %s, CountryName: %s }",
                URL, EmailAddress, ListID, Date, IPAddress, Latitude, Longitude, City, Region, CountryCode, CountryName);
    }
}
