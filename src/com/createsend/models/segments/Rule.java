package com.createsend.models.segments;

import java.util.Arrays;

public class Rule {
    public String Subject;
    public String[] Clauses;
    
    @Override
    public String toString() {
        return String.format("{ Subject: %s, Clauses: %s }", Subject, Arrays.deepToString(Clauses));
    }
}
