import java.io.*;
import java.util.Scanner;

/**
 * class containing the main() method of the program, that deals with all input/output.
 */
public class Driver {
    private static SortedLinkedList<Subscriber> subscriberList = new SortedLinkedList<Subscriber>();

    private static SortedLinkedList<Meal> mealList = new SortedLinkedList<Meal>();

    /**
     * main() calls readInfoFromFile, then loops collecting a char from menuChoice() to decide the called method(s), until the collected character = f which ends the loop and causes the program to end.
     * @param args
     */
    public static void main(String[] args){
        try {
            readInfoFromFile();
        }catch(FileFormatException | FileNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println(e.getMessage()+" Please Check The Input File Exists and Is Correctly Formatted");
        }
        boolean close = false;
        while(!close) {
            char menuChoice = mainMenu();
            switch (menuChoice){
                case 'f':
                    close = true;
                    break;
                case 'm':
                    displayAllMealInfo();
                    break;
                case 's':
                    displayAllSubscriberInfo();
                    break;
                case 'a':
                    addMealToSubscriber();
                    break;
                case 'r':
                    removeOrderedMealFromSubscriber();
                    break;
            }
        }
    }

    /**
     * mainMenu() prints a main menu, then returns an input character collected from getCharInput()
     * @return char = f, m, s, a, or r.
     */
    private static char mainMenu(){
        char[] menuOptions ={'f','m','s','a','r'};
        return Character.toLowerCase(getCharInput(menuOptions,"/// Frozen Food Company ///\n\nPlease enter one of the following:\nF - To finish running the program\nM - To display on the screen information about all the meals\nS - To display on screen information about all the subscribers\nA - To update the stored data when a registered subscriber adds meals to their subscription\nR - To update the stored data when a registered subscriber removes meals from their subscription"));
    }

    /**
     * readInfoFromFile() reads subscriber and meal info form input_data.txt file, and updates the SortedLinkedList fields of subscribers and meals.
     * @throws FileFormatException if the input_data.txt file does not follow an expected pattern.
     * @throws FileNotFoundException if input_data.txt file is not cannot be found within the program files.
     */
    private static void readInfoFromFile() throws FileFormatException, FileNotFoundException{
        File frozenFoodFile;
        Scanner fileReader;
        try {
            frozenFoodFile = new File("input_data.txt");
            fileReader = new Scanner(frozenFoodFile);
        }catch(FileNotFoundException e){
            throw new FileNotFoundException("Input File Could Not Be Found");
        }
        if(fileReader.hasNextLine()){
            try{
                String numSubscribersLine = fileReader.nextLine().trim();
                int numSubscribers = Integer.parseInt(numSubscribersLine);
                for(int i = 0; i < numSubscribers; i++){
                    String[] subscriberName;
                    subscriberName = fileReader.nextLine().split(" ");
                    Subscriber newSubscriber = new Subscriber(subscriberName[0].trim(),subscriberName[1].trim());
                    subscriberList.add(newSubscriber);
                }
            }catch(Exception e){
                throw new FileFormatException("Subscriber Information Is Not Formatted Correctly in File");
            }
            try{
                String numMealsLine = fileReader.nextLine().trim();
                int numMeals = Integer.parseInt(numMealsLine);
                for(int i = 0; i < numMeals; i++){
                    String mealName = fileReader.nextLine().trim();
                    int numMealAvailable = Integer.parseInt(fileReader.nextLine().trim());
                    Meal newMeal = new Meal(mealName,numMealAvailable);
                    mealList.add(newMeal);
                }
            } catch(Exception e){
                throw new FileFormatException("Meal Information Is Not Formatted Correctly in File");
            }

        }
    }

    /**
     * displayAllMealInfo() outputs each meals name and number available from the SortedLinkedList Meal field.
     */
    private static void displayAllMealInfo(){
        System.out.println("Meal : Number Remaining Available");
        for(Meal meal:mealList){
            System.out.println(meal.getName()+" : "+meal.getRemainingAvailable());
        }
        System.out.println("\n");
    }

    /**
     * displayAllSubscriberInfo() outputs each subscriber's name and meals subscribed to from the SortedLinkedList Subscriber field.
     */
    private static void displayAllSubscriberInfo(){
        for(Subscriber subscriber:subscriberList){
            System.out.println(subscriber.getFirstName()+" "+subscriber.getSurname());
            Subscription subscriberSubscription = subscriber.getSubscription();
            if(subscriberSubscription.getNumDifferentMeals()<1){
                System.out.println("Not Subscribed To Any Meals\n");
            }else {
                for (int i = 0; i < subscriberSubscription.getNumDifferentMeals(); i++) {
                    String[] mealSubscriptionDetails = subscriberSubscription.getOrderedMealInfo(i);
                    System.out.println(mealSubscriptionDetails[0] + " : " + mealSubscriptionDetails[1]+"\n");
                }
            }
        }
    }

