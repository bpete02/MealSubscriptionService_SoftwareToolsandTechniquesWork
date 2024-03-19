import java.util.Collection;
import java.util.LinkedList;

/**
 * SortedLinkedList extends LinkedList methods, overriding the add method to place added objects in a sorted order.
 * @param <E extends Comparable<E>> // Object of type E must implement Comparable<E> interface.
 */
public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {

    /**
     * SortedLinkedList() constructor instantiates a new empty sortedlinkedlist
     */
    public SortedLinkedList() {
        super();
    }

    /**
     * SortedLinkedList constructor that instantiates a new SortedLinkedList and individually adds each item in a container to the list
     * @param c collection containing elements to be added to this collection
     */
    public SortedLinkedList(Collection<? extends E> c){
        super();
        addAll(c);
    }

    /**
     * add() method calls linkinposition() method to add an object to the appropriate position in the list
     * @param e element whose presence in this collection is to be ensured
     * @return true when added
     */
    public boolean add(E e) {
        linkInPosition(e);
        return true;
    }

    /**
     * addAll() method calls linkinposition() method to add an object to the appropriate position in the list, for each object in a container,
     * @param c collection containing elements to be added to this collection
     * @return true when all added
     */
    public boolean addAll(Collection<? extends E> c){
        for(E obj:c){
            add(obj);
        }
        return true;
    }

    /**
     * linkInPosition() method calls getPositionLinear() if the list is smaller (so fast to search) or getPositionBinarySearch() if the last is larger to find the appropriate index for the object.
     * Then uses LinkedList add(index) method to add the object the correct position
     * @param e element whose presence in this collection is to be ensured
     */
    private void linkInPosition(E e) {
        if(super.isEmpty()){
            super.addFirst(e);
        } else{
            int positionIndex;
            if(super.size()<25){
                positionIndex = getPositionLinear(e);

            }else{
                positionIndex = getPositionBinarySearch(e);
            }
            super.add(positionIndex, e);
        }
    }

    /**
     * getPositionLinear() compares objects by comparing results of toString().
     * Compares object to be added to each object in the list from index 0 to index n, until an object lexographically larger than the entering object is found.
     * @param e element whose presence in this collection is to be ensured
     * @return int equal to the index for the entering object to be put in the list at.
     */
    private int getPositionLinear(E e) {

        for (int i = 0; i < super.size(); i++) {
            Object inList = super.get(i);

            int compareToObjectInIndex = e.toString().compareTo(inList.toString());
            if (compareToObjectInIndex <= 0) {
                return i;
            }
        }
        return super.size();
    }

    /**
     * getPositionBinarySearch() compares objects by comparing results of toString().
     * Compares until an object lexographically larger than the entering object is found.
     * Uses a binary search algortihm. Example found here - https://www.geeksforgeeks.org/binary-search-in-java/
     * Orignal Author - Kartik (at geeksforgeeks - https://auth.geeksforgeeks.org/user/kartik)
     * Modifying Author - Billy Peters
     *
     * @param e element whose presence in this collection is to be ensured
     * @return int equal to the index for the entering object to be put in the list at.
     */
    private int getPositionBinarySearch(E e){

        int subLength;
        int lowerBound = 0;
        int upperBound = super.size();
        int pivot;
        E inList;
        do{
            subLength = upperBound-lowerBound;
            pivot = Math.round(subLength/2) + lowerBound;
            inList = super.get(pivot);
            int compareToObjectInIndex = e.compareTo(inList);
            if (compareToObjectInIndex < 0){
                upperBound = pivot;
            } else if(compareToObjectInIndex > 0){
                lowerBound = pivot;
            } else{
                return pivot;
            }
        }while(subLength>1);
        return upperBound;
    }

}
