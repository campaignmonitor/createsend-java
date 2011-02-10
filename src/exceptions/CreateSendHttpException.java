package exceptions;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;


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