    /**
     * addMealToSubscriber() Collects user input to select a subscriber and add a selected meal to their subscription
     * calls addLetterToFile() if the meal cannot be added to a subscriber's subscription.
     */
    private static void addMealToSubscriber(){
        Subscriber selectedSubscriber;
        Meal selectedMeal;
        try {
            selectedSubscriber = selectSubscriber();
            selectedMeal = selectMeal(true,"Enter The Name Of the Meal To Add ");
        }catch(DetailsNotOnFileException e){
            System.out.println(e.getMessage());
            return;
        }
        int numMealWanted;
        if(selectedSubscriber.subscriptionContains(selectedMeal)){
            numMealWanted = getIntInput(0,selectedSubscriber.getFirstName()+" "+selectedSubscriber.getSurname()+" Is Already Subscribed To "+selectedSubscriber.getHowManyOfMealInSubscription(selectedMeal)+" Of This Meal. Enter How Many More Of This Meal To Add To The Subscription.");
        }else {
            numMealWanted = getIntInput(0, "Enter The Number of " + selectedMeal.getName() + " to be Added to The Subscription");
        }
        try{
            selectedSubscriber.addToSubscription(selectedMeal, numMealWanted);
        } catch (MealNotInSubscriptionException e) {
            System.out.println(e.getMessage());
            return;

        }catch (RequestExceedsAvailabilityException | CannotAddNewMealToSubscriptionException e ){
            addLetterToFile(selectedSubscriber, selectedMeal, numMealWanted, e);
        }
    }

    /**
     * addLetterToFile() appends letters.txt with a selected message.
     * @param subscriber subscriber to be addressed
     * @param meal meal that failed to be added to subscribers subscription
     * @param numRequested number of the meal requested
     * @param e exception that was the reason why the meal/meal number could not be added to the subscription.
     */
    private static void addLetterToFile(Subscriber subscriber, Meal meal, int numRequested,Exception e){
        File letterFile = new File("letters.txt");
        PrintWriter letterWriter;
        try{
            letterFile.createNewFile();
            letterWriter = new PrintWriter(new FileWriter(letterFile, true));
        } catch (FileNotFoundException exc) {
            System.out.println(letterFile.getName()+(" Could Not Be Found. Letters Will Not Be Produced"));
            return;
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
            return;
        }
        String letter = "Dear "+subscriber.getFirstName()+ " "+ subscriber.getSurname()+", We're sorry, we cannot meet your request of adding "+numRequested+" "+meal.getName()+"s to your subscription. ";
        if(e instanceof RequestExceedsAvailabilityException){
            System.out.println("Order cannot be added to subscription. Not enough of the selected Meal are available.");
            letter += "Unfortunately only "+meal.getRemainingAvailable()+" are left available. Please let us know if we can add or change any other meal, or number of meals, to you subscription." ;
        } else if (e instanceof CannotAddNewMealToSubscriptionException){
            System.out.println("Order cannot be added to subscription. Subscription already contains the maximum number of different meals.");
            letter += "Unfortunately you have already reached the maximum number of different meals allowed in your subscription, however you may request more of the meals you are already subscribed to, or remove them from your subscription at any time. Please let us know if we can add or change any other meal, or number of meals, to you subscription.\n" ;
        }else{
            return;
        }
        letterWriter.println(letter);
        letterWriter.println("");
        System.out.println("A letter has been created");
        letterWriter.close();
    }

    /**
     * removeOrderedMealFromSubscriber() Collects user input to select a subscriber and remove a number of a selected meal from their subscription
     */
    private static void removeOrderedMealFromSubscriber() {
        Subscriber selectedSubscriber;
        Meal selectedMeal;
        try {
            selectedSubscriber = selectSubscriber();
            System.out.println(selectedSubscriber.getFirstName()+" "+selectedSubscriber.getSurname()+ " is Subscribed To ");
            displayOneSubscriberOrders(selectedSubscriber);
            selectedMeal = selectMeal(false,"Enter The Name Of A Meal To Be Removed From Or Decreased In The Subscription");
        }catch(DetailsNotOnFileException e){
            System.out.println(e.getMessage());
            return;
        }

        if(selectedSubscriber.subscriptionContains(selectedMeal)){
            int NumToRemove = getIntInput(0,"This Subscriber Has "+selectedSubscriber.getHowManyOfMealInSubscription(selectedMeal)+" "+selectedMeal.getName()+"s in Their Subscription. \nEnter The Number To Be Removed From The Subscription. Removing All Will Remove The Meal From The Subscription Entirely.");
            try{
                selectedSubscriber.removeFromSubscription(selectedMeal,NumToRemove);
            } catch (RequestExceedsAvailabilityException | RequestCancelsMoreThanOrderedException | MealNotInSubscriptionException e) {
                System.out.println(e.getMessage());
                return;
            }
        }else{
            System.out.println("The Entered Meal Details Do Not Match "+selectedSubscriber.getFirstName()+" "+ selectedSubscriber.getSurname()+" Subscribed Meals");
        }
    }

