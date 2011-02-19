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

import com.createsend.models.PagedResult;
import com.createsend.models.campaigns.DraftCampaign;
import com.createsend.models.campaigns.SentCampaign;
import com.createsend.models.clients.AccessDetails;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.BillingDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.clients.Template;
import com.createsend.models.lists.ListBasics;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;


public class Clients extends BaseClient {
    private String clientID;
    
    /**
     * Constructor.
     * Use this for creating new clients.
     */
    public Clients() {}
    
    /**
     * Constructor.
     * @param clientID The ID of the client to apply calls to.
     */
    public Clients(String clientID) {
        setClientID(clientID);
    }
    
    /**
     * Sets the current client ID.
     * @param clientID The ID of the client to apply API calls to.
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    
    /**
     * Gets the current client ID
     * @return The current client ID
     */
    public String getClientID() {
        return clientID;
    }
    
    
    public String create(Client client) throws CreateSendException {
        clientID = post(String.class, client, "clients.json");
        return clientID;
    }
   
    public AllClientDetails details() throws CreateSendException {
        return get(AllClientDetails.class, "clients", clientID + ".json");
    }
   
    public SentCampaign[] sentCampaigns() throws CreateSendException {
        return get(SentCampaign[].class, "clients", clientID, "campaigns.json");
    }
   
    public DraftCampaign[] draftCampaigns() throws CreateSendException {
        return get(DraftCampaign[].class, "clients", clientID, "drafts.json");
    }
   
    public ListBasics[] lists() throws CreateSendException {
        return get(ListBasics[].class, "clients", clientID, "lists.json");
    }
   
    public Segment[] segments() throws CreateSendException {
        return get(Segment[].class, "clients", clientID, "segments.json");
    }
   
    public PagedResult<Subscriber> suppressionList(
        Integer page, Integer pageSize, String orderField, String orderDirection) 
        throws CreateSendException {
        return getPagedResult(page, pageSize, orderField, orderDirection, null,
            "clients", clientID, "suppressionlist.json");
    }
   
    public Template[] templates() throws CreateSendException {
        return get(Template[].class, "clients", clientID, "templates.json");
    }
   
    public void setBasics(Client client) throws CreateSendException {
        put(client, "clients", clientID, "setbasics.json");
    }
   
    public void setAccess(AccessDetails access) throws CreateSendException {
        put(access, "clients", clientID, "setaccess.json");
    }
   
    public void setPaygBilling(BillingDetails billing) throws CreateSendException {
        put(billing, "clients", clientID, "setpaygbilling.json");
    }
   
    public void setMonthlyBilling(BillingDetails billing) throws CreateSendException {
        billing.MarkupOnDelivery = null;
        billing.MarkupOnDesignSpamTest = null;
        billing.MarkupPerRecipient = null;
        billing.CanPurchaseCredits = false;
       
        put(billing, "clients", clientID, "setmonthlybilling.json");
    }
   
    public void delete() throws CreateSendException {
        delete("clients", clientID + ".json");
    }
}