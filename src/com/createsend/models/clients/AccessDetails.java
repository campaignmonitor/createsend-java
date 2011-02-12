package com.createsend.models.clients;

public class AccessDetails {
    public String Username;
    public String Password;
    public int AccessLevel;
    
    public String toString() {
        return String.format("{ Username: %s, Password: %s, Level: %s }", Username, Password, AccessLevel);
    }
}
