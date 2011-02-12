package com.createsend.util.exceptions;


public class NotFoundException extends CreateSendHttpException {
    private static final long serialVersionUID = -7730164870627844838L;

    public NotFoundException(int apiErrorCode, String apiErrorMessage) {
        super("The requested resource does not exist", 404, apiErrorCode, apiErrorMessage);
    }
}
