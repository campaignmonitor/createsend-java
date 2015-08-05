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

public class TransactionalClick {
    @JsonProperty("EmailAddress")
    private String emailAddress;

    @JsonProperty("Date")
    private Date date;

    @JsonProperty("IPAddress")
    private String ipAddress;

    @JsonProperty("Geolocation")
    private GeoLocation geoLocation;

    @JsonProperty("URL")
    private String url;

    /**
     * @return the recipient address of the event.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return the date of the event.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the IP Address the event originated at.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @return the location the event originated at.
     */
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    /**
     * @return the url that was clicked.
     */
    public String getUrl() {
        return url;
    }
}
