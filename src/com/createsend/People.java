package com.createsend;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.people.Person;
import com.createsend.models.people.PersonResult;
import com.createsend.models.people.PersonToAdd;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class People extends CreateSendBase {
	private String clientID;

    /**
     * Constructor.
     * @param auth The authentication details to use when making API calls.
     * May be either an OAuthAuthenticationDetails or
     * ApiKeyAuthenticationDetails instance.
     * @param clientID The Client ID to use when making API calls.
     */
	public People(AuthenticationDetails auth, String clientID) {
		this.setClientID(clientID);
		this.jerseyClient = new JerseyClientImpl(auth);
	}
	
	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

    /**
     * Adds a person to the client. If a password is not supplied, an email invitation will be sent to the 
     * person. Otherwise the person will be added with the specified password and immediately activated
     * @param person The person to add to the client
     * @return The email address of the newly added person
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#adding_a_person" target="_blank">
     * Adding a person</a>
     */
    public String add(PersonToAdd person) throws CreateSendException {
        return jerseyClient.post(PersonResult.class, person, "clients", clientID, "people" + ".json").EmailAddress;
    }
    
    /**
     * Gets the details for the person with the given email address
     * @param emailAddress The email address to get the person details for
     * @return The details of the person
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#getting_client_person" target="_blank">
     * Getting person details</a>
     */
    public Person details(String emailAddress) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        return jerseyClient.get(Person.class, queryString, "clients", clientID, "people" + ".json");
    }   
    
    /**
     * Deletes the person with the specified email address from the client
     * @param emailAddress The email address of the person to delete
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#deleting_a_person" target="_blank">
     * Deleting a person</a>
     */
    public void delete(String emailAddress) throws CreateSendException {
    	MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", emailAddress);
        
        jerseyClient.delete(queryString, "clients", clientID, "people" + ".json");
    }
    
    /**
     * Updates the details for an existing person
     * @param originalEmailAddress The current email address of the existing person
     * @param newDetails The new details for the person.
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/clients/#updating_a_person" target="_blank">
     * Updating a person</a>
     */
    public void update(String originalEmailAddress, Person newDetails) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("email", originalEmailAddress);
        
        jerseyClient.put(newDetails, queryString, "clients", clientID, "people" + ".json");
    }
}
