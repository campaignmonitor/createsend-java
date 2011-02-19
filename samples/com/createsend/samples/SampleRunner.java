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

import java.net.URI;
import java.util.Arrays;
import java.util.Date;

import com.createsend.Campaigns;
import com.createsend.Clients;
import com.createsend.General;
import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.PreviewData;
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
            runCampaignMethods();
        } catch (CreateSendException e) {
            e.printStackTrace();
        }
    }
    
    private static void runCampaignMethods() throws CreateSendException {
        Campaigns campaignAPI = new Campaigns();
        Date resultsAfter = new Date();
        
        CampaignForCreation newCampaign = new CampaignForCreation();
        newCampaign.FromEmail = "From Email";
        newCampaign.FromName = "Java Wrapper";
        newCampaign.HtmlUrl = URI.create(
            "http://www.campaignmonitor.com/uploads/templates/previews/template-1-left-sidebar/index.html");
        newCampaign.TextUrl = URI.create(
            "http://www.campaignmonitor.com/uploads/templates/previews/template-1-left-sidebar/textversion.txt");
        newCampaign.ListIDs = new String[] { "List ID" };
        newCampaign.Name = "API Testing Campaign: " + new Date();
        newCampaign.ReplyTo = "Reply To Email";
        newCampaign.SegmentIDs = new String[0];
        newCampaign.Subject = "Java Wrapper Test: " + new Date();

        campaignAPI.create("Client ID", newCampaign);
        System.out.printf("Result of campaign create: %s\n", campaignAPI.getCampaignID());
        campaignAPI.delete();

        campaignAPI.create("Client ID", newCampaign);
        System.out.printf("Result of campaign create: %s\n", campaignAPI.getCampaignID());
        
        PreviewData data = new PreviewData();
        data.Personalize = "Random";
        data.PreviewRecipients = new String[] { "Preview Recipient" };
        campaignAPI.test(data);
        
        campaignAPI.send("Confirmation Email", null);

        System.out.printf("Result of get campaign summary: %s\n", 
            campaignAPI.summary());

        System.out.printf("Result of get campaign bounces: %s\n", 
            campaignAPI.bounces(null, null, null, null));
        
        System.out.printf("Result of get campaign clicks: %s\n", 
            campaignAPI.clicks(resultsAfter, null, null, null, null));
        
        System.out.printf("Result of get campaign opens: %s\n", 
            campaignAPI.opens(resultsAfter, null, null, null, null));
        
        System.out.printf("Result of get campaign recipients: %s\n", 
            campaignAPI.recipients(null, null, null, null));
        
        System.out.printf("Result of get campaign unsubscribes: %s\n", 
            campaignAPI.unsubscribes(resultsAfter, null, null, null, null));
        
        System.out.printf("Result of get campaign lists and segments: %s\n", 
            campaignAPI.listsAndSegments());
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
