package com.createsend.samples;

import java.util.Arrays;

import com.createsend.Clients;
import com.createsend.General;
import com.createsend.models.clients.Client;

import exceptions.CreateSendException;

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
        
        String clientID = clientAPI.create(newClient);
        System.out.printf("Result of client create: %s\n", clientID);
        
        System.out.printf("Result of client details: %s\n", clientAPI.details(clientID));
        
        System.out.printf("Result of get sent campaigns: %s\n", 
                Arrays.deepToString(clientAPI.sentCampaigns("Client ID")));
        
        System.out.printf("Result of get draft campaigns: %s\n", 
                Arrays.deepToString(clientAPI.draftCampaigns("Client ID")));
        
        System.out.printf("Result of get lists: %s\n", 
                Arrays.deepToString(clientAPI.lists("Client ID")));
    }
    
    private static void runGeneralMethods() throws CreateSendException {
        General client = new General();
        
        System.out.printf("Result of get apikey: %s\n", 
            client.getAPIKey("Site URL", "Username", "Password"));
        
        Client[] clients = client.getClients();
        System.out.printf("Result of get clients: %s\n", Arrays.deepToString(clients));
        
        String[] countries = client.getCountries();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(countries));
        
        String[] timezones = client.getTimezones();
        System.out.printf("Result of get countries: %s\n", Arrays.deepToString(timezones));
        
        System.out.printf("Result of get systemdate: %s\n", client.getSystemDate());
    }
}
