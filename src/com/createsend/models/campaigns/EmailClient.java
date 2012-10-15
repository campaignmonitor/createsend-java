package com.createsend.models.campaigns;

public class EmailClient {
	public String Client;
	public String Version;
	public float Percentage;
	public int Subscribers;
	
    @Override
    public String toString() {
        return String.format(
            "{ Client: %s, Version: %s, Percentage: %d, Subscribers: %d }",
            Client, Version, Percentage, Subscribers);
    }
}
