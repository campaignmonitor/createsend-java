/**
 * Copyright (c) 2015 Richard Bremner
 *
 *  Permission is hereby granted, free of charge, To any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), To deal
 *  in the Software without restriction, including without limitation the rights
 *  To use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and To permit persons To whom the Software is
 *  furnished To do so, subject To the following conditions:
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
package com.createsend.models.transactional.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonUnwrapped;

import java.util.*;

public class SmartEmailRequest {
    @JsonIgnore
    private UUID smartEmailId;
    private List<String> to = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
    private Map<String, String> data = new HashMap<>();
    private boolean addRecipientsToList;

    /**
     * Creates a new SmartEmailRequest with mandatory values.
     * @param smartEmailId The SmartEmailID of the email to send.
     * @param to The recipient of the email.
     */
    public SmartEmailRequest(UUID smartEmailId, String to) {
        if (smartEmailId == null) {
            throw new IllegalArgumentException("Must supply a Smart Email ID");
        }

        if (to == null || to.length() == 0) {
            throw new IllegalArgumentException("Must supply a TO address");
        }

        this.smartEmailId = smartEmailId;
        this.to.add(to);
    }

    /**
     * @return the smart email id.
     */
    public UUID getSmartEmailId() {
        return smartEmailId;
    }

    /**
     * @param recipient add a To recipient to the request.
     */
    public void addTo(String recipient) {
        to.add(recipient);
    }

    /**
     * @return the list of To recipients.
     */
    public Iterator<String> getTo() {
        return to.iterator();
    }

    /**
     * @param recipient add a Cc recipient to the request.
     */
    public void addCc(String recipient) {
        cc.add(recipient);
    }

    /**
     * @return the list of Cc recipients.
     */
    public Iterator<String> getCc() {
        return cc.iterator();
    }

    /**
     * @param recipient add a Bcc recipient to the request.
     */
    public void addBcc(String recipient) {
        bcc.add(recipient);
    }

    /**
     * @return the list of Bcc recipients.
     */
    public Iterator<String> getBcc() {
        return bcc.iterator();
    }

    /**
     * Add a data merge variable to be mail merged when the email is sent.
     * @param name The name of the variable.
     * @param value The value of the variable.
     */
    public void addData(String name, String value) {
        data.put(name, value);
    }

    /**
     * @return the data variables to be used for mail merge.
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param attachment attachment to add. content must be base64 encoded.
     */
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    /**
     * @return the list of attachments.
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @param addRecipientsToList add recipients to a list during send.
     */
    public void setAddRecipientsToList(boolean addRecipientsToList) {
        this.addRecipientsToList = addRecipientsToList;
    }

    /**
     * @return true if recipients are to be added to a list, false otherwise.
     */
    public boolean isAddRecipientsToList() {
        return addRecipientsToList;
    }
}