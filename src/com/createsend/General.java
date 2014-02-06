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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.ApiKey;
import com.createsend.models.ExternalSessionOptions;
import com.createsend.models.ExternalSessionResult;
import com.createsend.models.OAuthTokenDetails;
import com.createsend.models.SystemDate;
import com.createsend.models.administrators.Administrator;
import com.createsend.models.administrators.AdministratorResult;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.Configuration;
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
public class General extends CreateSendBase {

    /**
     * Constructor.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
    public General(AuthenticationDetails auth) {
    	this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * Get the authorization URL for your application, given the application's
     * Client ID, Redirect URI, Scope, and optional State data.
     * @param clientID The Client ID value for your application.
     * @param redirectUri The Redirect URI value for your application.
     * @param scope The permission scope your application is requesting.
     * @param state Optional state data to include in the authorization URL.
     * @return The authorization URL to which your application should redirect
     * your users.
     */
    public static String getAuthorizeUrl(
    	int    clientID,
    	String redirectUri,
    	String scope,
    	String state) {
        String qs = "client_id=" + String.valueOf(clientID);
        try {
			qs += "&redirect_uri=" + URLEncoder.encode(redirectUri, urlEncodingScheme);
			qs += "&scope=" + URLEncoder.encode(scope, urlEncodingScheme);
			if (state != null)
				qs += "&state=" + URLEncoder.encode(state, urlEncodingScheme);
		} catch (UnsupportedEncodingException e) {
			qs = null;
		}
    	return Configuration.Current.getOAuthBaseUri() + "?" + qs;
    }

    /**
     * Exchange a provided OAuth code for an OAuth access token, 'expires in'
     * value, and refresh token.
     * @param clientID The Client ID value for your application.
     * @param clientSecret The Client Secret value for your application.
     * @param redirectUri The Redirect URI value for your application.
     * @param code A unique code provided to your user which can be exchanged
     * for an access token.
     * @return An OAuthTokenDetails object containing the access token,
     * 'expires in' value, and refresh token.
     */
    public static OAuthTokenDetails exchangeToken(
    	int    clientID,
    	String clientSecret,
    	String redirectUri,
    	String code) throws CreateSendException {

    	JerseyClient oauthClient = new JerseyClientImpl(null);
    	String body = "grant_type=authorization_code";
    	try {
        	body += "&client_id=" + String.valueOf(clientID);
        	body += "&client_secret=" + URLEncoder.encode(clientSecret, urlEncodingScheme);
        	body += "&redirect_uri=" + URLEncoder.encode(redirectUri, urlEncodingScheme);
        	body += "&code=" + URLEncoder.encode(code, urlEncodingScheme);
		} catch (UnsupportedEncodingException e) {
			body = null;
		}
    	
    	// TODO: Use a custom error deserialiser in the following post

    	OAuthTokenDetails result = oauthClient.post(
    			Configuration.Current.getOAuthBaseUri(), OAuthTokenDetails.class, body,
    			MediaType.APPLICATION_FORM_URLENCODED_TYPE, "token");
    	return result;
    }

    /**
     * @return An array of active clients in the create send account. 
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_clients" target="_blank">
     * Getting your clients</a>
     * @throws CreateSendException
     */
    public ClientBasics[] getClients() throws CreateSendException {
        return jerseyClient.get(ClientBasics[].class, "clients.json");
    }

    /**
     * @return Gets your billing details. 
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_your_billing_details" target="_blank">
     * Getting your billing details</a>
     * @throws CreateSendException
     */
    public com.createsend.models.BillingDetails getBillingDetails() throws CreateSendException {
        return jerseyClient.get(com.createsend.models.BillingDetails.class, "billingdetails.json");
    }

    /**
     * @return An array of valid countries for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_countries" target="_blank">
     * Getting valid countries</a>
     * @throws CreateSendException
     */
    public String[] getCountries() throws CreateSendException {
        return jerseyClient.get(String[].class, "countries.json");
    }
    
    /**
     * @return An array of valid timezones for use in the Create Send API
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_timezones" target="_blank">
     * Getting valid timezones</a>
     * @throws CreateSendException
     */
    public String[] getTimezones() throws CreateSendException {
        return jerseyClient.get(String[].class, "timezones.json");
    }
    
    /**
     * @return The current date in the timezone of the authorised account
     * @see <a href="http://www.campaignmonitor.com/api/account/#getting_systemdate" target="_blank">
     * Getting the current date</a>
     * @throws CreateSendException
     */
    public Date getSystemDate() throws CreateSendException {
        return jerseyClient.get(SystemDate.class, "systemdate.json").SystemDate;
    }
    
    public Administrator[] administrators() throws CreateSendException {
    	return jerseyClient.get(Administrator[].class, "admins.json");
    }
    
    public String getPrimaryContact() throws CreateSendException {
    	return jerseyClient.get(AdministratorResult.class, "primarycontact.json").EmailAddress;
    } 
    
    public void setPrimaryContact(String emailAddress) throws CreateSendException {
    	MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        jerseyClient.put("", queryString, "primarycontact.json");
    }

    /**
     * Get a URL which initiates a new external session for the user with the
     * given email.
     * Full details: http://www.campaignmonitor.com/api/account/#single_sign_on
     * @param sessionOptions The session options to use.
     * @return The URL to initiate the external Campaign Monitor session.
     * @throws CreateSendException
     */
    public String getExternalSessionUrl(ExternalSessionOptions sessionOptions) 
    		throws CreateSendException {
    	return jerseyClient.put(ExternalSessionResult.class, sessionOptions, 
    			"externalsession.json").SessionUrl;
    }
}
