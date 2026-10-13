package myAdapter;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Stack adapter that implements the HList interface, providing a LIFO data structure backed
 * by a java.util.Vector of CLDC 1.1 (Adapter pattern: the Vector is the adaptee).
 * This implementation supports null elements and elements of any type, and follows the behavior
 * of java.util.Stack and of the J2SE 1.4.2 Collections Framework.
 * <p>
 * Mapping between the stack and the list view: the element at index 0 is the bottom of the stack
 * and the element at index size() - 1 is the top. push() appends at the end, pop() and peek() work on
 * the last element, and the iterators traverse the elements from the bottom (index 0) to the top.
 * </p>
*/
public class StackAdapter implements HList {

    private Vector adaptee; // index 0 = bottom of the stack, index size() - 1 = top of the stack

    /**
     * Creates an empty Stack.
     */ 
    public StackAdapter() { 
        adaptee = new Vector(); 
    }

    // ------------------------------------------
    // STACK METHODS
    // ------------------------------------------

    /**
     * Tests if this stack is empty.
     * 
     * @return true if and only if this stack contains no items; false otherwise.
     */
    public boolean empty() { 
        return adaptee.isEmpty();
    }
    
    /**
     * Looks at the object at the top of this stack without removing it from the stack.
     * 
     * @return the object at the top of this stack (the last item of the Vector object).
     * @throws EmptyStackException if this stack is empty.
     */
    public Object peek() { 
        if(adaptee.isEmpty()){
            throw new EmptyStackException();
        }
        return adaptee.lastElement();
    }

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     * 
     * @return The object at the top of this stack (the last item of the Vector object).
     * @throws EmptyStackException if this stack is empty.
     */
    public Object pop() { 
        if(adaptee.isEmpty()){
            throw new EmptyStackException();
        }
        Object lastElement = adaptee.lastElement();
        adaptee.removeElementAt(adaptee.size() - 1);
        return lastElement;
     }

    /**
     * Pushes an item onto the top of this stack (the item is appended at the end of the Vector).
     *
     * @param item the item to be pushed onto this stack.
     * @return the item argument.
     */
    public Object push(Object item) { 
        adaptee.addElement(item);
        return item;   
    }

