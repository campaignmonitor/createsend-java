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

import com.createsend.Administrators;
import com.createsend.Campaigns;
import com.createsend.Clients;
import com.createsend.General;
import com.createsend.Lists;
import com.createsend.People;
import com.createsend.Segments;
import com.createsend.Subscribers;
import com.createsend.Templates;
import com.createsend.models.administrators.Administrator;
import com.createsend.models.campaigns.CampaignForCreation;
import com.createsend.models.campaigns.CampaignForCreationFromTemplate;
import com.createsend.models.campaigns.EditableField;
import com.createsend.models.campaigns.PreviewData;
import com.createsend.models.campaigns.Repeater;
import com.createsend.models.campaigns.RepeaterItem;
import com.createsend.models.campaigns.TemplateContent;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.lists.CustomFieldForCreate;
import com.createsend.models.lists.List;
import com.createsend.models.lists.ListForUpdate;
import com.createsend.models.lists.UpdateFieldOptions;
import com.createsend.models.lists.Webhook;
import com.createsend.models.lists.WebhookTestFailureDetails;
import com.createsend.models.people.Person;
import com.createsend.models.people.PersonToAdd;
import com.createsend.models.segments.Rule;
import com.createsend.models.segments.RuleGroup;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.CustomField;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.models.subscribers.SubscriberToAdd;
import com.createsend.models.subscribers.SubscribersToAdd;
import com.createsend.models.templates.TemplateForCreate;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.BadRequestException;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.samples.AssertException;

public class SampleRunner {

