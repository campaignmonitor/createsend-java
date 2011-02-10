package com.createsend.util;

public class BadRequestException extends CreateSendHttpException {    
    private static final long serialVersionUID = -2724621705342365927L;
        
    public BadRequestException(int apiErrorCode, String apiErrorMessage) { 
        super(
            String.format("The CreateSend API responded with the following client error %d: %s",
                apiErrorCode, apiErrorMessage), 
            400,
            apiErrorCode,
            apiErrorMessage);
    }
}
