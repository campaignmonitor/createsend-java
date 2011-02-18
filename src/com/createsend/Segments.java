package com.createsend;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.PagedResult;
import com.createsend.models.segments.Rule;
import com.createsend.models.segments.Segment;
import com.createsend.models.subscribers.Subscriber;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.JsonProvider;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Segments extends BaseClient {
    /**
     * Creates a new segment on the specified list
     * @param listID The ID of the list to segment
     * @param segment The segment details
     * @return The ID of the newly created segment
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#creating_a_segment" target="blank">
     * Creating a segment</a>
     */
    public String create(String listID, Segment segment) throws CreateSendException {
        return post(String.class, segment, "segments", listID + ".json");
    }
    
    /**
     * Updates an existing segment with the specified details
     * @param segment The details of the segment to update. 
     *     The <code>SegmentID</code> field of this object must be populated
     * @throws CreateSendException Thrown when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#updating_a_segment" target="blank">
     * Updating a segment</a>
     */
    public void update(Segment segment) throws CreateSendException {
        put(segment, "segments", segment.SegmentID + ".json");
    }
    
    /**
     * Adds a new rule to an existing list segment
     * @param segmentID The ID of the segment to add the rule to
     * @param rule The rule specification
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#adding_a_segment_rule" target="_blank">
     * Adding a segment rule</a>
     */
    public void addRule(String segmentID, Rule rule) throws CreateSendException {
        post(String.class, rule, "segments", segmentID, "rules.json");
    }
    
    /**
     * Gets the details of the specified segment, including all rules and the current number of
     * active subscribers
     * @param segmentID The ID of the segment to get the details of.
     * @return The details of the specified segment.
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#getting_a_segment" target="_blank">
     * Getting a segment</a>
     */
    public Segment details(String segmentID) throws CreateSendException {
        return get(Segment.class, "segments", segmentID + ".json");
    }
    
    /**
     * Gets a paged collection of active subscribers within the specified segment
     * since the provided date.
     * @param segmentID The ID of the segment to get the subcribers from.
     * @param subscribedFrom The API will only return subscribers who became active on or after this date. 
     *     This field is required
     * @param page The page number or results to get. Use <code>null</code> for the default (page=1)
     * @param pageSize The number of records to get on the current page. Use <code>null</code> for the default.
     * @param orderField The field used to order the results by. Use <code>null</code> for the default.
     * @param orderDirection The direction to order the results by. Use <code>null</code> for the default.
     * @return The paged subscribers returned by the api call.
     * @throws CreateSendException Thrown when the API responds with a HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#getting_segment_subs" target="_blank">
     * Getting active subscribers</a>
     */
    public PagedResult<Subscriber> active(String segmentID, Date subscribedFrom,
        Integer page, Integer pageSize, String orderField, String orderDirection) throws CreateSendException {
        MultivaluedMap<String, String> queryString = new MultivaluedMapImpl(); 
        queryString.add("date", JsonProvider.ApiDateFormat.format(subscribedFrom));
        
        return getPagedResult(page, pageSize, orderField, orderDirection,
            queryString, "segments", segmentID, "active.json");
    }
    
    /**
     * Deletes the specified segment
     * @param segmentID The ID of the segment to delete
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#deleting_a_segment" target="_blank">
     * Deleting a segment</a>
     */
    public void delete(String segmentID) throws CreateSendException {
        delete("segments", segmentID + ".json");
    }
    
    /**
     * Deletes <em>all</em> rules from the specifed segment
     * @param segmentID The ID of the segment to delete the rules from
     * @throws CreateSendException Raised when the API responds with HTTP Status >= 400
     * @see <a href="http://www.campaignmonitor.com/api/segments/#deleting_segment_rules" target="_blank">
     * Deleting a segments rules</a>
     */
    public void deleteRules(String segmentID) throws CreateSendException {
        delete("segments", segmentID, "rules.json");
    }
}
