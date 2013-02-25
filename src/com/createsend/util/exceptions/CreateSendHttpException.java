/**
 * Copyright (c) 2011 Toby Brain
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
package com.createsend.util.exceptions;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * An exception raised on any HTTP based error. i.e Status code >= 400
 */
public class CreateSendHttpException extends CreateSendException {
    private static final long serialVersionUID = 6026680795882633621L;

    private ClientResponse.Status httpStatusCode;
    private int apiErrorCode;
    private String apiErrorMessage;

    public CreateSendHttpException(Status httpStatusCode) {
        super("The API call failed due to an unexpected HTTP error: " + httpStatusCode);
        this.httpStatusCode = httpStatusCode;
        this.apiErrorMessage = "";
    }

    public CreateSendHttpException(String message, int httpStatusCode, int apiErrorCode, String apiErrorMessage) {
        super(message);
        
        this.httpStatusCode = Status.fromStatusCode(httpStatusCode);
        this.apiErrorCode = apiErrorCode;
        this.apiErrorMessage = apiErrorMessage;
    }
    
    /**
     * @return The HTTP Status code from the failed request
     */
    public Status getHttpStatusCode() {
        return httpStatusCode;
    }
    
    /**
     * @return The API Error message from the failed request. 
     */
    public String getApiErrorMessage() {
        return apiErrorMessage;
    }

    /**
     * @return The API Error Code from the failed request. 
     */
    public int getApiErrorCode() {
        return apiErrorCode;
    }
}
