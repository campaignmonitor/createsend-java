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
package com.createsend.models.transactional;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class EmailContent {

    @JsonProperty("Html")
    private String html;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("EmailVariables")
    private List<String> emailVariables;

    @JsonProperty("InlineCss")
    private boolean inlineCss;

    @JsonProperty("TrackOpens")
    private boolean trackOpens;

    @JsonProperty("TrackClicks")
    private boolean trackClicks;

    /**
     * @return the html body.
     */
    public String getHtml() {
        return html;
    }

    /**
     * @return the text body.
     */
    public String getText() {
        return text;
    }

    /**
     * @return the data merge variables.
     */
    public List<String> getEmailVariables() {
        return emailVariables;
    }

    /**
     * @return true if inline css, false otherwise.
     */
    public boolean isInlineCss() {
        return inlineCss;
    }

    /**
     * @return true if track opens enabled, false otherwise.
     */
    public boolean isTrackOpens() {
        return trackOpens;
    }

    /**
     * @return true if track clicks enabled, false otherwise.
     */
    public boolean isTrackClicks() {
        return trackClicks;
    }

    /**
     * @param html html body content.
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * @param text text body content.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param inlineCss enabled css inlining.
     */
    public void setInlineCss(boolean inlineCss) {
        this.inlineCss = inlineCss;
    }

    /**
     * @param trackOpens enabled open tracking.
     */
    public void setTrackOpens(boolean trackOpens) {
        this.trackOpens = trackOpens;
    }

    /**
     * @param trackClicks enable click tracking.
     */
    public void setTrackClicks(boolean trackClicks) {
        this.trackClicks = trackClicks;
    }

    @Override
    public String toString() {
        return String.format("html\n: %s\n\n text\n: %s\n", html, text);
    }
}