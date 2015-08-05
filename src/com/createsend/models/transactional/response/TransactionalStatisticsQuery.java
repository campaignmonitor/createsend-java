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

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

public class TransactionalStatisticsQuery {
    @JsonProperty("Group")
    private String group;

    @JsonProperty("SmartEmailID")
    private UUID smartEmailID;

    @JsonProperty("From")
    private String from;

    @JsonProperty("To")
    private String to;

    @JsonProperty("TimeZone")
    private String timeZone;

    /**
     * @return the group.
     */
    public String getGroup() {
        return group;
    }

    /**
     * @return the smart email id.
     */
    public UUID getSmartEmailID() {
        return smartEmailID;
    }

    /**
     * @return the from address of the message.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the to address of the message.
     */
    public String getTo() {
        return to;
    }

    /**
     * @return the timezone.
     */
    public String getTimeZone() {
        return timeZone;
    }
}
