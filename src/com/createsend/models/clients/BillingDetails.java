package com.createsend.models.clients;

public class BillingDetails {
    public boolean CanPurchaseCredits;
    public double MarkupOnDesignSpamTest;
    public boolean ClientPays;
    public double BaseRatePerRecipient;
    public double MarkupPerRecipient;
    public double MarkupOnDelivery;
    public double BaseDeliveryRate;
    public String Currency;
    public double BaseDesignSpamTestRate;
    
    @Override
    public String toString() {
        return String.format("{ CanPurchaseCredits: %s, MarkupOnDesignSpamTest: %s, ClientPays: %s, BaseRatePerRecipient: %s, MarkupPerRecipient: %s, MarkupOnDelivery: %s, BaseDeliveryRate: %s, Currency: %s, BaseDesignSpamTestRate: %s }",
            CanPurchaseCredits, MarkupOnDesignSpamTest, ClientPays, BaseRatePerRecipient, 
            MarkupPerRecipient, MarkupOnDelivery, BaseDeliveryRate, Currency, BaseDesignSpamTestRate);
    }
}
