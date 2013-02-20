package com.createsend;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.administrators.Administrator;
import com.createsend.models.administrators.AdministratorResult;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Administrators extends CreateSendBase {

    /**
     * Constructor.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     */
	public Administrators(AuthenticationDetails auth) {
		this.jerseyClient = new JerseyClientImpl(auth);
	}

	 /**
     * Adds an administrator to the account.
     * @param person The administrator to add to the account
     * @return The email address of the newly added administrator
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#adding_a_subscriber" target="_blank">
     * Adding a subscriber</a>
     */
    public String add(Administrator person) throws CreateSendException {
        return jerseyClient.post(AdministratorResult.class, person, "admins.json").EmailAddress;
    }
    
    /**
     * Gets the details for the administrator with the given email address
     * @param emailAddress The email address of the administrator to get the details for
     * @return The details of the person
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#getting_subscriber_details" target="_blank">
     * Getting subscriber details</a>
     */
    public Administrator details(String emailAddress) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        return jerseyClient.get(Administrator.class, queryString, "admins.json");
    }   
    
    /**
     * Deletes the administrator with the specified email address from this account
     * @param emailAddress The email address of the administrator to delete
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#deleting_a_subscriber" target="_blank">
     * Deleting a subscriber</a>
     */
    public void delete(String emailAddress) throws CreateSendException {
    	MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        jerseyClient.delete(queryString, "admins.json");
    }
    
    /**
     * Updates the details for an existing administrator
     * @param originalEmailAddress The current email address of the existing administrator
     * @param newDetails The new details for the administrator.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/subscribers/#updating_a_subscriber" target="_blank">
     * Updating a subscriber</a>
     */
    public void update(String originalEmailAddress, Administrator newDetails) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", originalEmailAddress);
        
        jerseyClient.put(newDetails, queryString, "admins.json");
    }
}
