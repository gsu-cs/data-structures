/**
 * @author Kyle McKean
 * @since  10-05-016
 * <h1> Assignment 3: ArrayList </h1>
 * Compilation: java testarray.java
 * Execution: java -jar testarray.jar
 * Documentation: javadoc ArrayList.java
 * -- This source code is fully javadoc compliant
 * Purpose of Program:
 * -- The purpose of the program 'testarray' is to test the class
 *     below. It should test every operation listed on the website.
 * Solution:
 * -- Just implement a growable array.
 * -- Growable Arrays have a max capacity and when you try to set a
 *    value past the capacity the array needs to be reallocated with a
 *    larger capacity.
 * Data Strutures: Arrays
 * Description of Use:
 * -- Follow Execution above
 * Expected I/O:
 * -- Default construtor initialized
 *    With capacity construtor initialized (capacity = 2)
 *    Added ten integers (0..9)
 *    Added 20 to index 3
 *    The collection looks like: [ 0 1 2 20 3 4 5 6 7 8 9 ]
 *    The 6th element is: 4
 *    The size of this collection is: 11
 *    The collection is not empty
 *    The element 10 is not in the collection
 *    The element '6' is at index: 7
 *    Removed element at index 2
 *    The collection looks like: [ 0 1 20 3 4 5 6 7 8 9 ]
 * Explaination of Classes:
 * -- See above each class
 * <strong> Important </strong>
 * -- Assume every parameter has an implicit non null pre-condition
 *    Assume every return value has an implicit non null post-condition
 */

/**
 * The <tt>ArrayList</tt> Class is just a class that wraps a growable array
 * in a nice API.
 * See API documentation below.
 **/
public class ArrayList<E> {
    private int capacity;
    private int pointer;
    private E[] elements;
    public ArrayList() { this(10); }
    public ArrayList(int initialCap) {
        this.capacity = initialCap;
        this.pointer  = 0;
        this.elements = (E[]) new Object[initialCap];
    }
    /**
     * Appends the specified element to the end of this list.
     * @param e element to be appended to this list
     */
    public void add(E element) { this.add(this.pointer, element); }
    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     * @pre 0 <= index <= size()
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    public void add(int index, E element) {
        if (this.pointer == this.capacity) {
            this.capacity *= 2;
            E[] grown = (E[]) new Object[this.capacity];
            for(int i = 0; i < index; i++) {
                grown[i] = this.elements[i];
            }
            grown[index] = element;
            for(int i = index + 1; i <= this.pointer; i++) {
                grown[i] = this.elements[i - 1];
            }
            this.elements = grown;
        } else {
            for(int i = this.pointer - 1 ; i >= index; i--) {
                this.elements[i + 1] = this.elements[i];
            }
            this.elements[index] = element;
        }
        pointer++;
    }
    /**
     * Returns the element at the index given without removing it.
     * @pre 0 <= index <= size()
     * @return E element at the given index
     **/
    public E get(int index) { return this.elements[index]; }
    /**
     * Returns the amount of values in the internal array.
     * @return int length
     **/
    public int size() { return this.pointer; }
    /**
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() { return this.pointer == 0; }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt>.
     * @param E element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    public boolean isIn(E element) { return this.find(element) != -1; }
    public int find(E element) {
        for(int i = 0; i < this.pointer; i++) {
            if (this.elements[i].equals(element)) return i;
        }
        return -1;
    }
    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     * @pre 0 <= index <= size()
     * @param index the index of the element to be removed
     */
    public void remove(int index) {
        for(int i = index + 1; i < this.pointer; i++) {
            this.elements[i-1] = this.elements[i];
        }
        pointer--;
    }
    /**
     * Not Required just for pretty printing
     **/
    public String toString() {
        String str = "[";
        for(int i = 0; i < this.pointer; i++) {
            str += " " + this.elements[i];
        }
        return str + " ]";
    }
}
