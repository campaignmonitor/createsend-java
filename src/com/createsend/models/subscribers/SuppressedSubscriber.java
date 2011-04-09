package com.createsend.models.subscribers;

public class SuppressedSubscriber extends Subscriber {
    public String SuppressionReason;
    
    @Override
    public String toString() {
        return String.format("{ Subscriber: %s, SuppressionReason: %s }", super.toString(), SuppressionReason);
    }
}
