/**
 * custom exception class to be thrown when a subscription object already contains the maximum number of different meals, and another is trying to be added.
 */
public class CannotAddNewMealToSubscriptionException extends Exception {
    public CannotAddNewMealToSubscriptionException(String message){
        super(message);
    }
}