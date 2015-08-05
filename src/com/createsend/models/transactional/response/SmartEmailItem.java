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

import java.util.Date;
import java.util.UUID;

public class SmartEmailItem {
    @JsonProperty("ID")
    private UUID id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("CreatedAt")
    private Date createdAt;

    @JsonProperty("Status")
    private SmartEmailStatus status;

    /**
     * @return the smart email id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the smart email name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the created at date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the status of the smart email.
     */
    public SmartEmailStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Status: %s, CreatedAt: %s", id, name, status, createdAt);
    }
}