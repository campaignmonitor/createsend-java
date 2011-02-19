package com.createsend.models.subscribers;

public class ImportResult {
    public String[] FailureDetails; // TODO: Complete this model
    public int TotalUniqueEmailsSubmitted;
    public int TotalExistingSubscribers;
    public int TotalNewSubscribers;
    public String[] DuplicateEmailsInSubmission;
}
