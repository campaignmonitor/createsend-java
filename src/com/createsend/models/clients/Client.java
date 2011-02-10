package com.createsend.models.clients;

public class Client {
    public String ClientID;
    public String CompanyName;
    public String ContactName;
    public String EmailAddress;
    public String Country;
    public String TimeZone;
    
    public String toString() { 
        return String.format("{ ID: %s, Company: %s, Contact: %s, Email: %s, Country: %s, TZ: %s }", 
            ClientID, CompanyName, ContactName, EmailAddress, Country, TimeZone);
    }
}
