package myAdapter;

/**
 Represents a group of objects, known as its elements. Typically used to pass HCollections around and manipulate them 
 regardless of the details of their implementation
*/

public interface HCollection
{
    /**
     * Returns the number of elements in this HCollection. If this HCollection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * 
     * @return the number of elements in this HCollection
     */
    public int size();

    /**
     * Returns true if this HCollection contains no elements.
     * 
     * @return true if this HCollection contains no elements
     */
    public boolean isEmpty();

    /**
     * Returns true if this HCollection contains the specified element. More formally, returns true if and only if this HCollection contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * 
     * @param o element whose presence in this HCollection is to be tested.
     * @return true if this HCollection contains the specified element
     * @throws ClassCastException if the type of the specified element is incompatible with this HCollection (optional).
     * @throws NullPointerException if the specified element is null and this HCollection does not support null elements (optional).
     */
    public boolean contains(Object o);

    /**
     * Returns an HIterator over the elements in this HCollection. There are no guarantees concerning the order in which the elements are returned (unless this HCollection is an instance of some class that provides a guarantee).
     * 
     * @return an HIterator over the elements in this HCollection
     */
    public HIterator iterator();

    /**
     * Returns an array containing all of the elements in this HCollection. If the HCollection makes any guarantees as to what order its elements are returned by its HIterator, this method must return the elements in the same order.
     * The returned array will be "safe" in that no references to it are maintained by this HCollection. (In other words, this method must allocate a new array even if this HCollection is backed by an array). The caller is thus free to modify the returned array.
     * 
     * This method acts as bridge between array-based and HCollection-based APIs.
     * 
     * @return an array containing all of the elements in this HCollection
     */
    public Object[] toArray();

    /**
     * Returns an array containing all of the elements in this HCollection; the runtime type of the returned array is that of the specified array. If the HCollection fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this HCollection.
     * If this HCollection fits in the specified array with room to spare (i.e., the array has more elements than this HCollection), the element in the array immediately following the end of the HCollection is set to null. This is useful in determining the length of this HCollection only if the caller knows that this HCollection does not contain any null elements.)
     * 
     * If this HCollection makes any guarantees as to what order its elements are returned by its HIterator, this method must return the elements in the same order.
     * 
     * Like the toArray method, this method acts as bridge between array-based and HCollection-based APIs. Further, this method allows precise control over the runtime type of the output array, and may, under certain circumstances, be used to save allocation costs
     * 
     * Suppose l is a HList known to contain only strings. The following code can be used to dump the HList into a newly allocated array of String:
     * 
     *   String[] x = (String[]) v.toArray(new String[0]);
     * 
     * Note that toArray(new Object[0]) is identical in function to toArray().
     * 
     * @param a the array into which the elements of this HCollection are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return an array containing the elements of this HCollection
     * @throws ArrayStoreException the runtime type of the specified array is not a supertype of the runtime type of every element in this HCollection.
     * @throws NullPointerException if the specified array is null.
     */
    public Object[] toArray(Object[] a);

    /**
     * Ensures that this HCollection contains the specified element (optional operation). Returns true if this HCollection changed as a result of the call. (Returns false if this HCollection does not permit duplicates and already contains the specified element.)
     * HCollections that support this operation may place limitations on what elements may be added to this HCollection. In particular, some HCollections will refuse to add null elements, and others will impose restrictions on the type of elements that may be added. HCollection classes should clearly specify in their documentation any restrictions on what elements may be added.
     * 
     * If a HCollection refuses to add a particular element for any reason other than that it already contains the element, it must throw an exception (rather than returning false). This preserves the invariant that a HCollection always contains the specified element after this call returns.
     * 
     * @param o element whose presence in this HCollection is to be ensured.
     * @return true if this HCollection changed as a result of the call
     * @throws UnsupportedOperationException add is not supported by this HCollection.
     * @throws ClassCastException class of the specified element prevents it from being added to this HCollection.
     * @throws NullPointerException if the specified element is null and this HCollection does not support null elements.
     * @throws IllegalArgumentException some aspect of this element prevents it from being added to this HCollection.
     */
    public boolean add(Object o);

    /**
     * Removes a single instance of the specified element from this HCollection, if it is present (optional operation). More formally, removes an element e such that (o==null ? e==null : o.equals(e)), if this HCollection contains one or more such elements. Returns true if this HCollection contained the specified element (or equivalently, if this HCollection changed as a result of the call).
     * 
     * @param o element to be removed from this HCollection, if present.
     * @return true if this HCollection changed as a result of the call
     * @throws ClassCastException if the type of the specified element is incompatible with this HCollection (optional).
     * @throws NullPointerException if the specified element is null and this HCollection does not support null elements (optional).
     * @throws UnsupportedOperationException remove is not supported by this HCollection.
     */
    public boolean remove(Object o);

