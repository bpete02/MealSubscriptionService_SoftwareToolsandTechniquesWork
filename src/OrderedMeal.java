/**
 * Ordered Meal class describes a single order in a subscription.
 * Contains a meal type, and the number of meal ordered.
 */
public class OrderedMeal implements Comparable<OrderedMeal>{
    private Meal meal;
    private int numOrdered;

    /**
     * OrderedMeal() constructor instantiates a new OrderedMeal object, containing a meal reference, and the number of the meal ordered
     * @param meal Meal object to be referenced
     * @param numOrdered int number of meal ordered
     * @throws RequestExceedsAvailabilityException if numOrdered exceeds the number of meal remaining available
     */
    public OrderedMeal(Meal meal, int numOrdered) throws RequestExceedsAvailabilityException{
        this.meal = meal;
        this.meal.newOrder(numOrdered);
        this.numOrdered = numOrdered;
    }

    /**
     * orderMore() increases numOrdered field that represents the number of meal ordered.
     * @param numOrdered int to increase numOrdered field by.
     * @throws RequestExceedsAvailabilityException if numOrdered exceeds the number of meal remaining available
     */
    public void orderMore(int numOrdered) throws RequestExceedsAvailabilityException{
        this.meal.newOrder(numOrdered);
        this.numOrdered += numOrdered;
    }

    /**
     * reduceNumOrdered() decreases numOrdered field that represents the number of meal ordered.
     * @param numCancelled int to decrease numOrdered field by.
     * @throws RequestCancelsMoreThanOrderedException if the number being cancelled is more than has been ordered (preventing a negative numOrder field)
     * @throws RequestExceedsAvailabilityException if cancelling specified number would result in remaining number available > total number available.
     */
    public void reduceNumOrdered(int numCancelled) throws RequestCancelsMoreThanOrderedException, RequestExceedsAvailabilityException {

        if(numCancelled > this.numOrdered){
            throw new RequestCancelsMoreThanOrderedException("Entered an invalid number to cancel");
        } else{
            this.numOrdered -= numCancelled;
            this.meal.orderCancelled(numCancelled);
        }
    }

    /**
     * getNumOrederd() return the numOrdered field representing the number of the meal in this order
     * @return int numOrdered
     */
    public int getNumOrdered(){
        return numOrdered;
    }

    /**
     * toString() return the name of the ordered meal
     * @return meal object name field
     */
    public String toString(){
        return meal.toString();
    }

    /**
     * getMeal() returns the meal object reference
     * @return Meal meal
     */
    public Meal getMeal(){
        return meal;
    }

    /**
     * getName() returns the name of the orderedMeal
     * @return String meal object name field
     */
    public String getName(){
        return meal.getName();
    }

    @Override
    public int compareTo(OrderedMeal otherMeal) {
        return this.meal.compareTo(otherMeal.getMeal());
    }
}
