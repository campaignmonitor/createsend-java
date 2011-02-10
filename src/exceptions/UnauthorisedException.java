package exceptions;


public class UnauthorisedException extends CreateSendHttpException {
    private static final long serialVersionUID = 4596052166026262638L;
    
    public UnauthorisedException(int apiErrorCode, String apiErrorMessage) { 
        super(
                String.format("The CreateSend API responded with the following authentication error %d: %s",
                    apiErrorCode, apiErrorMessage), 
                401,
                apiErrorCode,
                apiErrorMessage);        
    }
}
