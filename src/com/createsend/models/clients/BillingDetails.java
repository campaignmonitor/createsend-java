/**
 * Copyright (c) 2011 Toby Brain
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend.models.clients;

public class BillingDetails {
    public boolean CanPurchaseCredits;
    public int Credits;
    public boolean ClientPays;
    
    public String Currency;
    
    public Integer MarkupPercentage;
    
    public Double MarkupOnDesignSpamTest;
    public Double BaseRatePerRecipient;
    public Double MarkupPerRecipient;
    public Double MarkupOnDelivery;
    public Double BaseDeliveryRate;
    public Double BaseDesignSpamTestRate;
    public String MonthlyScheme; // Should be either null, 'Basic' or 'Unlimited'
    
    @Override
    public String toString() {
        return String.format("{ CanPurchaseCredits: %s, MarkupOnDesignSpamTest: %s, ClientPays: %s, BaseRatePerRecipient: %s, MarkupPerRecipient: %s, MarkupOnDelivery: %s, BaseDeliveryRate: %s, Currency: %s, BaseDesignSpamTestRate: %s, MonthlyScheme %s }",
            CanPurchaseCredits, MarkupOnDesignSpamTest, ClientPays, BaseRatePerRecipient, 
            MarkupPerRecipient, MarkupOnDelivery, BaseDeliveryRate, Currency, BaseDesignSpamTestRate, MonthlyScheme);
    }
}
