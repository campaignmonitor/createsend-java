package com.createsend.models.segments;

public class Segment {
    public String ListID;
    public String SegmentID;
    public String Title;
    
    @Override
    public String toString() {
        return String.format("{ ListID: %s, SegmentID: %s, Title: %s }", ListID,
                SegmentID, Title);
    }
}
