package com.createsend.models.subscribers;

import java.util.Date;

public class Subscriber {
    public String EmailAddress;
    public Date Date;
    public String State; // TODO: Probably want to move this to an enum
    
    @Override
    public String toString() {
        return String.format("{ EmailAddress: %s, Date: %s, State: %s }",
                EmailAddress, Date, State);
    }
}
