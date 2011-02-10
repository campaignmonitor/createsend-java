package com.createsend.models.clients;

public class DetailedClientWithID extends DetailedClient {
    public String ClientID;
    
    public String toString() { 
        return String.format("{ ID: %s, Company: %s, Contact: %s, Email: %s, Country: %s, TZ: %s }", 
            ClientID, CompanyName, ContactName, EmailAddress, Country, TimeZone);
    }
}
