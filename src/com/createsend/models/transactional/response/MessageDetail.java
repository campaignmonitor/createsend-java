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

import com.createsend.models.transactional.EmailContent;
import com.createsend.models.transactional.request.Attachment;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

public class MessageDetail {
    @JsonProperty("From")
    private String from;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("To")
    private List<String> to;

    @JsonProperty("CC")
    private List<String> cc;

    @JsonProperty("BCC")
    private List<String> bcc;

    @JsonProperty("ReplyTo")
    private String replyTo;

    @JsonProperty("Attachments")
    private List<Attachment> attachments;

    @JsonProperty("Body")
    private EmailContent body;

    @JsonProperty("Data")
    private Map<String, String> data;

    /**
     * @return the From address.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the subject of the message.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the list of To recipients.
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * @return the list of Cc recipients.
     */
    public List<String> getCc() {
        return cc;
    }

    /**
     * @return the list of Bcc recipients.
     */
    public List<String> getBcc() {
        return bcc;
    }

    /**
     * @return the reply to address.
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * @return the list of attachment meta data.
     * The base64 attachment content is not available from the CampaignMonitor API.
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @return the email body.
     */
    public EmailContent getBody() {
        return body;
    }

    /**
     * @return the data merge variables.
     */
    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("Message. From: %s, Subject: %s", from, subject);
    }
}
