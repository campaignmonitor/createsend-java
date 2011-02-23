package com.createsend.models.subscribers;

public class ImportResult {
    public FailedImportSubscriber[] FailureDetails; 
    public int TotalUniqueEmailsSubmitted;
    public int TotalExistingSubscribers;
    public int TotalNewSubscribers;
    public String[] DuplicateEmailsInSubmission;
}
