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
package com.createsend;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.ApiKey;
import com.createsend.models.SystemDate;
import com.createsend.models.administrators.Administrator;
import com.createsend.models.administrators.AdministratorResult;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.JerseyClient;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.AuthorisedResourceFactory;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Provides methods for accessing all  
 * <a href="http://www.campaignmonitor.com/api/account/" target="_blank">Account</a>
 * methods in the Campaign Monitor API
 *
 */
public class General {
    private JerseyClient client;

    /**
     * Constructor.
     */
    public General() {
        this(new JerseyClientImpl());
    }

    /**
     * Constructor which specifies to use an OAuth access token when making 
     * API requests, and a refresh token to use when the access token expires.
     * @param accessToken The access token to use when making API requests.
     * @param refreshToken The refresh token to use to refresh the access token
     * when the access token expires.
     */
    public General(String accessToken, String refreshToken) {
    	this(new JerseyClientImpl(accessToken, refreshToken));
    }

    /**
     * Constructor which specifies to use an API key when making API requests.
     * @param apiKey The API key to use for making API requests.
     */
    public General(String apiKey) {
    	this(new JerseyClientImpl(apiKey));
    }
    
    /**
     * Constructor
     * @param client The {@link com.createsend.util.JerseyClient} to use for API requests
     */
    public General(JerseyClient client) {
        this.client = client;
    }
    
    /**
     * Gets the API Key to use with the given authentication data
     * @param siteAddress The create send site address for the account
     * @param username The username used to login
     * @param password The password of the account
     * @return The API key for the specified authentication data
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_api_key" target="_blank">
     * Getting you API Key</a>
     * @throws CreateSendException
     */
    public String getAPIKey(String siteAddress, String username, String password) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("siteurl", siteAddress);
        
        return client.get(ApiKey.class, queryString, 
            new AuthorisedResourceFactory(username, password), "apikey.json").ApiKey;
    }
    
    /**
     * @return An array of active clients in the create send account. 
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_clients" target="_blank">
     * Getting your clients</a>
     * @throws CreateSendException
     */
    public ClientBasics[] getClients() throws CreateSendException {
        return client.get(ClientBasics[].class, "clients.json");
    }

    /**
     * @return Gets your billing details. 
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_billing_details" target="_blank">
     * Getting your billing details</a>
     * @throws CreateSendException
     */
    public com.createsend.models.BillingDetails getBillingDetails() throws CreateSendException {
        return client.get(com.createsend.models.BillingDetails.class, "billingdetails.json");
    }

    /**
     * @return An array of valid countries for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_countries" target="_blank">
     * Getting valid countries</a>
     * @throws CreateSendException
     */
    public String[] getCountries() throws CreateSendException {
        return client.get(String[].class, "countries.json");
    }
    
    /**
     * @return An array of valid timezones for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_timezones" target="_blank">
     * Getting valid timezones</a>
     * @throws CreateSendException
     */
    public String[] getTimezones() throws CreateSendException {
        return client.get(String[].class, "timezones.json");
    }
    
    /**
     * @return The current date in the timezone of the authorised account
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_systemdate" target="_blank">
     * Getting the current date</a>
     * @throws CreateSendException
     */
    public Date getSystemDate() throws CreateSendException {
        return client.get(SystemDate.class, "systemdate.json").SystemDate;
    }
    
    public Administrator[] administrators() throws CreateSendException {
    	return client.get(Administrator[].class, "admins.json");
    }
    
    public String getPrimaryContact() throws CreateSendException {
    	return client.get(AdministratorResult.class, "primarycontact.json").EmailAddress;
    } 
    
    public void setPrimaryContact(String emailAddress) throws CreateSendException {
    	MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        client.put("", queryString, "primarycontact.json");
    }
}
