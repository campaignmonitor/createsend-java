package com.createsend.models.subscribers;

public class EmailToUnsubscribe {
    public String EmailAddress;
    
    public static EmailToUnsubscribe fromString(String emailAddress) {
        EmailToUnsubscribe obj = new EmailToUnsubscribe();
        obj.EmailAddress = emailAddress;
        
        return obj;
    }
}
