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
package com.createsend.models.transactional.request;

import com.createsend.models.transactional.EmailContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains the request body for Transactional Send Classic email in the Campaign Monitor API.
 */
public class ClassicEmailRequest {
    /**
     * The Subject of the email.
     */
    private String subject;

    /**
     * The From address of the email.
     */
    private String from;

    /**
     * The Reply To address of the email.
     */
    private String replyTo;

    /**
     * The list of To recipients of the email.
     */
    private List<String> to = new ArrayList<>();

    /**
     * The list of Cc recipients of the email.
     */
    private List<String> cc = new ArrayList<>();

    /**
     * The list of Bcc recipients of the email.
     */
    private List<String> bcc = new ArrayList<>();

    /**
     * The list of Attachments to be sent with the email.
     */
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * The Text version of the message content.
     */
    private String text;

    /**
     * The Html version of the message content.
     */
    private String html;

    /**
     * The Group to tag the message with. This allows you to group similar messages for reporting purposes.
     */
    private String group;

    /**
     * The ListID to add recipients to.
     */
    private String addRecipientsToList;

    /**
     * Should Css be automatically inlined.
     */
    private boolean inlineCss;

    /**
     * Should Opens be tracked.
     */
    private boolean trackOpens;

    /**
     * Should Clicks be tracked.
     */
    private boolean trackClicks;

    public ClassicEmailRequest(String to) {
        if (to == null || to.length() == 0) {
            throw new IllegalArgumentException("Must supply a TO address");
        }

        this.to.add(to);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(EmailContent content) {
        this.text = content.getText();
        this.html = content.getHtml();
        this.inlineCss = content.isInlineCss();
        this.trackOpens = content.isTrackOpens();
        this.trackClicks = content.isTrackClicks();
    }

    public String getText() {
        return text;
    }

    public String getHtml() {
        return html;
    }

    public boolean isInlineCss() {
        return inlineCss;
    }

    public boolean isTrackClicks() {
        return trackClicks;
    }

    public boolean isTrackOpens() {
        return trackOpens;
    }

    public void addTo(String recipient) {
        to.add(recipient);
    }

    public Iterator<String> getTo() {
        return to.iterator();
    }

    public void addCc(String recipient) {
        cc.add(recipient);
    }

    public void addBcc(String recipient) {
        bcc.add(recipient);
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAddRecipientsToList(String addRecipientsToList) {
        this.addRecipientsToList = addRecipientsToList;
    }

    public String getAddRecipientsToList() {
        return addRecipientsToList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
