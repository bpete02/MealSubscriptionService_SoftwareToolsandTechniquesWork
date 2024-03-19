/**
 * custom exception thrown when trying to find an ordered meal in a subscription that does not exist in that subscription.
 */
public class MealNotInSubscriptionException extends Exception{
    public MealNotInSubscriptionException(String message){
        super(message);
    }
}
