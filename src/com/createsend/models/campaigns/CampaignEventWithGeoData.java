package com.createsend.models.campaigns;

public class CampaignEventWithGeoData {
    public String EmailAddress;
    public String ListID;
    public java.util.Date Date;
    public String IPAddress;
    public float Latitude;
    public float Longitude;
    public String City;
    public String Region;
    public String CountryCode;
    public String CountryName;

    @Override
    public String toString() {
    	return String.format("{ EmailAddress: %s, ListID: %s, Date: %s, IPAddress: %s, Latitude: %s, Longitude: %s, City: %s, Region: %s, CountryCode: %s, CountryName: %s }",
                EmailAddress, ListID, Date, IPAddress, Latitude, Longitude, City, Region, CountryCode, CountryName);
    }
}
