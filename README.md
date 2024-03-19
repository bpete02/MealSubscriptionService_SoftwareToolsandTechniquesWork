This task was compeleted as an introduction to polymorphism, inhertiance, and generic programming. 

The Driver.java class contains the main method, reading in data from a text file and provides a basic interface. 

Task instructions are summarised below. 

Task 1: SortedLinkedList<E> Class
- Extend java.util.LinkedList<E> to create SortedLinkedList<E>.
- Ensure items are sorted in ascending order.
- Implement a new insertion method for sorting.
- Ignore other list-modifying methods from LinkedList<E>.
- Use inheritance, not a fresh implementation.

Task 2: Subscription Management Program
- Develop a program for managing weekly frozen meal subscriptions.
- Allow office clerks to manage subscribers’ meal additions/removals.
- Read subscribers and meal availability from a file (input_data.txt).
- File format:
  	First line: Number of subscribers.
  	Following lines: Subscriber names.
  	Next line: Number of meal types.
  	Following lines: Meal names and weekly availability.

Program Functionalities
- Stores meal names and remaining quantities available for order.
- Tracks subscriber details, including names and subscribed meal types and quantities.
- Limits subscribers to a maximum of three different meal types at a time.

Interactive Clerk Interface
- Offers a menu with operations represented by lowercase letters:
	f - Finish the program and discard data.
	m - Display all meals and their availability.
	s - Show all subscriber details.
	a - Add meals to a subscriber’s subscription.
	r - Remove meals from a subscriber’s subscription.

Additional Assumptions
- Selecting f ends the program without saving data.
- Selecting m shows meal names with available quantities.
- Selecting s displays subscriber names and their meal subscriptions.

Data Storage with SortedLinkedList<E>
- Utilize SortedLinkedList<E> to store and sort meals and subscribers.
- Meals sorted by name in ascending lexicographic order.
- Subscribers sorted by surname, then first name in ascending lexicographic order.

Adding Meals to Subscription
- Verify subscriber validity and meal availability.
- Allow adding multiple units of a meal type in one transaction.
- If insufficient meals, print a notification letter to letters.txt.
- Update stored data after successful transactions.

Removing Meals from Subscription
- Validate subscriber and meal type during removal requests.
- Allow removal of one, some, or all meals of a type in a single transaction.
- Prompt for the quantity to remove.
- Update records after valid changes.

Operational Assumptions
- The company sells only pre-allocated meals.
- Services are exclusive to registered subscribers.
- Transactions are processed in a sequential manner.

Initial State
- At program startup, no meals are subscribed to by any subscriber.
