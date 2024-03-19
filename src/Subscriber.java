/**
 * Subscriber class describes an instance of a subscriber with a firstname surname, and one subscription (ie a list of ordered meals).
 */
public class Subscriber implements Comparable<Subscriber> {

    private String firstName;
    private String surname;
    private Subscription subscription = new Subscription();

    /**
     * subscriber() constructor instantiating Subscriber object with values assigned to firstName and surname fields
     * @param first desired firstname
     * @param last desired surname
     */
    public Subscriber(String first, String last){
        this.firstName = first;
        this.surname = last;
    }

    /**
     * getSubscription() returns the subscriber object's subscription field
     * @return a reference variable to a Subscription object.
     */
    public Subscription getSubscription(){
        return subscription;
    }

    /**
     * getFirstName() returns the subscribers first name
     * @return string firstName field
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * getSurname() returns the subscribers surname.
     * @return string surname field
     */
    public String getSurname(){
        return surname;
    }

    /**
     * toString() returns the surname and firstname of the subscriber
     * @return string surname + firstname fields
     */
    public String toString(){
        return (surname+" "+firstName);
    }

    /**
     * subscriptionContains() checks whether the Subscription assigned to the subscriber contains a specified meal.
     * @param meal Meal to be checked whether in subscription
     * @return true if subscription.contains(Meal) is true
     */
    public boolean subscriptionContains(Meal meal){
        return subscription.contains(meal);

    }

    /**
     * subscriptionContains() checks whether the Subscription assigned to the subscriber contains a specified meal.
     * @param meal OrderdMeal to be checked whether in subscription
     * @return true if subscription.contains(OrderedMeal) is true
     */
    public boolean subscriptionContains(OrderedMeal meal){
        return subscription.contains(meal);
    }

    /**
     * addToSubscription() adds a number of a meal to the Subscription object of the subscriber.
     * @param meal Meal requested to be added
     * @param numToAdd number of Meal object requested to be added.
     * @throws RequestExceedsAvailabilityException if number ordered > remaining number available.
     * @throws CannotAddNewMealToSubscriptionException if the number of different meals already in list =3, so new meal type is not added.
     * @throws MealNotInSubscriptionException
     */
    public void addToSubscription(Meal meal, int numToAdd) throws RequestExceedsAvailabilityException, CannotAddNewMealToSubscriptionException, MealNotInSubscriptionException {
        subscription.add(meal,numToAdd);
    }

    /**
     * removeFromSubscription() removes a number of a meal to the Subscription object of the subscriber.
     * @param meal Meal requested to be decreased.
     * @param numToRemove number of Meal object requested to be decreased
     * @throws RequestExceedsAvailabilityException if cancelling specified number would result in remaining number available > total number available.
     * @throws MealNotInSubscriptionException
     * @throws RequestCancelsMoreThanOrderedException if the number being cancelled is more than has been ordered
     */
    public void removeFromSubscription(Meal meal, int numToRemove) throws RequestExceedsAvailabilityException, MealNotInSubscriptionException, RequestCancelsMoreThanOrderedException {
        subscription.decreaseNumOrderedMeal(meal,numToRemove);
    }

    /**
     * getHowManyOfMealInSubscription() finds the number of in the Subscribers Subscription field
     * @param meal to find how many the subscriber has ordered
     * @return 0 if no the meal cannot be found in the subscription, or the numOrdered of OrderedMeal with a meal type equal to the requested meal.
     */
    public int getHowManyOfMealInSubscription(Meal meal) {
        if (subscriptionContains(meal)) {
            try {
                int index = subscription.getIndex(meal);
                return subscription.getHowManyofMealInSubscription(index);
            } catch (MealNotInSubscriptionException e) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int compareTo(Subscriber otherSubscriber) {
        int lastNameComparison = this.surname.compareTo(otherSubscriber.getSurname());
        if(lastNameComparison == 0){
            return (this.firstName.compareTo(otherSubscriber.getFirstName()));
        } else{
            return lastNameComparison;
        }
    }
}
