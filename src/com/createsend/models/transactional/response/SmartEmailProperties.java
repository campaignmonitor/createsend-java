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
import org.codehaus.jackson.annotate.JsonProperty;

public class SmartEmailProperties {

    @JsonProperty("From")
    private String from;

    @JsonProperty("ReplyTo")
    private String replyTo;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("TextPreviewUrl")
    private String textPreviewUrl;

    @JsonProperty("HtmlPreviewUrl")
    private String htmlPreviewUrl;

    @JsonProperty("Content")
    private EmailContent content;

    /**
     * @return the from address of the smart email.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the replyTo address of the smart email.
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * @return the subject of the smart email.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the text preview url.
     */
    public String getTextPreviewUrl() {
        return textPreviewUrl;
    }

    /**
     * @return the html preview url.
     */
    public String getHtmlPreviewUrl() {
        return htmlPreviewUrl;
    }

    /**
     * @return the content of the smart email.
     * The Campaign Monitor API does not support returning smart email content.
     */
    public EmailContent getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("From: %s, Subject: %s, Content:\n%s", from, subject, content);
    }
}