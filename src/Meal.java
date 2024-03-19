/**
 * Meal Class Describes a type of meal available for order
 *
 * Stores meal name, total number available before any subscriptions, and the remaining number available for adding to a subscription (if any have been subscribed to)
 */
public class Meal implements Comparable<Meal>{
    private String name;

    private int totalNum;

    private int remainingAvailable;

    /**
     * Meal constructor. Only constuctor available so meal bust be given a name and total number available
     * @param name string specifying name of the meal
     * @param totalNum int declaring the initial number of meals available.
     */
    public Meal(String name, int totalNum){
        this.name = name.trim();
        this.totalNum = totalNum;
        this.remainingAvailable = this.totalNum;
    }

    /**
     * newOrder() called in when an order is created or added to, reduces meal remaining number available by the number ordered.
     * @param numOrders an integer specifying the amount to decrease remainingAvailable field by
     * @throws RequestExceedsAvailabilityException if number ordered > remaining number available.
     */
    public void newOrder(int numOrders) throws RequestExceedsAvailabilityException{
        if(numOrders>remainingAvailable){
            throw new RequestExceedsAvailabilityException("Requested number of meals exceeds availability");
        }else{
            this.remainingAvailable = this.remainingAvailable - numOrders;
        }
    }

    /**
     * orderCancelled() called when an order reduced number of meals ordered. Increases meal remaining number available by the number cancelled
     * @param numCancelled an integer specifying the amount to increase remainingAvailable field by
     * @throws RequestExceedsAvailabilityException if cancelling specified number would result in remaining number available > total number available.
     */
    public void orderCancelled(int numCancelled) throws RequestExceedsAvailabilityException{
        if(numCancelled+remainingAvailable>totalNum){
            throw new RequestExceedsAvailabilityException("The Number Of Cancellable Meals Should Not Exist, As They Exceed The Total Number of the Meal Originally Available");
        }else{
            this.remainingAvailable = this.remainingAvailable+numCancelled;
        }
    }

    /**
     * getRemainingAvailable
     * @return remaining number available field
     */
    public int getRemainingAvailable(){
        return remainingAvailable;
    }

    /**
     * getName()
     * @return name field
     */
    public String getName(){
        return name;
    }

    /**
     * toString()
     * @return name field
     */
    public String toString(){
        return name;
    }

    @Override
    public int compareTo(Meal otherMeal) {
        return this.name.compareTo(otherMeal.getName());
    }
}
