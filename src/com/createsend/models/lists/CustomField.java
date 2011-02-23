package com.createsend.models.lists;

import java.util.Arrays;

public class CustomField extends BaseCustomField {
    public String Key;
    public String[] FieldOptions;
    
    public String toString() {
        return String.format("{ Key: %s %s, FieldOptions: %s }", Key, super.toString(),
            Arrays.deepToString(FieldOptions));
    }
}
