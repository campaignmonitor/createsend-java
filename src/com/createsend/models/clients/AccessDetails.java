package com.createsend.models.clients;

public class AccessDetails {
    public String Username;
    public int AccessLevel;
    
    public String toString() {
        return String.format("{ Username: %s, Level: %s }", Username, AccessLevel);
    }
}
