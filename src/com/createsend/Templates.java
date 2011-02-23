package com.createsend;

import com.createsend.models.templates.TemplateDetails;
import com.createsend.models.templates.TemplateForCreate;
import com.createsend.util.JerseyClient;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;

public class Templates {
    private String templateID;
    private JerseyClient client;
    
    /**
     * Constructor.
     * Use this for creating new templates.
     */
    public Templates() {
        this(null);
    }
    
    /**
     * Constructor.
     * @param templateID The ID of the template to apply calls to.
     */
    public Templates(String templateID) {
        this(templateID, new JerseyClientImpl());
    }
    
    public Templates(String templateID, JerseyClient client) {
        setTemplateID(templateID);
        this.client = client;
    }
    
    /**
     * Gets the current template ID.
     * @return The current template ID.
     */
    public String getTemplateID() {
        return templateID;
    }

    /**
     * Sets the ID of the template to apply API calls to.
     * @param templateID The ID of the template to apply API calls to.
     */
    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }
    
    /**
     * Creates a new template for the specified client.
     * After a successful call, this method sets the current template ID to that of the 
     * newly created template.
     * @param clientID The ID of the client to create the template for.
     * @param template The template details.
     * @return The ID of the newly created template
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/templates/#creating_a_template" target="_blank">
     * Creating a template</a>
     */
    public String create(String clientID, TemplateForCreate template) throws CreateSendException {
        templateID = client.post(String.class, template, "templates", clientID + ".json");
        return templateID;
    }
    
    /**
     * Gets the details of the current template
     * @return The details of the current template
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/templates/#getting_a_template" target="_blank">
     * Getting a template</a>
     */
    public TemplateDetails get() throws CreateSendException {
        return client.get(TemplateDetails.class, "templates", templateID + ".json");
    }
    
    /**
     * Updates a template with the specified details
     * @param template The template details to use
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/templates/#updating_a_template" target="_blank">
     * Updating a template</a>
     */
    public void update(TemplateForCreate template) throws CreateSendException {
        client.put(template, "templates", templateID + ".json");
    }
    
    /**
     * Deletes the template with the current ID
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/templates/#deleting_a_template" target="_blank">
     * Deleting a template</a>
     */
    public void delete() throws CreateSendException {
        client.delete("templates", templateID + ".json");
    }
}
