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

public class TransactionalStatistics {
    @JsonProperty("Sent")
    private int sent;

    @JsonProperty("Bounces")
    private int bounces;

    @JsonProperty("Delivered")
    private int delivered;

    @JsonProperty("Opened")
    private int opened;

    @JsonProperty("Clicked")
    private int clicked;

    @JsonProperty("Query")
    private TransactionalStatisticsQuery query;

    /**
     * @return the total sent.
     */
    public int getSent() {
        return sent;
    }

    /**
     * @return the total bounced.
     */
    public int getBounces() {
        return bounces;
    }

    /**
     * @return the total delivered.
     */
    public int getDelivered() {
        return delivered;
    }

    /**
     * @return the total opened.
     */
    public int getOpened() {
        return opened;
    }

    /**
     * @return the total clicked.
     */
    public int getClicked() {
        return clicked;
    }

    /**
     * @return details about the message.
     */
    public TransactionalStatisticsQuery getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return String.format("Sent: %s, Bounces: %s, Delivered: %s, Opened: %s, Clicked: %s", sent, bounces, delivered, opened, clicked);
    }
}

