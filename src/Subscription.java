/**
 * Subscription class contains a SortedLinkedList of ordered meals. This describes all the meals (and number of the ordered meals) in subscribers subscription.
 */
public class Subscription {
    private SortedLinkedList<OrderedMeal> orderedMeals;

    /**
     * Subscription() constructor creates a subscription object with an empty list orderedMeals;
     */
    public Subscription(){
        orderedMeals = new SortedLinkedList<OrderedMeal>();
    }

    /**
     * contains() checks whether orderedMeals contains any OrderedMeals objects with a meal equal to the parameter
     * @param meal to be checked whether in subscription
     * @return true if the meal is in the orderedMeals list
     */
    public boolean contains(Meal meal){
        for(OrderedMeal ordered:orderedMeals){
            if(ordered.getName().equals(meal.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * contains() checks whether orderedMeals contains any OrderedMeals objects equal to the OrderedMeal parameter
     * @param meal to be checked whether in subscription
     * @return true if the OrderedMeal is in the orderedMeals list
     */
    public boolean contains(OrderedMeal meal){
        for(OrderedMeal ordered:orderedMeals){
            if(ordered.getName().equals(meal.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * getIndex() finds the index of an OrderedMeal object with a meal field equal to the parameter
     * @param meal to find the index of in the list.
     * @return int index of OrderedMeal
     * @throws MealNotInSubscriptionException if the corresponding OrderedMeal could not be found
     */
    public int getIndex(Meal meal) throws MealNotInSubscriptionException{
        for(int i = 0; i<orderedMeals.size();i++) {
            OrderedMeal ordered = orderedMeals.get(i);
            if (ordered.getName().equals(meal.getName())) {
                return i;
            }
        }
        throw new MealNotInSubscriptionException("Requested Meal Not Found In Subscription");
    }

    /**
     * add() adds an OrderedMeal object to the list, or increases the numOrdered field of OrderedMeal object if it is already in the list by calling increaseNumOrderedMeal().
     * @param meal meal object requested to be added to the subscription
     * @param numOrdered int number of meal to be added
     * @throws RequestExceedsAvailabilityException if number ordered > remaining number available.
     * @throws CannotAddNewMealToSubscriptionException if the number of different meals already in list =3, so new meal type is not added.
     * @throws MealNotInSubscriptionException
     */
    public void add(Meal meal, int numOrdered) throws RequestExceedsAvailabilityException, CannotAddNewMealToSubscriptionException, MealNotInSubscriptionException {
        if(contains(meal)){
            int orderIndex = getIndex(meal);
            increaseNumOrderedMeal(orderIndex, numOrdered);
            return;
        }else if(orderedMeals.size()==3){
            throw new CannotAddNewMealToSubscriptionException("Subscription already contains the maximum number of different meals (3)");
        }
        OrderedMeal order = new OrderedMeal(meal, numOrdered);
        this.orderedMeals.add(order);
    }

    /**
     * increaseNumOrderedMeal() increases the numOrdered field of an OrderedMeal object in the subscription list
     * @param orderIndex index of OrderedMeal object
     * @param numIncrease number to increase numOrdered by
     * @throws RequestExceedsAvailabilityException if number ordered > remaining number available.
     */
    private void increaseNumOrderedMeal(int orderIndex, int numIncrease) throws RequestExceedsAvailabilityException{
        OrderedMeal order = orderedMeals.get(orderIndex);
        order.orderMore(numIncrease);
    }

    /**
     * decreaseNumOrderedMeal() decreases the numOrdered field of an OrderedMeal object in the subscription list, by finding the corresponding OrderedMeal index and calling decreaseNumOrderedMeal(index, numDecrease)
     * @param meal meal object requested to be decreased from subscription
     * @param numDecrease number to decrease numOrdered by
     * @throws RequestCancelsMoreThanOrderedException if the number being cancelled is more than has been ordered (preventing a negative numOrder field
     * @throws RequestExceedsAvailabilityException if cancelling specified number would result in remaining number available > total number available.
     * @throws MealNotInSubscriptionException
     */
    public void decreaseNumOrderedMeal(Meal meal, int numDecrease) throws RequestCancelsMoreThanOrderedException, RequestExceedsAvailabilityException, MealNotInSubscriptionException {
        int orderIndex = getIndex(meal);
        decreaseNumOrderedMeal(orderIndex,numDecrease);
    }

    /**
     * decreaseNumOrderedMeal() decreases the numOrdered field of an OrderedMeal object in the subscription list
     * @param orderIndex index of OrderedMeal to be altered
     * @param numDecrease num to decrease numOrdered field of OrderedMeal object by.
     * @throws RequestCancelsMoreThanOrderedException if the number being cancelled is more than has been ordered (preventing a negative numOrder field
     * @throws RequestExceedsAvailabilityException if cancelling specified number would result in remaining number available > total number available.
     */
    private void decreaseNumOrderedMeal(int orderIndex, int numDecrease) throws RequestCancelsMoreThanOrderedException, RequestExceedsAvailabilityException {
        OrderedMeal order = orderedMeals.get(orderIndex);
        order.reduceNumOrdered(numDecrease);
        if(order.getNumOrdered() == 0){
            orderedMeals.remove(orderIndex);
        }
    }

    /**
     * getNumDifferentMeals(0 returns the number of OrderedMeal objects in the subscription list
     * @return num OrderedMeal objects in the subscription list.
     */
    public int getNumDifferentMeals(){
        return orderedMeals.size();
    }

    /**
     * getOrderedMealInfo() returns the name of the OrderedMeal stored meal and the number of the meal ordered
     * @param index of OrderedMeal
     * @return string array containing meal name with index 0 and numOrdered at index 1
     */
    public String[] getOrderedMealInfo(int index){
        String[] orderDetails = {orderedMeals.get(index).toString(),Integer.toString(orderedMeals.get(index).getNumOrdered())};
        return orderDetails;
    }

    /**
     * getHowManyOfMealInSubscription() returns OrderedMeal numOrdered field
     * @param index index of desired OrderedMeal object in subscription list
     * @return in representing number of meal ordered by OrderedMeal.
     */
    public int getHowManyofMealInSubscription(int index){
        return orderedMeals.get(index).getNumOrdered();
    }
}


