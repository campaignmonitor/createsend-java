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

import com.createsend.models.ApiKey;
import com.createsend.models.SystemDate;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Provides methods for accessing all  
 * <a href="http://www.campaignmonitor.com/api/account/" target="_blank">Account</a>
 * methods in the Campaign Monitor API
 *
 */
public class General extends BaseClient {
    
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
        WebResource r = getAPIResource().path("apikey.json").queryParam("siteurl", siteAddress);
        r.addFilter(new HTTPBasicAuthFilter(username, password));
        
        try { 
            return r.get(ApiKey.class).ApiKey;   
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
    
    /**
     * @return An array of active clients in the create send account. 
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_clients" target="_blank">
     * Getting your clients</a>
     * @throws CreateSendException
     */
    public ClientBasics[] getClients() throws CreateSendException {
        return get(ClientBasics[].class, "clients.json");
    }
    
    /**
     * @return An array of valid countries for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_countries" target="_blank">
     * Getting valid countries</a>
     * @throws CreateSendException
     */
    public String[] getCountries() throws CreateSendException {
        return get(String[].class, "countries.json");
    }
    
    /**
     * @return An array of valid timezones for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_timezones" target="_blank">
     * Getting valid timezones</a>
     * @throws CreateSendException
     */
    public String[] getTimezones() throws CreateSendException {
        return get(String[].class, "timezones.json");
    }
    
    /**
     * @return The current date in the timezone of the authorised account
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_systemdate" target="_blank">
     * Getting the current date</a>
     * @throws CreateSendException
     */
    public Date getSystemDate() throws CreateSendException {
        return get(SystemDate.class, "systemdate.json").SystemDate;
    }
}
