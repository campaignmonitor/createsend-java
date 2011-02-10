package com.createsend;

import com.createsend.models.campaigns.DraftCampaign;
import com.createsend.models.campaigns.SentCampaign;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.lists.List;
import com.createsend.util.BaseClient;

import exceptions.CreateSendException;

public class Clients extends BaseClient {
    
   public String create(Client client) throws CreateSendException {
       return post(String.class, client, "clients.json");
   }
   
   public AllClientDetails details(String clientID) throws CreateSendException {
       return get(AllClientDetails.class, "clients", clientID + ".json");
   }
   
   public SentCampaign[] sentCampaigns(String clientID) throws CreateSendException {
       return get(SentCampaign[].class, "clients", clientID, "campaigns.json");
   }
   
   public DraftCampaign[] draftCampaigns(String clientID) throws CreateSendException {
       return get(DraftCampaign[].class, "clients", clientID, "drafts.json");
   }
   
   public List[] lists(String clientID) throws CreateSendException {
       return get(List[].class, "clients", clientID, "lists.json");
   }
}
