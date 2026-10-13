package myAdapter;

/**
 * HList represents an ordered collection (also known as a sequence).
 * The user of this interface has precise control over where in the list each element is inserted.
 * The user can access elements by their integer index (position in the list) and search for elements in the list.
 * Unlike sets, lists typically allow duplicate elements. More formally, lists typically allow pairs of elements 
 * e1 and e2 such that e1.equals(e2), and they typically allow multiple null elements if they allow null elements at all.
 */

public interface HList extends HCollection
{
    /**
     * Returns the element at the specified position in this HList.
     * 
     * @param index index of element to return.
     * @return the element at the specified position in this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object get(int index);


    /**
     * Replaces the element at the specified position in this HList with the specified element (optional operation).
     * 
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws UnsupportedOperationException if the set method is not supported by this HList.
     * @throws ClassCastException if the class of the specified element prevents it from being added to this HList.
     * @throws NullPointerException if the specified element is null and this HList does not support null elements.
     * @throws IllegalArgumentException if some aspect of the specified element prevents it from being added to this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object set(int index, Object element);


    /**
     * Inserts the specified element at the specified position in this HList (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * 
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * @throws UnsupportedOperationException if the add method is not supported by this HList.
     * @throws ClassCastException if the class of the specified element prevents it from being added to this HList.
     * @throws NullPointerException if the specified element is null and this HList does not support null elements.
     * @throws IllegalArgumentException if some aspect of the specified element prevents it from being added to this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public void add(int index, Object element);


    /**
     * Inserts all of the elements in the specified collection into this list at the specified position (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (increases their indices). The new elements will appear in this list in the order that they are returned by the specified collection's iterator. The behavior of this operation is unspecified if the specified collection is modified while the operation is in progress. (Note that this will occur if the specified collection is this list, and it's nonempty.)
     * 
     * @param index index at which to insert first element from the specified collection
     * @param c elements to be inserted into this list.
     * @return true if this list changed as a result of the call.
     * @throws UnsupportedOperationException - if the addAll method is not supported by this list.
       @throws ClassCastException - if the class of one of elements of the specified collection prevents it from being added to this list.
       @throws NullPointerException - if the specified collection contains one or more null elements and this list does not support null elements, or if the specified collection is null.
       @throws IllegalArgumentException - if some aspect of one of elements of the specified collection prevents it from being added to this list.
       @throws IndexOutOfBoundsException - if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public boolean addAll(int index, HCollection c);

    
    /**
     * Removes the element at the specified position in this HList (optional operation). Shifts any subsequent elements to the left (subtracts one from their indices). Returns the element that was removed from the HList.
     * 
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     * @throws UnsupportedOperationException if the remove method is not supported by this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public Object remove(int index);


    /**
     * Returns the index in this HList of the first occurrence of the specified element, or -1 if this HList does not contain this element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
     * 
     * @param o element to search for.
     * @return the index in this HList of the first occurrence of the specified element, or -1 if this HList does not contain this element.
     * @throws ClassCastException if the type of the specified element is incompatible with this HList (optional).
     * @throws NullPointerException if the specified element is null and this HList does not support null elements (optional).
     */
    public int indexOf(Object o);

    /**
     * Returns the index in this HList of the last occurrence of the specified element, or -1 if this HList does not contain this element.
     * 
     * @param o element to search for.
     * @return the index in this HList of the last occurrence of the specified element, or -1 if this HList does not contain this element.
     */
    public int lastIndexOf(Object o);


    /**
     * Returns a list iterator of the elements in this HList (in proper sequence).
     * 
     * @return a list iterator of the elements in this HList (in proper sequence).
     */
    public HListIterator listIterator();


    /**
     * Returns a list iterator of the elements in this HList (in proper sequence), starting at the specified position in this HList. The specified index indicates the first element that would be returned by an initial call to the next method. An initial call to the previous method would return the element with the specified index minus one.
     * 
     * @param index index of first element to be returned from the list iterator (by a call to the next method).
     * @return a list iterator of the elements in this HList (in proper sequence), starting at the specified position in this HList.
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; size()).
     */
    public HListIterator listIterator(int index);

    
    /**
     * Returns a view of the portion of this HList between the specified fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned HList is empty.) The returned HList is backed by this HList, so non-structural changes in the returned HList are reflected in this HList, and vice-versa. The returned HList supports all of the optional list operations supported by this HList.
     * This method eliminates the need for explicit range operations (of the sort that commonly exist for arrays). Any operation that expects a HList can be used as a range operation by passing a subList view instead of a whole HList. For example, the following idiom removes a range of elements from a HList:
     * 
     *     list.subList(from, to).clear();
     * 
     * Similar idioms may be constructed for indexOf and lastIndexOf, and all of the algorithms in the Collections class can be applied to a subList.
     * The semantics of the HList returned by this method become undefined if the backing HList (i.e., this HList) is structurally modified in any way other than via the returned HList. (Structural modifications are those that change the size of this HList, or otherwise perturb it in such a fashion that iterations in progress may yield incorrect results.)
     * 
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex high endpoint (exclusive) of the subList.
     * @return a view of the specified range within this HList.
     * @throws IndexOutOfBoundsException for an illegal endpoint index value (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex).
     */
    public HList subList(int fromIndex, int toIndex); 
}