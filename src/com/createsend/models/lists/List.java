package com.createsend.models.lists;

public class List {
    public String ListID;
    public String Name;
    
    @Override
    public String toString() {
        return String.format("{ ListID: %s, Name: %s } ", ListID, Name);
    }
}