    /**
     * Returns the 1-based position where an object is on this stack. If the object o occurs as an item in this stack,
     * this method returns the distance from the top of the stack of the occurrence nearest the top of the stack;
     * the topmost item on the stack is considered to be at distance 1. The equals method is used to compare o to the items in this stack.
     * 
     * @param o the desired object.
     * @return the 1-based position from the top of the stack where the object is located; the return value -1 indicates that the object is not on the stack.
    */
    public int search(Object o) { 
        int position = 1; // 1-based distance from the top of the element currently checked
        for(int i = adaptee.size() - 1; i >= 0; i--) // loop through the vector from the end (top of the stack)
        {
            Object current = adaptee.elementAt(i);
            // if the object is null verify that also current is null; otherwise use equals() as usual.
            if (o == null ? current == null : o.equals(current)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    // ------------------------------------------
    // HCOLLECTION'S METHODS
    // ------------------------------------------

    /**
     * Returns the number of elements in this HCollection. If this HCollection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @return the number of elements in this HCollection.
     */
    public int size() {
        return adaptee.size();
    } 

    /**
     * Returns true if this HCollection contains no elements.
     * 
     * @return true if this HCollection contains no elements.
     */
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    /**
     * Returns true if this HCollection contains the specified element. More formally, returns true if and only if this HCollection contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * 
     * @param o element whose presence in this HCollection is to be tested.
     * @return true if this HCollection contains the specified element.
     */
    public boolean contains(Object o) { 
        // No ClassCastException or NullPointerException is thrown: this stack accepts
        // elements of any type, including null.
        return adaptee.contains(o);
    }

    /**
     * Returns an HIterator over the elements in this HCollection, from the bottom (index 0) to the top of the stack.
     * The HIterator works directly on this stack, so changes made after its creation are visible through it.
     *
     * @return an HIterator over the elements in this HCollection.
     */
    public HIterator iterator() {
        HIterator iterator = new StackIterator(); // using the nested class constructor
        return iterator;
     }

    /**
     * Returns an array containing all of the elements in this HCollection in proper sequence. The returned array will be "safe" in that no references to it are maintained by this HCollection. (In other words, this method must allocate a new array even if this HCollection is backed by an array). The caller is thus free to modify the returned array.
     * 
     * @return an array containing all of the elements in this HCollection.
     */
    public Object[] toArray() {
        Object[] newArray = new Object[adaptee.size()]; // instantiate new memory
        adaptee.copyInto(newArray);
        return newArray; // returned array is "safe". No references to it are maintained by this HCollection
    }

    /**
     * Returns an array containing all of the elements in this HCollection, from the bottom to the top of the stack.
     * If the HCollection fits in the specified array, it is stored and returned therein; if the array has more
     * elements than this HCollection, the element immediately following the last one is set to null.
     * Otherwise, a new array with the size of this HCollection is allocated and returned. Since CLDC 1.1 does not
     * provide java.lang.reflect.Array, the new array cannot have the runtime type of the specified array:
     * it is always an Object[].
     *
     * @param a the array into which the elements of this HCollection are to be stored, if it is big enough.
     * @return an array containing the elements of this HCollection.
     * @throws NullPointerException if the specified array is null.
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this HCollection.
     */
    public Object[] toArray(Object[] a) {
        // the ArrayStoreException is thrown automatically by copyInto() when an element doesn't fit the type of a
        if (a == null) {
            throw new NullPointerException();
        }
        int size = adaptee.size();
        if(a.length < size) {
            Object[] arrayToReturn = new Object[size];
            adaptee.copyInto(arrayToReturn);
            return arrayToReturn;
        }
        adaptee.copyInto(a);
        if(a.length > size) {
            a[size] = null;
        }
        return a; 
     }

    /**
     * Ensures that this HCollection contains the specified element by appending it on top of the stack
     * (same effect as push). Since duplicates and null are allowed, the stack always changes.
     *
     * @param o element whose presence in this HCollection is to be ensured.
     * @return true, because this stack always changes as a result of the call.
     */
    public boolean add(Object o) { 
        adaptee.addElement(o);
        return true;
    }
    
    /**
     * Removes a single instance of the specified element from this HCollection, if it is present: the occurrence with the lowest index (the one nearest to the bottom of the stack) is removed. More formally, removes an element e such that (o==null ? e==null : o.equals(e)), if this HCollection contains one or more such elements. Returns true if this HCollection contained the specified element (or equivalently, if this HCollection changed as a result of the call).
     * 
     * @param o element to be removed from this HCollection, if present.
     * @return true if this HCollection changed as a result of the call.
     */
    public boolean remove(Object o) { 
       return adaptee.removeElement(o);
    }
    
    /**
     * Returns true if this HCollection contains all of the elements in the specified HCollection.
     * 
     * @param c HCollection to be checked for containment in this HCollection.
     * @return true if this HCollection contains all of the elements in the specified HCollection.
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean containsAll(HCollection c) { 
        if (c == null) throw new NullPointerException();
        HIterator scanner = c.iterator();
        while(scanner.hasNext()){
            if(!adaptee.contains(scanner.next())){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified HCollection to this HCollection. The behavior of this operation is undefined if the specified HCollection is modified while the operation is in progress. (This implies that the behavior of this call is undefined if the specified HCollection is this HCollection, and this HCollection is nonempty.)
     * 
     * @param c elements to be inserted into this HCollection.
     * @return true if this HCollection changed as a result of the call.
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean addAll(HCollection c) { 
        if (c == null) throw new NullPointerException();
        HIterator scanner = c.iterator();
        boolean isChanged = false;
        while(scanner.hasNext()){
            adaptee.addElement(scanner.next());
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Removes all this HCollection's elements that are also contained in the specified HCollection. After this call returns, this HCollection will contain no elements in common with the specified HCollection.
     * 
     * @param c elements to be removed from this HCollection.
     * @return true if this HCollection changed as a result of the call.
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean removeAll(HCollection c) { 
        if (c == null) throw new NullPointerException();
        HIterator scanner = this.iterator(); // this stack's own HIterator: its remove() keeps the scan consistent
        boolean isChanged = false;
        while(scanner.hasNext()){ // the whole stack is scanned so that every occurrence is removed, not only the first one
            if(c.contains(scanner.next())){
                scanner.remove(); // if collection contains this element of the stack, remove it
                isChanged = true;
            }
        }
        return isChanged;
    }   

    /**
     * Retains only the elements in this HCollection that are contained in the specified HCollection. In other words, removes from this HCollection all of its elements that are not contained in the specified HCollection.
     * 
     * @param c elements to be retained in this HCollection.
     * @return true if this HCollection changed as a result of the call.
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean retainAll(HCollection c) { 
        if (c == null) throw new NullPointerException();
        HIterator scanner = this.iterator(); // this stack's own HIterator: its remove() keeps the scan consistent
        boolean isChanged = false;
        while(scanner.hasNext()){ // the whole stack is scanned so that every occurrence is checked
            if(!c.contains(scanner.next())){ // the exact opposite of removeAll
                scanner.remove();
                isChanged = true;
            }
        }
        return isChanged;  
    }

    /**
     * Removes all of the elements from this HCollection. This HCollection will be empty after this method returns unless it throws an exception.
     */
    public void clear() {
        adaptee.removeAllElements();
    }

    /**
     * Compares the specified object with this HCollection for equality. Returns true if and only if the specified
     * object is also an HList, both lists have the same size, and all corresponding pairs of elements are equal
     * (two elements e1 and e2 are equal if (e1==null ? e2==null : e1.equals(e2))). In other words, two lists are
     * equal if they contain the same elements in the same order.
     *
     * @param o Object to be compared for equality with this HCollection.
     * @return true if the specified object is equal to this HCollection.
     */
    public boolean equals(Object o) {
        if(o == this) return true; 
        if(o == null) return false; 
        if(!(o instanceof HList)) return false;

        HList other = (HList) o;
        if(this.size() != other.size()) return false; // preventive dimensional check
        int count = 0; // number of positions in which the two lists hold equal elements
        HIterator stackScanner = this.iterator();
        HIterator otherScanner = other.iterator();

        while(stackScanner.hasNext() && otherScanner.hasNext()){
            Object firstElement = stackScanner.next();
            Object secondElement = otherScanner.next();
            boolean areEqual = (firstElement == null) ? (secondElement == null) : firstElement.equals(secondElement);
            if (areEqual) count ++;
        }
        return count == adaptee.size();
    }

    /**
     * Returns the hash code value for this HCollection. In particular, c1.equals(c2) implies that c1.hashCode()==c2.hashCode().
     * 
     * @return the hash code value for this HCollection.
     */
    public int hashCode() { 
        // Standard Java 1.4.2 formula: starts with a hash of 1, then applies (31 * previous_hash + element_hash) for each element.  
        int hashCode = 1;
        HIterator scanner = this.iterator();
        while (scanner.hasNext()) {
            Object obj = scanner.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode()); // if the object is null sum zero (handling the null case)
        }
        return hashCode;
    }

    // ------------------------------------------
    // HLIST METHODS
    // ------------------------------------------

    /**
     * Returns the element at the specified position in this HList.
     * 
     * @param index index of the element to return.
     * @return the element at the specified position in this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object get(int index) {
        if (index < 0 || index >= adaptee.size()) {
            throw new IndexOutOfBoundsException();
        }
        return adaptee.elementAt(index);
    }

    /**
     * Replaces the element at the specified position in this HList with the specified element.
     * 
     * @param index index of the element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object set(int index, Object element) { 
        if (index < 0 || index >= adaptee.size()) {
            throw new IndexOutOfBoundsException();
        }
        Object old = adaptee.elementAt(index);
        adaptee.setElementAt(element, index);
        return old;
    }
    
    /**
     * Inserts the specified element at the specified position in this HList. Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * 
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public void add(int index, Object element) {
        if (index < 0 || index > adaptee.size()) {
            throw new IndexOutOfBoundsException();
        }
        adaptee.insertElementAt(element, index); // implicit handles the case index == adaptee.size();
    }

    /**
     * Inserts all of the elements in the specified HCollection into this HList at the specified position. Shifts the element currently at that position (if any) and any subsequent elements to the right (increases their indices). The new elements will appear in this HList in the order that they are returned by the specified HCollection's iterator. The behavior of this operation is unspecified if the specified HCollection is modified while the operation is in progress. 
     * 
     * @param index index at which to insert the first element from the specified HCollection.
     * @param c elements to be inserted into this HList.
     * @return true if this HList changed as a result of the call.
     * @throws NullPointerException if the specified HCollection is null.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public boolean addAll(int index, HCollection c) {
        if(c == null) throw new NullPointerException();
        if(index < 0 || index > adaptee.size()){
            throw new IndexOutOfBoundsException();
        }      
        HIterator scanner = c.iterator();
        boolean isChanged = false;
        while(scanner.hasNext()){
            adaptee.insertElementAt(scanner.next(), index);
            index += 1;
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Removes the element at the specified position in this HList. Shifts any subsequent elements to the left (subtracts one from their indices). Returns the element that was removed from the HList.
     * 
     * @param index the index of the element to be removed.
     * @return the element previously at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object remove(int index) {
        if(index < 0 || index >= adaptee.size()){
            throw new IndexOutOfBoundsException();
        }
        Object toRemove = adaptee.elementAt(index);
        adaptee.removeElementAt(index);
        return toRemove;
    }

    /**
     * Returns the index in this HList of the first occurrence of the specified element, or -1 if this HList does not contain this element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     * 
     * @param o element to search for.
     * @return the index of the first occurrence of the specified element, or -1 if this HList does not contain the element.
     */
    public int indexOf(Object o) {
        // explicit scan: null is compared with ==, any other element with equals(), as required by the HList contract
        for (int i = 0; i < adaptee.size(); i++) {
            Object current = adaptee.elementAt(i);
            if (o == null ? current == null : o.equals(current)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index in this HList of the last occurrence of the specified element, or -1 if this HList does not contain this element. More formally, returns the highest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     * 
     * @param o element to search for.
     * @return the index of the last occurrence of the specified element, or -1 if this HList does not contain the element.
     */
    public int lastIndexOf(Object o) {
        // explicit backward scan: null is compared with ==, any other element with equals(), as required by the HList contract
        for (int i = adaptee.size() - 1; i >= 0; i--) {
            Object current = adaptee.elementAt(i);
            if (o == null ? current == null : o.equals(current)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns an HListIterator of the elements in this HList (in proper sequence).
     * 
     * @return an HListIterator of the elements in this HList (in proper sequence).
     */
    public HListIterator listIterator() { 
        return new StackListIterator();
    }
    
    /**
     * Returns an HListIterator of the elements in this HList (in proper sequence), starting at the specified position in this HList. The specified index indicates the first element that would be returned by an initial call to the next method. An initial call to the previous method would return the element with the specified index minus one.
     * 
     * @param index index of first element to be returned from the HListIterator.
     * @return an HListIterator of the elements in this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public HListIterator listIterator(int index) {
        if (index > adaptee.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return new StackListIterator(index); 
    }

    /**
     * Returns a view of the portion of this HList between the specified fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned HList is empty.) The returned HList is backed by this HList, so changes in the returned HList (including structural ones such as add, remove and clear) are reflected in this HList, and non-structural changes in this HList are reflected in the returned HList. The returned HList supports all of the optional list operations supported by this HList. If this HList is structurally modified in any way other than via the returned HList, the behavior of the returned HList is undefined.
     * 
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex high endpoint (exclusive) of the subList.
     * @return a view of the specified range within this HList.
     * @throws IndexOutOfBoundsException for an illegal endpoint index value (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex).
     */
    public HList subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > adaptee.size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new StackSubList(fromIndex, toIndex);
    }

    // ------------------------------------------
    // NESTED CLASSES
    // ------------------------------------------

    // Note: Because these are private classes, Javadoc by default ignores the generation 
    // of the documentation for their methods. It was a personal decision to still insert
    // a compact description for each method to try to make the code more readable for a reviewer.

    /**
     * HIterator for StackAdapter's HCollection.
     * Scans the stack from the bottom (index 0) to the top and allows the removal of the last returned element.
     * It works directly on the adaptee, so it always reflects the current content of the stack.
     */
    private class StackIterator implements HIterator {
        protected int pointer = 0; // index of the element that the next call to next() will return
        protected int previous = -1; // index of the last element returned by next()/previous(); -1 if none, or if it was already removed
        
        /**
         * Returns true if the iteration has more elements.
         * 
         * @return true if the HIterator has more elements.
         */
        public boolean hasNext() {
            return pointer < adaptee.size(); 
        }  
        
        /**
         * Returns the next element in the iteration.
         * 
         * @return the next element in the iteration.
         * @throws NoSuchElementException iteration has no more elements.
         */
        public Object next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Object element = adaptee.elementAt(pointer);
            previous = pointer; // remember the index of the returned element (used by remove() and set())
            pointer++; // move the cursor forward
            return element;
        }

        /**
         * Removes from the underlying collection the last element returned by the HIterator.
         *
         * @throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method.
         */
        public void remove() {
            if (previous == -1) {
                throw new IllegalStateException();
            }
            adaptee.removeElementAt(previous);
            // the following elements have shifted one position to the left: the cursor moves to the index of the
            // removed element (after next() this is one step back, after previous() it is unchanged)
            pointer = previous;
            previous = -1; // reset, so that a second remove() without a new next()/previous() throws IllegalStateException
        }
    }

    /**
     * HListIterator for StackAdapter's HList.
     * Extends StackIterator (which provides hasNext(), next() and remove()) to implement bidirectional traversal
     * and modification operations. The cursor lies between two elements: pointer is the index of the element
     * returned by next(), pointer - 1 the index of the element returned by previous().
     */
    private class StackListIterator extends StackIterator implements HListIterator {
        
        /**
         * Constructs an HListIterator positioned at the beginning of the HList.
         */
        public StackListIterator() {
            // default constructor
            super();
        }

        /**
         * Constructs an HListIterator positioned at the specified index. The index has already been
         * validated by StackAdapter.listIterator(int).
         *
         * @param index index of the first element that would be returned by next().
         */
        public StackListIterator(int index) {
            pointer = index;
        }

        /**
         * Returns true if this HListIterator has more elements when traversing the HList in the reverse direction.
         * 
         * @return true if the HListIterator has more elements when traversing the HList in the reverse direction.
         */
        public boolean hasPrevious() {
            return pointer > 0;
        }
    
        /**
         * Returns the previous element in the HList.
         * 
         * @return the previous element in the HList.
         * @throws NoSuchElementException if the iteration has no previous element.
         */
        public Object previous() { 
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            pointer--; // move the cursor backward
            previous = pointer; // remember the index of the returned element (used by remove() and set())
            return adaptee.elementAt(pointer);
        }
    
        /**
         * Returns the index of the element that would be returned by a subsequent call to next.
         * 
         * @return the index of the element that would be returned by a subsequent call to next.
         */
        public int nextIndex() { 
            return pointer; 
        }
    
        /**
         * Returns the index of the element that would be returned by a subsequent call to previous.
         * 
         * @return the index of the element that would be returned by a subsequent call to previous.
         */
        public int previousIndex() {
            return pointer - 1;
        }
    
        /**
         * Replaces the last element returned by next or previous with the specified element.
         *
         * @param o the element with which to replace the last element returned by next or previous.
         * @throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous.
         */
        public void set(Object o) {
            if (previous == -1) {
                throw new IllegalStateException();
            }
            adaptee.setElementAt(o, previous);
        }

        /**
         * Inserts the specified element into the HList, immediately before the cursor: a subsequent call to next
         * is unaffected, while a subsequent call to previous returns the new element. After this call, set and
         * remove throw IllegalStateException until next or previous is called again.
         *
         * @param o the element to insert.
         */
        public void add(Object o) {
            adaptee.insertElementAt(o, pointer); // places the new element at the cursor position
            pointer++; // the cursor moves after the new element, so next() is unaffected
            previous = -1; // no "last returned element" anymore: set() and remove() are not allowed
        }
    }

    /**
     * View Pattern (dynamic view) implementation for subList. It stores no elements: every operation is
     * delegated to the parent StackAdapter by mapping the subList index i to the parent index offset + i,
     * so every modification made through the subList is reflected on the parent stack.
     * The size of the view is kept in subListSize and updated only by the subList's own operations:
     * structural changes made directly on the parent stack (not through this view) leave the view in an
     * undefined state, as stated by the HList contract.
    */
    private class StackSubList implements HList {
        private int offset; // index in the parent stack of the first element of the subList
        private int subListSize; // number of elements in the subList

        // Note: StackAdapter.this is used to call the methods of the enclosing class when the subList has a
        // method with the same name. Example: StackAdapter.this.remove(offset) calls StackAdapter's remove(int).
        
        /**
         * Constructs a subList view.
         * 
         * @param fromIndex low end point (inclusive) of the subList.
         * @param toIndex high end point (exclusive) of the subList.
         */
        public StackSubList(int fromIndex, int toIndex) {
            offset = fromIndex;
            subListSize = toIndex - fromIndex;
        }

        /**
         * Helper method that checks that index is a valid element index of the subList (0 &lt;= index &lt; size()).
         *
         * @param index index of the element to check.
         * @throws IndexOutOfBoundsException if the index is out of range.
         */
        private void checkRange(int index) {
            if (index < 0 || index >= subListSize) {
                throw new IndexOutOfBoundsException();
            }
        }

        /**
         * Returns the number of elements in this HList.
         * 
         * @return the number of elements in this HList.
         */
        public int size() { 
            return subListSize;
        }
        
        /**
         * Returns true if this HList contains no elements.
         * 
         * @return true if this HList contains no elements.
         */
        public boolean isEmpty() { 
            return subListSize == 0; 
        }
        
        /**
         * Removes all of the elements of this HList from the parent stack. Elements outside the range are not affected.
         */
        public void clear() {
            for (int i = 0; i < subListSize; i++) {
                StackAdapter.this.remove(offset); // after each removal the next element of the range shifts to offset
            }
            subListSize = 0;
        }

        /**
         * Returns the element at the specified position in this HList.
         * 
         * @param index index of the element to return.
         * @return the element at the specified position in this HList.
         * @throws IndexOutOfBoundsException if the index is out of range.
         */
        public Object get(int index) {
            checkRange(index);
            // offset + index identifies the element of the parent stack that matches the subList element.
            // Example: for subList(1, 4) offset == 1, so subList.get(0) asks for the first element
            // of the subList, which is the element at index 1 + 0 = 1 of the stack.
            return StackAdapter.this.get(offset + index);
        }

        /**
         * Replaces the element at the specified position in this HList with the specified element.
         * 
         * @param index index of the element to replace.
         * @param element element to be stored at the specified position.
         * @return the element previously at the specified position.
         * @throws IndexOutOfBoundsException if the index is out of range.
         */
        public Object set(int index, Object element) {
            checkRange(index);
            return StackAdapter.this.set(offset + index, element);
        }

        /**
         * Inserts the specified element at the specified position in this HList (and in the parent stack).
         * Shifts the following elements, of both the subList and the parent stack, to the right.
         *
         * @param index index at which the specified element is to be inserted.
         * @param element element to be inserted.
         * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
         */
        public void add(int index, Object element) {
            if (index < 0 || index > subListSize) {
                throw new IndexOutOfBoundsException();
            }
            StackAdapter.this.add(offset + index, element);
            subListSize++; // increases the size
        }

        /**
         * Removes the element at the specified position in this HList.
         * 
         * @param index the index of the element to be removed.
         * @return the element previously at the specified position.
         * @throws IndexOutOfBoundsException if the index is out of range.
         */
        public Object remove(int index) {
            checkRange(index);
            Object removed = StackAdapter.this.remove(offset + index);
            subListSize--; 
            return removed;
        }

        /**
         * Returns the index in this HList of the first occurrence of the specified element.
         * 
         * @param o element to search for.
         * @return the index of the first occurrence of the specified element, or -1 if this HList does not contain the element.
         */
        public int indexOf(Object o) {
            for (int i = 0; i < subListSize; i++) {
                Object current = get(i);
                if (o == null ? current == null : o.equals(current)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Returns the index in this HList of the last occurrence of the specified element.
         * 
         * @param o element to search for.
         * @return the index of the last occurrence of the specified element, or -1 if this HList does not contain the element.
         */
        public int lastIndexOf(Object o) {
            for (int i = subListSize - 1; i >= 0; i--) {
                Object current = get(i);
                if (o == null ? current == null : o.equals(current)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Returns true if this HList contains the specified element.
         * 
         * @param o element whose presence in this HList is to be tested.
         * @return true if this HList contains the specified element.
         */
        public boolean contains(Object o) {
            return indexOf(o) != -1;
        }

        /**
         * Returns an array containing all of the elements in this HList in proper sequence.
         * 
         * @return an array containing all of the elements in this HList.
         */
        public Object[] toArray() {
            Object[] array = new Object[subListSize];
            for (int i = 0; i < subListSize; i++) {
                array[i] = get(i);
            }
            return array;
        }

        /**
         * Returns an array containing all of the elements in this HList. If they fit in the specified array, they are
         * stored and returned therein (and the element right after the last one is set to null if there is room);
         * otherwise a new Object[] with the size of this HList is returned (CLDC 1.1 has no java.lang.reflect.Array).
         *
         * @param a the array into which the elements of this HList are to be stored, if it is big enough.
         * @return an array containing the elements of this HList.
         * @throws NullPointerException if the specified array is null.
         * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this HList.
         */
        public Object[] toArray(Object[] a) {
            if (a == null) throw new NullPointerException();
            if (a.length < subListSize) {
                a = new Object[subListSize];
            }
            for (int i = 0; i < subListSize; i++) {
                a[i] = get(i);
            }
            if (a.length > subListSize) {
                a[subListSize] = null;
            }
            return a;
        }

        /**
         * Appends the specified element at the end of this HList, i.e. at index offset + size() of the parent stack.
         *
         * @param o element whose presence in this HList is to be ensured.
         * @return true, because this HList always changes as a result of the call.
         */
        public boolean add(Object o) { 
            add(subListSize, o); 
            return true;
        }

        /**
         * Removes a single instance of the specified element from this HList, if it is present.
         * 
         * @param o element to be removed from this HList, if present.
         * @return true if this HList changed as a result of the call.
         */
        public boolean remove(Object o) {
            int index = indexOf(o);
            if (index != -1) {
                remove(index);
                return true;
            }
            return false;
        }

        /**
         * Returns true if this HList contains all of the elements in the specified HCollection.
         * 
         * @param c HCollection to be checked for containment in this HList.
         * @return true if this HList contains all of the elements in the specified HCollection.
         * @throws NullPointerException if the specified HCollection is null.
         */
        public boolean containsAll(HCollection c) {
            if (c == null) throw new NullPointerException();
            HIterator scanner = c.iterator();
            while (scanner.hasNext()) {
                if (!contains(scanner.next())) return false;
            }
            return true;
        }

        /**
         * Adds all of the elements in the specified HCollection to this HList.
         * 
         * @param c elements to be inserted into this HList.
         * @return true if this HList changed as a result of the call.
         * @throws NullPointerException if the specified HCollection is null.
         */
        public boolean addAll(HCollection c) {
            return addAll(subListSize, c);
        }

        /**
         * Inserts all of the elements in the specified HCollection into this HList at the specified position,
         * in the order in which they are returned by the HCollection's iterator.
         *
         * @param index index at which to insert the first element from the specified HCollection.
         * @param c elements to be inserted into this HList.
         * @return true if this HList changed as a result of the call.
         * @throws NullPointerException if the specified HCollection is null.
         * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
         */
        public boolean addAll(int index, HCollection c) {
            if (c == null) throw new NullPointerException();
            if (index < 0 || index > subListSize) throw new IndexOutOfBoundsException();
            HIterator scanner = c.iterator();
            boolean isChanged = false;
            while (scanner.hasNext()) {
                add(index, scanner.next());
                index++;
                isChanged = true;
            }
            return isChanged;
        }

        /**
         * Removes all this HList's elements that are also contained in the specified HCollection.
         * 
         * @param c elements to be removed from this HList.
         * @return true if this HList changed as a result of the call.
         * @throws NullPointerException if the specified HCollection is null.
         */
        public boolean removeAll(HCollection c) {
            if (c == null) throw new NullPointerException();
            boolean isChanged = false;
            HIterator scanner = this.iterator();
            while (scanner.hasNext()) {
                if (c.contains(scanner.next())) {
                    scanner.remove();
                    isChanged = true;
                }
            }
            return isChanged;
        }

        /**
         * Retains only the elements in this HList that are contained in the specified HCollection.
         * 
         * @param c elements to be retained in this HList.
         * @return true if this HList changed as a result of the call.
         * @throws NullPointerException if the specified HCollection is null.
         */
        public boolean retainAll(HCollection c) {
            if (c == null) throw new NullPointerException();
            boolean isChanged = false;
            HIterator scanner = this.iterator();
            while (scanner.hasNext()) {
                if (!c.contains(scanner.next())) {
                    scanner.remove();
                    isChanged = true;
                }
            }
            return isChanged;
        }

        /**
         * Compares the specified object with this HList for equality.
         * 
         * @param o Object to be compared for equality with this HList.
         * @return true if the specified object is equal to this HList.
         */
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null || !(o instanceof HList)) return false;
            HList other = (HList) o;
            if (this.size() != other.size()) return false;
            HIterator thisIterator = this.iterator();
            HIterator otherIterator = other.iterator();
            while (thisIterator.hasNext() && otherIterator.hasNext()) {
                Object first = thisIterator.next();
                Object second = otherIterator.next();
                if (!(first == null ? second == null : first.equals(second))) return false;
            }
            return true;
        }

        /**
         * Returns the hash code value for this HList.
         * 
         * @return the hash code value for this HList.
         */
        public int hashCode() {
            int hashCode = 1;
            HIterator scanner = this.iterator();
            while (scanner.hasNext()) {
                Object object = scanner.next();
                hashCode = 31 * hashCode + (object == null ? 0 : object.hashCode());
            }
            return hashCode;
        }

        /**
         * Returns a view of the portion of this HList between the specified fromIndex, inclusive, and toIndex, exclusive.
         * The indices are relative to this subList; the new view is backed directly by the parent stack.
         *
         * @param fromIndex low endpoint (inclusive) of the subList.
         * @param toIndex high endpoint (exclusive) of the subList.
         * @return a view of the specified range within this HList.
         * @throws IndexOutOfBoundsException for an illegal endpoint index value (fromIndex &lt; 0 || toIndex &gt; size() || fromIndex &gt; toIndex).
         */
        public HList subList(int fromIndex, int toIndex) {
            if (fromIndex < 0 || toIndex > subListSize || fromIndex > toIndex) {
                throw new IndexOutOfBoundsException();
            }
            return new StackSubList(this.offset + fromIndex, this.offset + toIndex);
        }

        /**
         * Returns an HIterator over the elements in this HList.
         * 
         * @return an HIterator over the elements in this HList.
         */
        public HIterator iterator() {
            return listIterator();
        }

        /**
         * Returns an HListIterator of the elements in this HList (in proper sequence).
         * 
         * @return an HListIterator of the elements in this HList (in proper sequence).
         */
        public HListIterator listIterator() {
            return listIterator(0);
        }

        /**
         * Returns an HListIterator of the elements in this HList (in proper sequence), starting at the specified position in this HList.
         * 
         * @param index index of first element to be returned from the HListIterator.
         * @return an HListIterator of the elements in this HList.
         * @throws IndexOutOfBoundsException if the index is out of range.
         */
        public HListIterator listIterator(int index) {
            if (index < 0 || index > subListSize) {
                throw new IndexOutOfBoundsException();
            }
            return new StackSubListIterator(index);
        }

        /**
         * Explicit internal HListIterator for StackSubList. It moves only inside the
         * boundary of the subList without interacting with any outside element. Uses StackSubList's
         * methods for traversing and modification, so its indices are relative to the subList.
        */
        private class StackSubListIterator implements HListIterator {
            private int pointer; // subList index of the element returned by next(); goes only from 0 to subListSize
            private int previous; // subList index of the last returned element; -1 if none, or after remove()/add()

            /**
             * Constructs a subList HListIterator positioned at the specified index.
             * 
             * @param index position of the HListIterator.
             */
            public StackSubListIterator(int index) {
                pointer = index;
                previous = -1;
            }

            /**
             * Returns true if the iteration has more elements.
             * 
             * @return true if the HIterator has more elements.
             */
            public boolean hasNext() {
                return pointer < subListSize;
            }

            /**
             * Returns the next element in the iteration.
             * 
             * @return the next element in the iteration.
             * @throws NoSuchElementException iteration has no more elements.
             */
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                previous = pointer;
                return get(pointer++);
            }

            /**
             * Removes from the subList (and so from the parent stack) the last element returned by next or previous.
             *
             * @throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous.
             */
            public void remove() {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                StackSubList.this.remove(previous);
                pointer = previous;
                previous = -1;
            }

            /**
             * Returns true if this HListIterator has more elements when traversing the HList in the reverse direction.
             * 
             * @return true if the HListIterator has more elements when traversing the HList in the reverse direction.
             */
            public boolean hasPrevious() {
                return pointer > 0;
            }

            /**
             * Returns the previous element in the HList.
             * 
             * @return the previous element in the HList.
             * @throws NoSuchElementException if the iteration has no previous element.
             */
            public Object previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                pointer--;
                previous = pointer;
                return get(pointer);
            }

            /**
             * Returns the index of the element that would be returned by a subsequent call to next.
             * 
             * @return the index of the element that would be returned by a subsequent call to next.
             */
            public int nextIndex() {
                return pointer;
            }

            /**
             * Returns the index of the element that would be returned by a subsequent call to previous.
             * 
             * @return the index of the element that would be returned by a subsequent call to previous.
             */
            public int previousIndex() {
                return pointer - 1;
            }

            /**
             * Replaces the last element returned by next or previous with the specified element.
             * 
             * @param o the element with which to replace the last element returned by next or previous.
             * @throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous.
             */
            public void set(Object o) {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                StackSubList.this.set(previous, o);
            }

            /**
             * Inserts the specified element into the subList (and the parent stack) immediately before the cursor:
             * a subsequent call to next is unaffected, while a subsequent call to previous returns the new element.
             *
             * @param o the element to insert.
             */
            public void add(Object o) {
                StackSubList.this.add(pointer++, o);
                previous = -1;
            }
        }
    }
}