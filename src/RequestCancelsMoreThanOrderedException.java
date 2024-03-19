/**
 * custom exception thrown when trying to cancel more meals than ordered.
 */
public class RequestCancelsMoreThanOrderedException extends Exception{
    public RequestCancelsMoreThanOrderedException(String message){
        super(message);
    }
}
