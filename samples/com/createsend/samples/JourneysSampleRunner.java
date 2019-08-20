package com.createsend.samples;

import com.createsend.Clients;
import com.createsend.JourneyEmails;
import com.createsend.Journeys;
import com.createsend.models.PagedResult;
import com.createsend.models.journeys.*;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.BadRequestException;
import com.createsend.util.exceptions.CreateSendException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JourneysSampleRunner {
    private static OAuthAuthenticationDetails auth = new OAuthAuthenticationDetails("your access token", "your refresh token");
    private static String ClientID = "your client id";
    private static String JourneyID = "your journey id";
    private static String JourneyEmailID = "your journey email id";

    public static void main(String[] args) {
        try {
            RunGetClientJourneys();
            RunGetJourneySummary();
            RunGetJourneyEmailRecipients();
            RunGetJourneyEmailOpens();
            RunGetJourneyEmailClicks();
            RunGetJourneyEmailUnsubscribes();
            RunGetJourneyEmailBounces();
        } catch (BadRequestException e) {
            e.printStackTrace();

            if(e.getResultData() != null) {
                System.err.printf("Exception result data: %s\n", e.getResultData());
            }
        } catch (CreateSendException e) {
            e.printStackTrace();
        }
    }

    private static void RunGetClientJourneys() throws CreateSendException {
        Clients clients = new Clients(auth, ClientID);

        JourneyDetail[] journeys = clients.journeys();
        for (JourneyDetail journey : journeys) {
            System.out.println(journey.Name);
        }
    }

    private static void RunGetJourneySummary() throws CreateSendException {
        Journeys journeys = new Journeys(auth, JourneyID);

        JourneySummary summary = journeys.summary();

        System.out.println(summary.Name);
    }

    private static void RunGetJourneyEmailRecipients() throws CreateSendException {
        JourneyEmails journeyEmails = new JourneyEmails(auth, JourneyEmailID);

        PagedResult<JourneyEmailRecipient> firstPage = journeyEmails.recipients(1, 10, "ASC");

        List<JourneyEmailRecipient> emailRecipients = new ArrayList<>(Arrays.asList(firstPage.Results));

        if (firstPage.NumberOfPages > 1) {
            for (int pageNumber = 2; pageNumber <= firstPage.NumberOfPages; pageNumber++)
            {
                PagedResult<JourneyEmailRecipient> subsequentPage = journeyEmails.recipients(pageNumber, 10, "ASC");
                emailRecipients.addAll(Arrays.asList(subsequentPage.Results));
            }
        }

        for (JourneyEmailRecipient result : emailRecipients) {
            System.out.println(result.EmailAddress);
        }
    }

    private static void RunGetJourneyEmailOpens() throws CreateSendException {
        JourneyEmails journeyEmails = new JourneyEmails(auth, JourneyEmailID);

        PagedResult<JourneyEmailOpenDetail> firstPage = journeyEmails.opens(1, 10, "ASC");

        List<JourneyEmailOpenDetail> emailOpens = new ArrayList<>(Arrays.asList(firstPage.Results));

        if (firstPage.NumberOfPages > 1) {
            for (int pageNumber = 2; pageNumber <= firstPage.NumberOfPages; pageNumber++)
            {
                PagedResult<JourneyEmailOpenDetail> subsequentPage = journeyEmails.opens(pageNumber, 10, "ASC");
                emailOpens.addAll(Arrays.asList(subsequentPage.Results));
            }
        }

        for (JourneyEmailOpenDetail result : emailOpens) {
            System.out.println(result.EmailAddress);
        }
    }

    private static void RunGetJourneyEmailClicks() throws CreateSendException {
        JourneyEmails journeyEmails = new JourneyEmails(auth, JourneyEmailID);

        PagedResult<JourneyEmailClickDetail> firstPage = journeyEmails.clicks(1, 10, "ASC");

        List<JourneyEmailClickDetail> emailOpens = new ArrayList<>(Arrays.asList(firstPage.Results));

        if (firstPage.NumberOfPages > 1) {
            for (int pageNumber = 2; pageNumber <= firstPage.NumberOfPages; pageNumber++)
            {
                PagedResult<JourneyEmailClickDetail> subsequentPage = journeyEmails.clicks(pageNumber, 10, "ASC");
                emailOpens.addAll(Arrays.asList(subsequentPage.Results));
            }
        }

        for (JourneyEmailClickDetail result : emailOpens) {
            System.out.println(result.EmailAddress);
        }
    }

    private static void RunGetJourneyEmailUnsubscribes() throws CreateSendException {
        JourneyEmails journeyEmails = new JourneyEmails(auth, JourneyEmailID);

        PagedResult<JourneyEmailUnsubscribeDetail> firstPage = journeyEmails.unsubscribes(1, 10, "ASC");

        List<JourneyEmailUnsubscribeDetail> emailOpens = new ArrayList<>(Arrays.asList(firstPage.Results));

        if (firstPage.NumberOfPages > 1) {
            for (int pageNumber = 2; pageNumber <= firstPage.NumberOfPages; pageNumber++)
            {
                PagedResult<JourneyEmailUnsubscribeDetail> subsequentPage = journeyEmails.unsubscribes(pageNumber, 10, "ASC");
                emailOpens.addAll(Arrays.asList(subsequentPage.Results));
            }
        }

        for (JourneyEmailUnsubscribeDetail result : emailOpens) {
            System.out.println(result.EmailAddress);
        }
    }

    private static void RunGetJourneyEmailBounces() throws CreateSendException {
        JourneyEmails journeyEmails = new JourneyEmails(auth, JourneyEmailID);

        PagedResult<JourneyEmailBounceDetail> firstPage = journeyEmails.bounces(1, 10, "ASC");

        List<JourneyEmailBounceDetail> emailOpens = new ArrayList<>(Arrays.asList(firstPage.Results));

        if (firstPage.NumberOfPages > 1) {
            for (int pageNumber = 2; pageNumber <= firstPage.NumberOfPages; pageNumber++)
            {
                PagedResult<JourneyEmailBounceDetail> subsequentPage = journeyEmails.bounces(pageNumber, 10, "ASC");
                emailOpens.addAll(Arrays.asList(subsequentPage.Results));
            }
        }

        for (JourneyEmailBounceDetail result : emailOpens) {
            System.out.println(result.EmailAddress);
        }
    }
}
