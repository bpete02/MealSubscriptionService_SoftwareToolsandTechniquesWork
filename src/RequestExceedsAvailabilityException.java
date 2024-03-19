/**
 * custom exception thrown when trying to order more of a meal available.
 */
public class RequestExceedsAvailabilityException extends Exception{
    public RequestExceedsAvailabilityException(String message){
        super(message);
    }
}
