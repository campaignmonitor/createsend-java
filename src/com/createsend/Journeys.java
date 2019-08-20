package com.createsend;

import com.createsend.models.journeys.JourneySummary;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;

public class Journeys extends CreateSendBase {
    private String journeyID;

    public Journeys(AuthenticationDetails auth) {
        this(auth, null);
    }

    public Journeys(AuthenticationDetails auth, String journeyID) {
        this.journeyID = journeyID;
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    public JourneySummary summary() throws CreateSendException {
        return jerseyClient.get(JourneySummary.class, "journeys", journeyID, ".json");
    }
}
