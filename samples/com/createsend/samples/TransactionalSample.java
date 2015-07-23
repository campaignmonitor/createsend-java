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
package com.createsend.samples;

import com.createsend.BasicEmail;
import com.createsend.Messages;
import com.createsend.SmartEmail;
import com.createsend.models.transactional.EmailContent;
import com.createsend.models.transactional.request.Attachment;
import com.createsend.models.transactional.request.BasicEmailRequest;
import com.createsend.models.transactional.request.SmartEmailRequest;
import com.createsend.models.transactional.response.*;
import com.createsend.util.ApiKeyAuthenticationDetails;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;

import java.io.IOException;
import java.net.URL;

/**
 * Simple examples of how to use the Transactional API.
 */
public class TransactionalSample {

    private static OAuthAuthenticationDetails auth = new OAuthAuthenticationDetails("your access token", "your refresh token");

    private static final String smartEmailID = "your smart email id";
    private static final String messageID = "your message id";
    private static final String toAddress = "you@example.com";
    private static final String fromAddress = "you@example.com";
    private static final String replyToAddress = "you@example.com";
    private static final String subject = "java transactional api wrapper";
    private static final String basicGroup = "java wrapper emails";

    public static void main(String args[]) throws CreateSendException, IOException {
        listSmartEmails();
        sendSmartEmail();

        listBasicGroups();
        sendBasicEmail();

        resend();

        getMessage();

        showStatistics();

        timeline();
    }

    public static void timeline() throws CreateSendException {
        System.out.println("---- Timeline ----");

        Messages messages = new Messages(auth);

        MessageLogItem[] timeline = messages.timeline(null, null, null, 50, null);

        for (MessageLogItem item : timeline) {
            System.out.println(item);
        }
    }

    private static void showStatistics() throws CreateSendException {
        System.out.println("---- Show Statistics ----");

        Messages messages = new Messages(auth);

        TransactionalStatistics stats = messages.statistics(null, smartEmailID, null, null, null, null);

        System.out.println(stats);
    }

    private static void getMessage() throws CreateSendException {
        System.out.println("---- Get Message ----");

        Messages messages = new Messages(auth);

        Message message = messages.get(messageID);

        System.out.println(message);
    }

    private static void sendBasicEmail() throws CreateSendException, IOException {
        System.out.println("---- Send Basic Email ----");

        BasicEmail basicEmail = new BasicEmail(auth);

        BasicEmailRequest basicEmailRequest = new BasicEmailRequest(toAddress);
        basicEmailRequest.setFrom(fromAddress);
        basicEmailRequest.setReplyTo(replyToAddress);
        basicEmailRequest.setSubject(subject);

        EmailContent content = new EmailContent();
        content.setHtml("<html><body><h1>HTML content</h1>html sent via the wrapper</body></html>");
        content.setText("plain text sent via the wrapper");
        content.setTrackOpens(true);
        content.setTrackClicks(true);
        basicEmailRequest.setContent(content);

        //optional, but more powerful reporting is available if you specify a group
        basicEmailRequest.setBasicGroup(basicGroup);

        Attachment attachment = getAttachment();
        basicEmailRequest.addAttachment(attachment);

        basicEmail.send(basicEmailRequest);
    }

    private static void listBasicGroups() throws CreateSendException {
        System.out.println("---- List Basic Email Groups ----");

        BasicEmail basicEmail = new BasicEmail(auth);

        BasicEmailGroup[] basicGroups = basicEmail.list();

        for (BasicEmailGroup basicGroup : basicGroups) {
            System.out.printf("Basic Email: %s\n", basicGroup);
        }
    }

    private static void listSmartEmails() throws CreateSendException {
        System.out.println("---- List Smart Emails ----");

        SmartEmail smartEmail = new SmartEmail(auth);
        SmartEmailItem[] smartEmails = smartEmail.list();

        for (SmartEmailItem status : smartEmails) {
            System.out.printf("Smart Email: %s\n", status);
            getSmartEmailDetails(status.getId());
        }
    }

    private static void getSmartEmailDetails(String smartEmailId) throws CreateSendException {
        System.out.println("---- Get Smart Email Details ----");

        SmartEmail smartEmail = new SmartEmail(auth);
        SmartEmailDetails smartEmailDetails = smartEmail.get(smartEmailId);

        System.out.println(smartEmailDetails);
    }

    private static void sendSmartEmail() throws CreateSendException, IOException {
        System.out.println("---- Send Smart Email ----");

        SmartEmail smartEmail = new SmartEmail(auth);
        SmartEmailRequest smartEmailRequest = new SmartEmailRequest(smartEmailID, toAddress);
        smartEmailRequest.addData("myvariable", "supplied via the wrapper sample runner");

        Attachment attachment = getAttachment();
        smartEmailRequest.addAttachment(attachment);

        smartEmail.send(smartEmailRequest);
    }

    private static void resend() throws CreateSendException {
        System.out.println("---- Resend Email ----");

        Messages messages = new Messages(auth);
        messages.resend(messageID);
    }

    private static Attachment getAttachment() throws IOException {
        //attach an image to the outgoing email
        URL attachmentUrl = new URL("http://imgs.xkcd.com/comics/email.png");

        Attachment attachment = new Attachment();
        attachment.Name = "email.png";
        attachment.Type = "image/png";
        attachment.base64EncodeContentStream(attachmentUrl.openStream());

        return attachment;
    }
}