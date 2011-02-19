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
package com.createsend.samples;

import java.util.Arrays;

import com.createsend.Clients;
import com.createsend.General;
import com.createsend.models.clients.AccessDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.util.exceptions.CreateSendException;


public class SampleRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {        
        try { 
            runGeneralMethods();
            runClientMethods();
        } catch (CreateSendException e) {
            e.printStackTrace();
        }
    }
    
    private static void runClientMethods() throws CreateSendException {
        Clients clientAPI = new Clients();
        
        Client newClient = new Client();
        newClient.CompanyName = "Client Company Name";
        newClient.ContactName = "Client Contact Name";
        newClient.Country = "Client Country";
        newClient.EmailAddress = "Client Email Address";
        newClient.TimeZone = "Client Timezone";
        
        newClient.ClientID = clientAPI.create(newClient);
        System.out.printf("Result of client create: %s\n", clientAPI.getClientID());

        newClient.CompanyName = "Edited Company Name";
        clientAPI.setBasics(newClient);
        
        AccessDetails access = new AccessDetails();
        access.Username = "Username";
        access.Password = "Password";
        access.AccessLevel = 23;
        clientAPI.setAccess(access);
        
        BillingDetails billing = new BillingDetails();
        billing.ClientPays = true;
        billing.CanPurchaseCredits = true;
        billing.MarkupOnDelivery = 5.5;
        billing.MarkupPerRecipient = 6.1;
        billing.Currency = "AUD";
        clientAPI.setPaygBilling(billing);
        
        System.out.printf("Result of client details: %s\n", clientAPI.details());
        clientAPI.delete();
        
        clientAPI.setClientID("Other Client ID");
        System.out.printf("Result of get sent campaigns: %s\n", 
                Arrays.deepToString(clientAPI.sentCampaigns()));
        
        System.out.printf("Result of get draft campaigns: %s\n", 
                Arrays.deepToString(clientAPI.draftCampaigns()));
        
        System.out.printf("Result of get lists: %s\n", 
                Arrays.deepToString(clientAPI.lists()));
        
        System.out.printf("Result of get segments: %s\n", 
                Arrays.deepToString(clientAPI.segments()));
         
        System.out.printf("Result of get suppression: %s\n", 
            clientAPI.suppressionList(null, null, null, null));
    }
    
    private static void runGeneralMethods() throws CreateSendException {
        General client = new General();
        
        System.out.printf("Result of get apikey: %s\n", 
            client.getAPIKey("Site URL", "Username", "Password"));
        
        System.out.printf("Result of get clients: %s\n", Arrays.deepToString(client.getClients()));
        
        String[] countries = client.getCountries();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(countries));
        
        String[] timezones = client.getTimezones();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(timezones));
        
        System.out.printf("Result of get systemdate: %s\n", client.getSystemDate());
    }
}
