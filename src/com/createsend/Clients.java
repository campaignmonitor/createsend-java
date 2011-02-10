package com.createsend;

import com.createsend.models.clients.AllClientDetails;
import com.createsend.models.clients.DetailedClient;
import com.createsend.util.BaseClient;
import com.createsend.util.CreateSendException;

public class Clients extends BaseClient {
   public String create(DetailedClient client) throws CreateSendException {
       return post(String.class, client, "clients.json");
   }
   
   public AllClientDetails details(String clientID) throws CreateSendException {
       return get(AllClientDetails.class, "clients", clientID + ".json");
   }
}
