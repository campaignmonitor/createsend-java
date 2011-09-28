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
import com.createsend.Lists;
import com.createsend.Segments;
import com.createsend.Subscribers;
import com.createsend.Templates;
import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.clients.AccessDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.lists.CustomFieldForCreate;
import com.createsend.models.lists.List;
import com.createsend.models.lists.UpdateFieldOptions;
import com.createsend.models.lists.Webhook;
import com.createsend.models.lists.WebhookTestFailureDetails;
import com.createsend.models.segments.Rule;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.CustomField;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.models.subscribers.SubscriberToAdd;
import com.createsend.models.subscribers.SubscribersToAdd;
import com.createsend.models.templates.TemplateForCreate;
import com.createsend.util.exceptions.BadRequestException;
import com.createsend.util.exceptions.CreateSendException;


public class SampleRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {        
        try { 
            String clientID = "Client ID";
            runGeneralMethods();
            runClientMethods();
            runCampaignMethods(clientID);
            runListMethods(clientID);
            runSegmentMethods(clientID);
            runSubscriberMethods(clientID);
            runTemplateMethods(clientID);
        } catch (BadRequestException e) {
            e.printStackTrace();
            
            if(e.getResultData() != null) {
                System.err.printf("Exception result data: %s\n", e.getResultData());
            }
        } catch (CreateSendException e) {
            e.printStackTrace();
        }
    }
    
    private static void runTemplateMethods(String clientID) throws CreateSendException {
        Templates templateAPI = new Templates();
        
        TemplateForCreate template = new TemplateForCreate();
        template.Name = "Java Test Template";
        template.HtmlPageURL = URI.create("Template HTML");
        template.ZipFileURL = URI.create("Template ZIP");
        templateAPI.create(clientID, template);
        
        System.out.printf("Result of get template details: %s\n", templateAPI.get());
        
        template.Name = "Edited Java Template";
        templateAPI.update(template);

        System.out.printf("Result of get template details: %s\n", templateAPI.get());
        templateAPI.delete();
    }
    
    @SuppressWarnings("deprecation")
    private static void runSubscriberMethods(String clientID) throws CreateSendException { 
        Lists listAPI = new Lists();
        Date subscribersFrom = new Date();
        subscribersFrom.setHours(0);
        
        List list = new List();
        list.Title = "Java API Test List";
        list.ConfirmedOptIn = false;
        listAPI.create(clientID, list);        
        
        CustomFieldForCreate customField = new CustomFieldForCreate();
        customField.DataType = "Text";
        customField.FieldName = "Website";
        
        String key = listAPI.createCustomField(customField);
        
        Subscribers subscriberAPI = new Subscribers(listAPI.getListID());
        
        SubscriberToAdd subscriber = new SubscriberToAdd();
        subscriber.Resubscribe = true;
        subscriber.EmailAddress = "Subscriber Email 1";
        subscriber.Name = "Java Test Sub 1";
        subscriber.CustomFields = new CustomField[] {
            new CustomField()
        };
        subscriber.CustomFields[0].Key = key;
        subscriber.CustomFields[0].Value = "http://www.example.com";
        
        System.out.printf("Result of subscriber add: %s\n", subscriberAPI.add(subscriber));
        
        String originalEmail = subscriber.EmailAddress;
        subscriber.EmailAddress = "New Subscriber Email Address";
        subscriber.CustomFields = null; // We don't want to update any custom fields
        subscriber.Name = "New Subscriber Name";
        subscriberAPI.update(originalEmail, subscriber);  
        
        System.out.printf("Result of subscriber details: %s\n", subscriberAPI.details(subscriber.EmailAddress));
        System.out.printf("Result of subscriber history: %s\n", 
            Arrays.deepToString(subscriberAPI.history(subscriber.EmailAddress)));
        subscriberAPI.unsubscribe(subscriber.EmailAddress);      
        
        SubscribersToAdd subscribers = new SubscribersToAdd();
        subscribers.Resubscribe = true;
        subscribers.Subscribers = new Subscriber[] {
            new Subscriber(), new Subscriber(), new Subscriber()
        };
        
        subscribers.Subscribers[0].EmailAddress = "Subscriber Email 1";
        subscribers.Subscribers[0].CustomFields = new CustomField[] { new CustomField() };
        subscribers.Subscribers[0].CustomFields[0].Key = key;
        subscribers.Subscribers[0].CustomFields[0].Clear = true; // remove website from this existing subscriber
        
        subscribers.Subscribers[1].EmailAddress = "Subscriber Email 2";
        
        subscribers.Subscribers[2].EmailAddress = "Subscriber Email 3";
        subscribers.Subscribers[2].CustomFields = new CustomField[] { new CustomField() };
        subscribers.Subscribers[2].CustomFields[0].Key = key;
        subscribers.Subscribers[2].CustomFields[0].Value = "http://www.google.com";
        subscriberAPI.addMany(subscribers);
        
        System.out.printf("Result of list active: %s\n", 
                listAPI.active(subscribersFrom, null, null, null, null));
        
        subscriberAPI.delete("Subscriber Email 1");
        
        listAPI.delete();
    }
    
    private static void runSegmentMethods(String clientID) throws CreateSendException {
        Lists listAPI = new Lists();
        Date subscribersFrom = new Date();
        
        List list = new List();
        list.Title = "Java API Test List";
        list.ConfirmedOptIn = false;
        listAPI.create(clientID, list);
        
        Segments segmentAPI = new Segments();
        
        Segment segment = new Segment();
        segment.Title = "Java Test Segment";
        segment.Rules = new Rule[] {
            new Rule()
        };
        segment.Rules[0].Subject = "EmailAddress";
        segment.Rules[0].Clauses = new String[] {
            "CONTAINS gmail.com"
        };
        
        segmentAPI.create(listAPI.getListID(), segment);
        System.out.printf("Result of create segment: %s\n", segmentAPI.getSegmentID());
        
        System.out.printf("Result of segment details: %s\n", segmentAPI.details());
        System.out.printf("Result of segment active: %s\n", 
            segmentAPI.active(subscribersFrom, null, null, null, null));
        
        segment.Title = "New Java Test Segment";
        segment.Rules[0].Clauses[0] = "CONTAINS hotmail.com";
        segmentAPI.update(segment);
        
        segmentAPI.deleteRules();
        
        segment.Rules[0].Subject = "DateSubscribed";
        segment.Rules[0].Clauses[0] = "AFTER 2009-01-01";
        segmentAPI.addRule(segment.Rules[0]);
        
        segmentAPI.delete();        
        listAPI.delete();
    }
    
    private static void runListMethods(String clientID) throws CreateSendException {
        Lists listAPI = new Lists();
        Date subscribersFrom = new Date();
        
        List list = new List();
        list.Title = "Java API Test List";
        list.ConfirmedOptIn = false;
        listAPI.create(clientID, list);
        
        System.out.printf("Result of list create: %s\n", listAPI.getListID());
        System.out.printf("Result of list details: %s\n", listAPI.details());
        
        list.Title = "Edited Java List";
        listAPI.update(list);
        
        System.out.printf("Result of list details: %s\n", listAPI.details());
        System.out.printf("Result of list stats: %s\n", listAPI.stats());
        System.out.printf("Result of list segments: %s\n", 
            Arrays.deepToString(listAPI.segments()));
                
        System.out.printf("Result of list active: %s\n", 
            listAPI.active(subscribersFrom, null, null, null, null));
        System.out.printf("Result of list unsubscribed: %s\n", 
                listAPI.unsubscribed(subscribersFrom, null, null, null, null));
        System.out.printf("Result of list bounced: %s\n", 
                listAPI.bounced(subscribersFrom, null, null, null, null));
        System.out.printf("Result of list deleted: %s\n", 
                listAPI.deleted(subscribersFrom, null, null, null, null));
        
        CustomFieldForCreate customField = new CustomFieldForCreate();
        customField.DataType = "MultiSelectOne";
        customField.FieldName = "Java Wrapper Field";
        customField.Options = new String[] { "Option 1", "Option 2" };

        String key = listAPI.createCustomField(customField);
        System.out.printf("Result of create custom field: %s\n", key);
        System.out.printf("Result of list custom fields: %s\n", 
            Arrays.deepToString(listAPI.customFields()));
        
        UpdateFieldOptions options = new UpdateFieldOptions();
        options.KeepExistingOptions = true;
        options.Options = new String[] { "Option 3" };
        listAPI.updateCustomFieldOptions(key, options);
        
        listAPI.deleteCustomField(key);
        
        Webhook webhook = new Webhook();
        webhook.Events = new String[] { "Subscribe" };
        webhook.PayloadFormat = "json";
        webhook.Url = URI.create("Webhook URL");
        webhook.WebhookID = listAPI.createWebhook(webhook);
        
        System.out.printf("Result of create webhook: %s\n", webhook.WebhookID);
        listAPI.deactivateWebhook(webhook.WebhookID); 
        listAPI.activateWebhook(webhook.WebhookID); 
        
        try {
            listAPI.testWebhook(webhook.WebhookID);
        } catch (BadRequestException e) {
            e.printStackTrace();
            if(e.getResultData() != null) {
                WebhookTestFailureDetails testDetails = (WebhookTestFailureDetails)e.getResultData();
                System.out.printf("Webhook failure details: %s\n", testDetails);
            }
        }
        
        listAPI.deleteWebhook(webhook.WebhookID);
        
        listAPI.delete();
    }

    
    private static void runCampaignMethods(String clientID) throws CreateSendException {
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

        campaignAPI.create(clientID, newCampaign);
        System.out.printf("Result of campaign create: %s\n", campaignAPI.getCampaignID());
        campaignAPI.delete();

        campaignAPI.create(clientID, newCampaign);
        System.out.printf("Result of campaign create: %s\n", campaignAPI.getCampaignID());
        
        PreviewData data = new PreviewData();
        data.Personalize = "Random";
        data.PreviewRecipients = new String[] { "Preview Recipient" };
        campaignAPI.test(data);
        
        Date scheduledDate = new Date();
        campaignAPI.send("Confirmation Email", scheduledDate);

        System.out.printf("Result of get campaign summary: %s\n", 
            campaignAPI.summary());

        System.out.printf("Result of get campaign bounces: %s\n", 
            campaignAPI.bounces(resultsAfter, null, null, null, null));
        
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
        
        campaignAPI.unschedule();
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
        
        System.out.printf("Result of get scheduled campaigns: %s\n", 
                Arrays.deepToString(clientAPI.scheduledCampaigns()));
        
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
