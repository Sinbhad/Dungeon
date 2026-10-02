package lib;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Custom generic ArrayList implementation takes advantage of the Iterable
 * interface to provide advanced for loop capabilities.
 *
 * @param <T> Generic Type
 */
public class CustomArrayList<T> implements Iterable<T> {
    private Object[] storage;
    private int size;

    public CustomArrayList() {
        storage = new Object[10];
        size = 0;
    }

    /**
     * Doubles the size of the ArrayList storage.
     */
    public void doubleStorageSize() {
        storage = Arrays.copyOf(storage, storage.length * 2);
    }

    /**
     * Adds data to the ArrayList and expands if necessary.
     */
    public void add(T data) {
        if (size == storage.length) {
            doubleStorageSize();
        }
        storage[size++] = data;
    }

    /**
     * Inserts data at a specific index, shifting existing elements right.
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (size == storage.length) {
            doubleStorageSize();
        }

        //Move elements right to make space
        for (int i = size; i > index; i--) {
            storage[i] = storage[i - 1];
        }

        storage[index] = data;
        size++;
    }

    /**
     * Replaces data at target-index without changing list size.
     */
    public void replaceAtIndex(int index, T data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        storage[index] = data;
    }

    public int size() {
        return size;
    }

    public void printStringAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        System.out.println(storage[index]);
    }

    public void printAll() {
        for (int i = 0; i < size; i++) {
            System.out.print("[" + storage[i] + "]");
        }
        System.out.println();
    }

    public boolean find(T data) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i], data)) {
                return true;
            }
        }
        return false;
    }

    public int findCount(T data) {
        int count = 0;
        for (int i = 0; i < size; i++) { // Fixed off-by-one error
            if (Objects.equals(storage[i], data)) {
                count++;
            }
        }
        return count;
    }

    public void remove(T data){
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i], data)) {
                removeAtIndex(i);
                return;
            }
        }
    }

    public void removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        //Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }

        //Clear last slot and decrement size
        storage[--size] = null;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public CustomArrayList<T> cloneClass() {
        CustomArrayList<T> arrayListCopy = new CustomArrayList<>();
        arrayListCopy.storage = Arrays.copyOf(this.storage, this.storage.length);
        arrayListCopy.size = this.size;
        return arrayListCopy;
    }

    @SuppressWarnings("unchecked")
    public T getAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) storage[index];
    }

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