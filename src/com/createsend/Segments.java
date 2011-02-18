package com.createsend;

import com.createsend.models.segments.Segment;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;

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
}
