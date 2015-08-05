/**
 * Copyright (c) 2015 Richard Bremner
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend.models.transactional.response;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * The valid states of a Smart Email.
 */
public enum SmartEmailStatus {

    ALL,
    ACTIVE,
    DRAFT;

    private static Map<SmartEmailStatus, String> values = new HashMap<>();
    private static Map<String, SmartEmailStatus> names = new HashMap<>();
    private static void property(String value, SmartEmailStatus status) {
        names.put(value, status);
        values.put(status, value);
    }

    static {
        property("all", ALL);
        property("active", ACTIVE);
        property("draft", DRAFT);
    }

    @JsonCreator
    public static SmartEmailStatus forValue(String value) {
        return names.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        return values.get(this);
    }

    @Override
    public String toString() {
        return toValue();
    }
}