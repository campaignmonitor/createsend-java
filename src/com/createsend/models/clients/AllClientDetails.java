package com.createsend.models.clients;

public class AllClientDetails {
    public String ApiKey;
    public DetailedClientWithID BasicDetails;
    public AccessDetails AccessDetails;
    public BillingDetails BillingDetails;
    
    @Override
    public String toString() {
        return String.format("%s { Basic: %s, Access: %s, Billing: %s", 
            ApiKey, BasicDetails, AccessDetails, BillingDetails);
    }
}
