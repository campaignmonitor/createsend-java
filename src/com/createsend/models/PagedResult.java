package com.createsend.models;

import java.util.Arrays;

public class PagedResult<T> {
    public T[] Results;
    public String ResultsOrderedBy;
    public String OrderDirection;
    public int PageNumber;
    public int PageSize;
    public int RecordsOnThisPage;
    public int TotalNumberOfRecords;
    public int NumberOfPages;
    
    @Override
    public String toString() {
        return String.format("{ Results: %s, ResultsOrderedBy: %s, OrderDirection: %s, PageNumber: %s, PageSize: %s, RecordsOnThisPage: %s, TotalNumberOfRecords: %s, NumberOfPages: %s }",
            Arrays.deepToString(Results), ResultsOrderedBy, OrderDirection, PageNumber,
            PageSize, RecordsOnThisPage, TotalNumberOfRecords,
            NumberOfPages);
    }
}
