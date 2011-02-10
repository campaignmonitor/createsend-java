package exceptions;


public class ServerErrorException extends CreateSendHttpException {    
    private static final long serialVersionUID = 7077800306811546975L;

    public ServerErrorException(int apiErrorCode, String apiErrorMessage) {
        super("The API call resulted in an internal server error", 500, apiErrorCode, apiErrorMessage);
    }
}
