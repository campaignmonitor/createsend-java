package com.createsend.models.segments;

import com.createsend.models.ApiErrorResponse;

public class RuleCreationFailureDetails extends ApiErrorResponse<String> {
    public String Subject;
    public ClauseResults[] ClauseResults;
}