	private static OAuthAuthenticationDetails auth = 
			new OAuthAuthenticationDetails("your access token", "your refresh token");

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
            runPeopleMethods(clientID);
            runAdminMethods();
        } catch (BadRequestException e) {
            e.printStackTrace();
            
            if(e.getResultData() != null) {
                System.err.printf("Exception result data: %s\n", e.getResultData());
            }
        } catch (CreateSendException e) {
            e.printStackTrace();
        }
        catch (AssertException e) {
            e.printStackTrace();
        }
    }
    
    private static void runTemplateMethods(String clientID) throws CreateSendException {
        Templates templateAPI = new Templates(auth);
        
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
        Lists listAPI = new Lists(auth);
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
        
        Subscribers subscriberAPI = new Subscribers(auth, listAPI.getListID());
        
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
        Lists listAPI = new Lists(auth);
        Date subscribersFrom = new Date();
        
        List list = new List();
        list.Title = "Java API Test List";
        list.ConfirmedOptIn = false;
        listAPI.create(clientID, list);
        
        Segments segmentAPI = new Segments(auth);
        
        Segment segment = new Segment();
        segment.Title = "Java Segment";
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.Rules = new Rule[] {new Rule()};
        ruleGroup.Rules[0].RuleType = "EmailAddress";
        ruleGroup.Rules[0].Clause = "CONTAINS gmail.com";
        segment.RuleGroups = new RuleGroup[] {ruleGroup};
        segmentAPI.create(listAPI.getListID(), segment);
        System.out.printf("Result of create segment: %s\n", segmentAPI.getSegmentID());
        System.out.printf("Result of segment details: %s\n", segmentAPI.details());
        System.out.printf("Result of segment active: %s\n",  segmentAPI.active(subscribersFrom, null, null, null, null));

        segment.Title = "New Java Test Segment";
        segment.RuleGroups[0].Rules[0].Clause = "CONTAINS hotmail.com";
        segmentAPI.update(segment);

        RuleGroup extraRuleGroup = new RuleGroup();
        extraRuleGroup.Rules = new Rule[] {new Rule()};
        extraRuleGroup.Rules[0].RuleType = "DateSubscribed";
        extraRuleGroup.Rules[0].Clause = "AFTER 2013-11-02";

        segmentAPI.addRuleGroup(extraRuleGroup);

        segmentAPI.deleteRules();

        segmentAPI.delete();
        listAPI.delete();
    }
    
    private static void runListMethods(String clientID) throws CreateSendException {
        Lists listAPI = new Lists(auth);
        Date subscribersFrom = new Date();
        
        List list = new List();
        list.Title = "Java API Test List";
        list.ConfirmedOptIn = false;
        list.UnsubscribeSetting = "OnlyThisList";
        listAPI.create(clientID, list);
        
        System.out.printf("Result of list create: %s\n", listAPI.getListID());
        System.out.printf("Result of list details: %s\n", listAPI.details());
        
        ListForUpdate listForUpdate = new ListForUpdate();
        listForUpdate.Title = "Edited Java List";
        listForUpdate.ConfirmedOptIn = list.ConfirmedOptIn;
        listForUpdate.UnsubscribeSetting = "AllClientLists";
        listForUpdate.AddUnsubscribesToSuppList = true;
        listForUpdate.ScrubActiveWithSuppList = true;
        listAPI.update(listForUpdate);
        
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

    private static void testCampaignCreationFromTemplate(
    		String clientID) throws CreateSendException {
    	Campaigns campaignAPI = new Campaigns(auth);

        // Prepare the template content
        TemplateContent templateContent = new TemplateContent();

        EditableField singleline = new EditableField();
        singleline.Content = "This is a heading";
        singleline.Href = "http://example.com/";
        EditableField[] singlelines = new EditableField[] { singleline };
        templateContent.Singlelines = singlelines;

        EditableField multiline = new EditableField();
        multiline.Content = "<p>This is example</p><p>multiline <a href=\"http://example.com\">content</a>...</p>";
        EditableField[] multilines = new EditableField[] { multiline };
        templateContent.Multilines = multilines;
        
        EditableField image = new EditableField();
        image.Content = "http://example.com/image.png";
        image.Alt = "This is alt text for an image";
        image.Href = "http://example.com/";
        EditableField[] images = new EditableField[] { image };
        templateContent.Images = images;

        RepeaterItem item = new RepeaterItem();
        item.Layout = "My layout";
        // Just using the same data for Singlelines, Multilines,
        // and Images as above in this example.
        item.Singlelines = singlelines;
        item.Multilines = multilines;
        item.Images = images;
        RepeaterItem[] items = new RepeaterItem[] { item };
        Repeater repeater = new Repeater();
        repeater.Items = items;
        Repeater[] repeaters = new Repeater[] { repeater };
        templateContent.Repeaters = repeaters;

        // templateContent as defined above would be used to fill the content of
        // a template with markup similar to the following:
        // <html>
        // <head><title>My Template</title></head>
        // <body>
        //     <p><singleline>Enter heading...</singleline></p>
        //     <div><multiline>Enter description...</multiline></div>
        //     <img id="header-image" editable="true" width="500" />
        //     <repeater>
        //     <layout label="My layout">
        //         <div class="repeater-item">
        //         <p><singleline></singleline></p>
        //         <div><multiline></multiline></div>
        //         <img editable="true" width="500" />
        //         </div>
        //     </layout>
        //     </repeater>
        //     <p><unsubscribe>Unsubscribe</unsubscribe></p>
        // </body>
        // </html>
		
		CampaignForCreationFromTemplate campaign = new CampaignForCreationFromTemplate();
		campaign.Name = "Campaign From Java Wrapper";
		campaign.Subject = "Campaign From Java Wrapper";
		campaign.FromName = "Example";
		campaign.FromEmail = "example@example.com";
		campaign.ReplyTo = "example@example.com";
		campaign.ListIDs = new String[] { "List ID One" };
		campaign.SegmentIDs = new String[0];
		campaign.TemplateID = "Template ID";
		campaign.TemplateContent = templateContent;
		
		campaignAPI.createFromTemplate(clientID, campaign);
    }
    
    private static void runCampaignMethods(String clientID) throws CreateSendException {
        Campaigns campaignAPI = new Campaigns(auth);
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
    
    private static void runClientMethods() throws AssertException, CreateSendException {
        Clients clientAPI = new Clients(auth);
        
        Client newClient = new Client();
        newClient.CompanyName = "Client Company Name";
        newClient.Country = "Client Country";
        newClient.TimeZone = "Client Timezone";
        
        newClient.ClientID = clientAPI.create(newClient);
        System.out.printf("Result of client create: %s\n", clientAPI.getClientID());

        newClient.CompanyName = "Edited Company Name";
        clientAPI.setBasics(newClient);
        
        String emailAddress = "example@example.com";
        clientAPI.unsuppress(emailAddress);
        System.out.printf("Unsuppressed email address: %s\n", emailAddress);

        BillingDetails billing = new BillingDetails();
        billing.ClientPays = true;
        billing.CanPurchaseCredits = true;
        billing.MarkupOnDelivery = 5.5;
        billing.MarkupPerRecipient = 6.1;
        billing.Currency = "AUD";
        clientAPI.setPaygBilling(billing);
        
        AllClientDetails details = clientAPI.details();
        
        if (details.BillingDetails.MonthlyScheme != null)  {
			throw new AssertException("Monthly Scheme set for PAYG when it shouldn't be");
        }

        String serialised = details.BillingDetails.toString();
        
        BillingDetails monthlyBilling = new BillingDetails();
        monthlyBilling.Currency = "USD";
        monthlyBilling.ClientPays = true;
        monthlyBilling.MarkupPercentage = 100;
        clientAPI.setMonthlyBilling(monthlyBilling);
        AllClientDetails monthlyDetails = clientAPI.details();
        
        if (!("Basic".equals(monthlyDetails.BillingDetails.MonthlyScheme)))  {
			throw new AssertException("Monthly Scheme is not Basic when expected");
        }
        
        serialised = monthlyDetails.BillingDetails.toString();
        
        monthlyBilling.MonthlyScheme = "Unlimited";
        clientAPI.setMonthlyBilling(monthlyBilling);
        monthlyDetails = clientAPI.details();
        
        if (!("Unlimited".equals(monthlyDetails.BillingDetails.MonthlyScheme)))  {
			throw new AssertException("Monthly Scheme is not Unlimited when expected");
        }

        monthlyBilling.MonthlyScheme = null;
        clientAPI.setMonthlyBilling(monthlyBilling);
        monthlyDetails = clientAPI.details();
        
        if (!("Unlimited".equals(monthlyDetails.BillingDetails.MonthlyScheme)))  {
			throw new AssertException("Monthly Scheme is not Unlimited when expected");
        }

        monthlyBilling.MonthlyScheme = "Basic";
        clientAPI.setMonthlyBilling(monthlyBilling);
        monthlyDetails = clientAPI.details();
        
        if (!("Basic".equals(monthlyDetails.BillingDetails.MonthlyScheme)))  {
			throw new AssertException("Monthly Scheme is not Basic when expected");
        }

        monthlyBilling.MonthlyScheme = null;
        clientAPI.setMonthlyBilling(monthlyBilling);
        monthlyDetails = clientAPI.details();
        
        if (!("Basic".equals(monthlyDetails.BillingDetails.MonthlyScheme)))  {
			throw new AssertException("Monthly Scheme is not Basic when expected");
        }
        
        clientAPI.setPaygBilling(billing);
        
        details = clientAPI.details();
        
        if (details.BillingDetails.MonthlyScheme != null)  {
			throw new AssertException("Monthly Scheme set for PAYG when it shouldn't be");
        }

        
    	System.out.printf("Result of client details: %s\n", details);
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

        System.out.printf("Result of get lists for email address: %s\n", 
                Arrays.deepToString(clientAPI.listsForEmailAddress("example@example.com")));
        
        System.out.printf("Result of get segments: %s\n", 
                Arrays.deepToString(clientAPI.segments()));
         
        System.out.printf("Result of get suppression: %s\n", 
            clientAPI.suppressionList(null, null, null, null));
    }
    
    private static void runGeneralMethods() throws CreateSendException {
        General client = new General(auth);
        
        System.out.printf("Result of get clients: %s\n", Arrays.deepToString(client.getClients()));
        
        String[] countries = client.getCountries();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(countries));
        
        String[] timezones = client.getTimezones();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(timezones));
        
        System.out.printf("Result of get systemdate: %s\n", client.getSystemDate());
                
    }
    
    private static void runPeopleMethods(String clientID) throws CreateSendException {
    	People people = new People(auth, clientID);
    	PersonToAdd person = new PersonToAdd();
    	person.EmailAddress = "this.is@notarealdomain.com";
    	person.Password = "djfdjffdj123";
    	person.AccessLevel = 1023;
    	person.Name = "NotaReal Person";
		people.add(person);
		Clients client = new Clients(auth, clientID);
						
		Person details = people.details(person.EmailAddress);
		details.Name = "NotaReal PersonUpdated";
		System.out.println("updated person: " +details.Name + " (" + details.EmailAddress + ")");
		people.update(details.EmailAddress, details);
				
		for(Person p: client.people()) {
			System.out.println("person: " + p.Name + " (" + p.EmailAddress + ")");
		}
		String currentPrimary = client.getPrimaryContact();
		System.out.println("current primary contact: " + currentPrimary);
		client.setPrimaryContact(person.EmailAddress);
		System.out.println("new primary contact: " + client.getPrimaryContact());
		client.setPrimaryContact(currentPrimary);
		people.delete(person.EmailAddress);
    }
        
    private static void runAdminMethods() throws CreateSendException {
    	Administrators admins = new Administrators(auth);
    	Administrator admin = new Administrator();
    	admin.EmailAddress = "this.is@notarealadmin.com";
    	admin.Name = "NotaReal Admin";
		admins.add(admin);
		General general = new General(auth);
						
		for(Administrator a: general.administrators()) {
			System.out.println("admin: " + a.Name + " (" + a.EmailAddress + ")");
		}
		
		admin.Name = "NotaReal AdminWithNewName";
		admins.update(admin.EmailAddress, admin);
		admin = admins.details(admin.EmailAddress);		
		
		String currentPrimary = general.getPrimaryContact();
		System.out.println("current primary contact: " + currentPrimary);
		admins.delete(admin.EmailAddress);
    }    
}
