package com.createsend.models.subscribers;

import com.createsend.models.ApiErrorResponse;

public class FailedImportSubscriber extends ApiErrorResponse<String> {
    public String EmailAddress;
}