    /**
     * Returns true if this HCollection contains all of the elements in the specified HCollection.
     * 
     * @param c HCollection to be checked for containment in this HCollection.
     * @return true if this HCollection contains all of the elements in the specified HCollection
     * @throws ClassCastException if the types of one or more elements in the specified HCollection are incompatible with this HCollection (optional).
     * @throws NullPointerException if the specified HCollection contains one or more null elements and this HCollection does not support null elements (optional).
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean containsAll(HCollection c);

    /**
     * Adds all of the elements in the specified HCollection to this HCollection (optional operation). The behavior of this operation is undefined if the specified HCollection is modified while the operation is in progress. (This implies that the behavior of this call is undefined if the specified HCollection is this HCollection, and this HCollection is nonempty.)
     * 
     * @param c elements to be inserted into this HCollection.
     * @return true if this HCollection changed as a result of the call
     * @throws UnsupportedOperationException if this HCollection does not support the addAll method.
     * @throws ClassCastException if the class of an element of the specified HCollection prevents it from being added to this HCollection.
     * @throws NullPointerException if the specified HCollection contains one or more null elements and this HCollection does not support null elements, or if the specified HCollection is null.
     * @throws IllegalArgumentException some aspect of an element of the specified HCollection prevents it from being added to this HCollection.
     */
    public boolean addAll(HCollection c);

    /**
     * Removes all this HCollection's elements that are also contained in the specified HCollection (optional operation). After this call returns, this HCollection will contain no elements in common with the specified HCollection.
     * 
     * @param c elements to be removed from this HCollection.
     * @return true if this HCollection changed as a result of the call
     * @throws UnsupportedOperationException if the removeAll method is not supported by this HCollection.
     * @throws ClassCastException if the types of one or more elements in this HCollection are incompatible with the specified HCollection (optional).
     * @throws NullPointerException if this HCollection contains one or more null elements and the specified HCollection does not support null elements (optional).
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean removeAll(HCollection c);

    /**
     * Retains only the elements in this HCollection that are contained in the specified HCollection (optional operation). In other words, removes from this HCollection all of its elements that are not contained in the specified HCollection.
     * 
     * @param c elements to be retained in this HCollection.
     * @return true if this HCollection changed as a result of the call
     * @throws UnsupportedOperationException if the retainAll method is not supported by this HCollection.
     * @throws ClassCastException if the types of one or more elements in this HCollection are incompatible with the specified HCollection (optional).
     * @throws NullPointerException if this HCollection contains one or more null elements and the specified HCollection does not support null elements (optional).
     * @throws NullPointerException if the specified HCollection is null.
     */
    public boolean retainAll(HCollection c);

    /**
     * Removes all of the elements from this HCollection (optional operation). This HCollection will be empty after this method returns unless it throws an exception.
     * 
     * @throws UnsupportedOperationException if the clear method is not supported by this HCollection.
     */
    public void clear();

    /**
     * Compares the specified object with this HCollection for equality.
     * While the HCollection interface adds no stipulations to the general contract for the Object.equals, programmers who implement the HCollection interface "directly" (in other words, create a class that is a HCollection but is not a Set or a HList) must exercise care if they choose to override the Object.equals. It is not necessary to do so, and the simplest course of action is to rely on Object's implementation, but the implementer may wish to implement a "value comparison" in place of the default "reference comparison." (The HList and Set interfaces mandate such value comparisons.)
     * 
     * The general contract for the Object.equals method states that equals must be symmetric (in other words, a.equals(b) if and only if b.equals(a)). The contracts for HList.equals and Set.equals state that HLists are only equal to other HLists, and sets to other sets. Thus, a custom equals method for a HCollection class that implements neither the HList nor Set interface must return false when this HCollection is compared to any HList or set. (By the same logic, it is not possible to write a class that correctly implements both the Set and HList interfaces.)
     * 
     * Overrides:
     * equals in class Object
     * 
     * @param o Object to be compared for equality with this HCollection.
     * @return true if the specified object is equal to this HCollection
     */
    public boolean equals(Object o);

    /**
     * Returns the hash code value for this HCollection. While the HCollection interface adds no stipulations to the general contract for the Object.hashCode method, programmers should take note that any class that overrides the Object.equals method must also override the Object.hashCode method in order to satisfy the general contract for the Object.hashCodemethod. In particular, c1.equals(c2) implies that c1.hashCode()==c2.hashCode().
     * 
     * Overrides:
     * hashCode in class Object
     * 
     * @return the hash code value for this HCollection
     */
    public int hashCode();
}