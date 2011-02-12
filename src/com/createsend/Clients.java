package com.createsend;

import com.createsend.models.PagedResult;
import com.createsend.models.campaigns.DraftCampaign;
import com.createsend.models.campaigns.SentCampaign;
import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.Client;
import com.createsend.models.clients.Template;
import com.createsend.models.lists.List;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;


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
   
   public Segment[] segments(String clientID) throws CreateSendException {
       return get(Segment[].class, "clients", clientID, "segments.json");
   }
   
   public PagedResult<Subscriber> suppressionList(String clientID) throws CreateSendException {
       return getPagedResult("clients", clientID, "suppressionlist.json");
   }
   
   public Template[] templates(String clientID) throws CreateSendException {
       return get(Template[].class, "clients", clientID, "templates.json");
   }
}
