package com.createsend;

import com.createsend.models.PagedResult;
import com.createsend.models.journeys.JourneyEmailBounceDetail;
import com.createsend.models.journeys.JourneyEmailClickDetail;
import com.createsend.models.journeys.JourneyEmailOpenDetail;
import com.createsend.models.journeys.JourneyEmailRecipient;
import com.createsend.models.journeys.JourneyEmailUnsubscribeDetail;
import com.createsend.util.AuthenticationDetails;
import com.createsend.util.JerseyClientImpl;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Date;

public class JourneyEmails extends CreateSendBase {
    private String journeyEmailID;

    public JourneyEmails(AuthenticationDetails auth, String journeyEmailID) {
        this.journeyEmailID = journeyEmailID;
        this.jerseyClient = new JerseyClientImpl(auth);
    }

    /**
     * Gets a paged list of recipients for the specified journey email
     * @return The paged recipients returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-recipients" target="_blank">
     * Getting journey email recipients</a>
     */
    public PagedResult<JourneyEmailRecipient> recipients() throws CreateSendException {
        return recipients(1, 1000, "asc");
    }

    /**
     * Gets a paged list of recipients for the specified journey email
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged recipients returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-recipients" target="_blank">
     * Getting journey email recipients</a>
     */
    public PagedResult<JourneyEmailRecipient> recipients(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return recipients("", page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of recipients for the specified journey email
     * @param fromDate The date to start getting bounce results from. This field is required
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged recipients returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-recipients" target="_blank">
     * Getting journey email recipients</a>
     */
    public PagedResult<JourneyEmailRecipient> recipients(
            Date fromDate, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return recipients(JsonProvider.ApiDateFormat.format(fromDate), page, pageSize, orderDirection);
    }

    private PagedResult<JourneyEmailRecipient> recipients(
            String fromDate, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", fromDate);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection, null,
                "journeys", "email", journeyEmailID, "recipients.json");
    }

    /**
     * Gets a paged list of bounces for the specified journey email
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-bounces" target="_blank">
     * Getting journey email bounces</a>
     */
    public PagedResult<JourneyEmailBounceDetail> bounces() throws CreateSendException {
        return bounces(1, 1000, "asc");
    }

    /**
     * Gets a paged list of bounces for the specified journey email
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-bounces" target="_blank">
     * Getting journey email bounces</a>
     */
    public PagedResult<JourneyEmailBounceDetail> bounces(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return bounces("", page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of bounces for the specified journey email
     * @param bouncesFrom The date to start getting bounce results from. This field is required
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged bounces returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-bounces" target="_blank">
     * Getting journey email bounces</a>
     */
    public PagedResult<JourneyEmailBounceDetail> bounces(
            Date bouncesFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return bounces(JsonProvider.ApiDateFormat.format(bouncesFrom), page, pageSize,orderDirection);
    }

    private PagedResult<JourneyEmailBounceDetail> bounces(
            String bouncesFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", bouncesFrom);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection, queryString,
                "journeys", "email", journeyEmailID, "bounces.json");
    }

    /**
     * Gets a paged list of opens for the specified journey email
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-opens" target="_blank">
     * Getting journey email opens</a>
     */
    public PagedResult<JourneyEmailOpenDetail> opens() throws CreateSendException {
        return opens(1, 1000,"asc");
    }

    /**
     * Gets a paged list of opens for the specified journey email
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-opens" target="_blank">
     * Getting journey email opens</a>
     */
    public PagedResult<JourneyEmailOpenDetail> opens(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return opens("", page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of opens for the specified journey email
     * @param opensFrom The date to start getting open results from. This field is required
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged opens returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-opens" target="_blank">
     * Getting journey email opens</a>
     */
    public PagedResult<JourneyEmailOpenDetail> opens(
            Date opensFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return opens(JsonProvider.ApiDateFormat.format(opensFrom), page, pageSize,orderDirection);
    }

    private PagedResult<JourneyEmailOpenDetail> opens(
            String opensFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", opensFrom);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection,
                queryString, "journeys", "email", journeyEmailID, "opens.json");
    }

    /**
     * Gets a paged list of clicks for the specified journey email
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/journeys/#journey_clickslist" target="_blank">
     * Getting journey email clicks</a>
     */
    public PagedResult<JourneyEmailClickDetail> clicks() throws CreateSendException {
        return clicks(1, 1000, "asc");
    }

    /**
     * Gets a paged list of clicks for the specified journey email
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-clicks" target="_blank">
     * Getting journey email clicks</a>
     */
    public PagedResult<JourneyEmailClickDetail> clicks(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return clicks("", page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of clicks for the specified journey email
     * @param clicksFrom The date to start getting click results from. This field is required
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged clicks returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-clicks" target="_blank">
     * Getting journey email clicks</a>
     */
    public PagedResult<JourneyEmailClickDetail> clicks(
            Date clicksFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return clicks(JsonProvider.ApiDateFormat.format(clicksFrom), page, pageSize, orderDirection);
    }

    private PagedResult<JourneyEmailClickDetail> clicks(
            String clicksFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", clicksFrom);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection,
                queryString, "journeys", "email", journeyEmailID, "clicks.json");
    }

    /**
     * Gets a paged list of unsubscribes for the specified journey email
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-unsubscribes" target="_blank">
     * Getting journey email unsubscribes</a>
     */
    public PagedResult<JourneyEmailUnsubscribeDetail> unsubscribes() throws CreateSendException {
        return unsubscribes(1, 1000, "asc");
    }

    /**
     * Gets a paged list of unsubscribes for the specified journey email
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-unsubscribes" target="_blank">
     * Getting journey email unsubscribes</a>
     */
    public PagedResult<JourneyEmailUnsubscribeDetail> unsubscribes(
            Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return unsubscribes("", page, pageSize, orderDirection);
    }

    /**
     * Gets a paged list of unsubscribes for the specified journey email
     * @param unsubscribesFrom The date to start getting unsubscribe results from. This field is required
     * @param page The page number of results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged unsubscribes returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="https://www.campaignmonitor.com/api/journeys/#journey-email-unsubscribes" target="_blank">
     * Getting journey email unsubscribes</a>
     */
    public PagedResult<JourneyEmailUnsubscribeDetail> unsubscribes(
            Date unsubscribesFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        return unsubscribes(JsonProvider.ApiDateFormat.format(unsubscribesFrom), page, pageSize, orderDirection);
    }

    private PagedResult<JourneyEmailUnsubscribeDetail> unsubscribes(
            String unsubscribesFrom, Integer page, Integer pageSize, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl();
        queryString.add("date", unsubscribesFrom);

        return jerseyClient.getPagedResult(page, pageSize, null, orderDirection,
                queryString, "journeys", "email", journeyEmailID, "unsubscribes.json");
    }
}