    /**
     * displayOneSubscriberOrders() outputs a selected subscribers subscription info (ie meals and meal number)
     * @param selectedSubscriber Subscriber object to find details from.
     */
    private static void displayOneSubscriberOrders(Subscriber selectedSubscriber){
        Subscription subscriberSubscription = selectedSubscriber.getSubscription();
        if(subscriberSubscription.getNumDifferentMeals()<1){
            System.out.println("Not Subscribed To Any Meals\n");
        }else {
            for (int i = 0; i < subscriberSubscription.getNumDifferentMeals(); i++) {
                String[] mealSubscriptionDetails = subscriberSubscription.getOrderedMealInfo(i);
                System.out.println(mealSubscriptionDetails[0] + " : " + mealSubscriptionDetails[1]+"\n");
            }
        }
    }

    /**
     * selectSubscriber() collects string input for firstname and last name, and compares to the list of Subscribers field to find a match to a previously instantiated Subscriber.
     * @return Subscriber reference to object in SortedLinkedList of Subscribers field.
     * @throws DetailsNotOnFileException if the names do not match a Subscriber in the list of Subscribers,
     */
    private static Subscriber selectSubscriber() throws DetailsNotOnFileException{
        for(Subscriber subscriber:subscriberList) {
            System.out.println(subscriber.getFirstName() + " " + subscriber.getSurname());
        }
        String inputFirstName = getStringInput("Enter the Subscribers First Name").toLowerCase();
        String inputSurname = getStringInput("Enter the Subscribers Surname").toLowerCase();

        for(Subscriber subscriber:subscriberList){
            if(inputFirstName.equals(subscriber.getFirstName().toLowerCase()) && inputSurname.equals(subscriber.getSurname().toLowerCase())){
                return subscriber;
            }
        }
        throw new DetailsNotOnFileException("The Subscriber Details Entered Do Not Match Any On File");
    }

    /**
     * selectMeal() Collects string user input to compare to SortedLinkedList of Meal to find a match to a previously instantiated Meal object.
     * @param listAll if true, will output all meal names contained in SortedLinkedList meals field
     * @param message string message to be output before user input is taken.
     * @return reference to Meal object in SortedLinkedList meals field
     * @throws DetailsNotOnFileException if the input name does not match a Meal in the list of Meals,
     */
    private static Meal selectMeal(boolean listAll,String message) throws DetailsNotOnFileException{
        if(listAll)
            displayAllMealInfo();
        String inputMealName = getStringInput(message).toLowerCase();
        for(Meal meal:mealList){
            if(inputMealName.equals(meal.getName().toLowerCase())){
                return meal;
            }
        }
        throw new DetailsNotOnFileException("The Meal Details Entered Do Not Match Any On File");
    }


    /**
     * getStringInput() reads a line of user input, and removes white space at either end of the string.
     * @param message a message to be output before user input is taken
     * @return String of user input
     */
    private static String getStringInput(String message){
        System.out.println(message);
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine().trim();
        return inputString;
    }

    /**
     * getCharInput() calls getStringInput() and converts to a single character. If input was not one character long, keeps recalling getStringInput() until the input is one character long.
     * @param permitted a list of characters to be accepted as a valid input.
     * @param message a message to be output before user input is taken
     * @return a character taken from user input.
     */
    private static char getCharInput(char[] permitted, String message){
        String inputString = getStringInput(message);
        char inputChar;
        if(inputString.length()==1) {
            inputChar = inputString.charAt(0);
            boolean permittedInput = false;
            for (char option : permitted) {
                if (option == inputChar) {
                    permittedInput = true;
                    break;
                }
            }
            if (!permittedInput) {
                System.out.println("Input Not Permitted as an Option");
                inputChar = getCharInput(permitted,message);
            }
        } else{
            System.out.println("Input Not Permitted. Please Input One Character");
            inputChar = getCharInput(permitted,message);
        }
        return inputChar;
    }

    /**
     * getIntInput() calls getStringInput() and converts to an integer. If input cannot be converted, keeps recalling getStringInput() until the input can be converted to an int.
     * @param min minimum int value allowed to be input
     * @param message a message to be output before user input is taken
     * @return an integer taken from user input.
     */
    private static int getIntInput(int min,String message){
        String inputString = getStringInput(message);
        int inputInt;
        try{
            inputInt = Integer.parseInt(inputString);
        } catch (Exception e){
            System.out.println("Invalid Input. Please Enter a Number");
            inputInt = getIntInput(min,message);
        }

        if(inputInt<min){
            System.out.println("Input Out of Bounds. Select a Number Greater Than "+min);
            inputInt = getIntInput(min,message);
        }

        return inputInt;
    }


}
