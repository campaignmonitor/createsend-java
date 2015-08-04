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
import java.util.List;
import java.util.UUID;

public class Message {
    @JsonProperty("MessageID")
    private UUID messageID;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("SentAt")
    private Date sentAt;

    @JsonProperty("SmartEmailID")
    private UUID smartEmailId;

    @JsonProperty("CanBeResent")
    private boolean canBeResent;

    @JsonProperty("Recipient")
    private String recipient;

    @JsonProperty("TotalOpens")
    private int totalOpens;

    @JsonProperty("TotalClicks")
    private int totalClicks;

    @JsonProperty("Message")
    private MessageDetail message;

    @JsonProperty("Opens")
    private List<TransactionalOpen> opens;

    @JsonProperty("Clicks")
    private List<TransactionalClick> clicks;

    /**
     * @return the message id.
     */
    public UUID getMessageID() {
        return messageID;
    }

    /**
     * @return the message delivery status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the message sentAt.
     */
    public Date getSentAt() {
        return sentAt;
    }

    /**
     * @return the smart email id.
     */
    public UUID getSmartEmailId() {
        return smartEmailId;
    }

    /**
     * @return true if the message can be resent, false otherwise.
     */
    public boolean isCanBeResent() {
        return canBeResent;
    }

    /**
     * @return the recipient of the message.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @return the total opens of the message.
     */
    public int getTotalOpens() {
        return totalOpens;
    }

    /**
     * @return the total clicks of the message.
     */
    public int getTotalClicks() {
        return totalClicks;
    }

    /**
     * @return the message details.
     */
    public MessageDetail getMessage() {
        return message;
    }

    /**
     * @return list of each open event.
     */
    public List<TransactionalOpen> getOpens() {
        return opens;
    }

    /**
     * @return list of each click event.
     */
    public List<TransactionalClick> getClicks() {
        return clicks;
    }

    @Override
    public String toString() {
        return String.format("MessageID: %s, Status: %s, Recipient: %s, %s", messageID, status, recipient, message);
    }
}
