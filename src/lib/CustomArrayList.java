package lib;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom generic ArrayList implementation, takes advantage of the Iterable
 * interface to provide advanced for loop capabilities.
 * Methods may vary from a standard implementation as they are added
 * based on project needs.
 * Author: Robert Poley
 *
 * @param <T> - Generic Type
 */
public class CustomArrayList<T> implements Iterable<T>{
    private Object[] storage;
    private int size;

    public CustomArrayList(){
        storage =  new Object[10];
        size = 0;
    }

    /**
     * Doubles the size of the ArrayList storage by copying the array values to a new one
     * of twice the size and then reassigning that data to the original.
     */
    public void doubleStorageSize(){
        //Create new object array twice the size of the given one
        Object[] newStorage = new Object[storage.length * 2];

        //Copy values
        for(int i = 0; i < storage.length; i++){
            newStorage[i] =  storage[i];
        }

        //Copy entire array
        storage = newStorage;
    }

    /**
     * Adds data to the ArrayList and expands if necessary.
     * @param data - generic value passed into the ArrayList.
     */
    public void add(T data){
        //Checks size of storage against amount of stored values before adding more
        if(size == storage.length){
            //Size doubling if full
           doubleStorageSize();
        }

        //Adds to the end of the list and updates the size
        storage[size] = data;
        size++;
    }

    /**
     * Allows a user to add a value to the list at a given index,
     * this does not remove values at the location, they are moved to make room.
     * @param index - the spot your data will sit in the list.
     * @param data - object to be added to the list.
     */
    public void addAtIndex(int index, T data) {
        //Checks size of storage against amount of stored values before adding more
        if (size == storage.length){
            //Size doubling if full
            doubleStorageSize();
        }

        //Move all elements to the right in storage before adding the new element
        for (int i = size; i > index; i--) {
            storage[i] = storage[i - 1];
        }

        //Assign the element at the given index and update size
        storage[index] = data;
        size++;
    }

    /**
     * Adds a value to the ArrayList but removes the value at the target index.
     * @param index - target location to store new value.
     * @param data - data to be added to the ArrayList.
     */
    public void replaceAtIndex(int index, T data){
        if (size == storage.length){
            doubleStorageSize();
        }
        storage[index] = data;
    }

    /**
     * Checks size of ArrayList.
     * @return - the amount of values currently stored returned as an int.
     */
    public int size(){
        return size;
    }

    /**
     * Allows the user to print an object held at a certain index.
     * Good for users who are storing strings or objects where overriding toString is applicable.
     * @param index - the numbered location of the value to be printed.
     */
    public void printStringAtIndex(int index){
        System.out.println(storage[index]);
    }

    /**
     * Prints all values in the list.
     */
    public void printAll(){
        for(int i = 0; i < size; i++){
            System.out.print("[" + storage[i] + "]");
        }
    }

    /**
     * Returns a boolean signaling whether a specific value
     * is held in the ArrayList or not.
     * @param data - value for the ArrayList to search for.
     */
    public boolean find(T data){
        //Flag holder set to false as this is the base case.
        boolean found = false;

        //Loop through the ArrayList and update the flag based on a check at each step
        for(int i = 0; i < size; i++){
            found = storage[i].equals(data);
        }
        return found;
    }

    /**
     * Counts the number of times an element occurs in an ArrayList.
     * @param data - target value to count sightings.
     * @return count returned as an int;
     */
    public int findCount(T data){
        int count = 0;

        //Loops through the ArrayList, updating the count each time the value at an index matches the passed in data
        for(int i = 0; i < size - 1; i++){
            if(storage[i].equals(data)){
                count++;
            }
        }
        return count;
    }

    /**
     * Removes a value at a given index and shifts remaining values to the left.
     * @param index - target index to be removed.
     */
    public void removeAtIndex(int index){
        //Clear data at index
        storage[index] = null;

        //Loop through the rest of the list shifting them to the left
        for(int i = index; i < size - 1; i++){
            storage[i] = storage[i + 1];
        }

        //Remove the end to ensure no data duplication occurs, then reduce the size
        storage[size - 1] = null;
        size--;
    }

    /**
     * Removes all stored values from the list.
     */
    public void clear(){
        storage = new Object[10];
        size = 0;
    }

    /**
     * Creates a copy of the entire ArrayList.
     * @return - copy of ArrayList.
     */
    public CustomArrayList<T> cloneClass(){
        CustomArrayList<T> arrayListCopy = new CustomArrayList<>();
        arrayListCopy.storage = new Object[this.storage.length];
        for(int i = 0; i < size; i++){
            arrayListCopy.storage[i] = this.storage[i];
        }
        arrayListCopy.size = this.size;
        return arrayListCopy;
    }

    @SuppressWarnings("unchecked")
    public T getAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        return (T) storage[index];
    }

    /**
     * Overridden Iterator interface, provides access to the advanced for loop
     * @return - returns iterator object.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return getAtIndex(index++);
            }
        };
    }
}
