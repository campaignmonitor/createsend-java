package com.createsend.models.clients;

public class BillingDetails {
    public boolean CanPurchaseCredits;
    public boolean ClientPays;
    
    public String Currency;
    
    public Integer MarkupPercentage;
    
    public Double MarkupOnDesignSpamTest;
    public Double BaseRatePerRecipient;
    public Double MarkupPerRecipient;
    public Double MarkupOnDelivery;
    public Double BaseDeliveryRate;
    public Double BaseDesignSpamTestRate;
    
    @Override
    public String toString() {
        return String.format("{ CanPurchaseCredits: %s, MarkupOnDesignSpamTest: %s, ClientPays: %s, BaseRatePerRecipient: %s, MarkupPerRecipient: %s, MarkupOnDelivery: %s, BaseDeliveryRate: %s, Currency: %s, BaseDesignSpamTestRate: %s }",
            CanPurchaseCredits, MarkupOnDesignSpamTest, ClientPays, BaseRatePerRecipient, 
            MarkupPerRecipient, MarkupOnDelivery, BaseDeliveryRate, Currency, BaseDesignSpamTestRate);
    }
}
