package myTest;

import static org.junit.Assert.*; // for using the static methods and avoid redundant Assert.method().

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;


/** 
 * <strong>Summary:</strong> This Test Suite verifies the behavior of the HIterator interface implemented 
 * by the StackAdapter's internal iterator. The suite is divided into sections using comments to group 
 * tests by the method being tested. Furthermore, it groups tests around sequential forward traversal 
 * and safe element removal, always ensuring full compatibility with the J2SE 1.4.2 collections framework.
 * 
 * <ul>
 *   <li><strong>Test Case Design:</strong> The tests have been developed following the Test Driven
 *   Development methodology, meaning they were implemented before their respective methods.
 *   The goal is to test sequential forward iteration, verifying the dynamic link with the backing stack
 *   and exception handling for illegal states (e.g., calling remove() without calling next(), or iterating past the end of the collection).</li>  
 * </ul>
 * 
 * <p><strong>Conventions used in the documentation of the tests:</strong></p>
 * <ul>
 *   <li>Every test starts from the same state: setUp() creates a new, empty StackAdapter ({@code stackTest}) before each test,
 *   so the Pre-Condition of every test is an empty stack and all the elements used are inserted by the test itself
 *   (as described in its Test Description).</li>
 *   <li>The content of a stack is written from the bottom (index 0) to the top, e.g. [A, B] means that "A" was pushed first
 *   and "B" is on top. The iterators traverse the stack in the same order.</li>
 * </ul>
*/
public class StackIteratorTest {
    private StackAdapter stackTest;

    /**
     * Creates a new, empty StackAdapter before each test, so that every test starts from the same known state.
    */
    @Before 
    public void setUp() {
        stackTest = new StackAdapter(); 
    }

    /**
     * Releases the reference to the stack after each test.
    */
    @After
    public void reset() {
        stackTest = null; 
    }

    // TEST FOR HITERATOR (StackIterator) INTERFACE METHODS: 

    // test for HIterator's hasNext() method

    /**
     * <strong>Summary:</strong> Verifies that hasNext() returns false on an HIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: iteration over a collection with zero elements.</li>
     *   <li><strong>Test Description:</strong> Obtains an HIterator with {@code stackTest.iterator()} without inserting any element and calls {@code hasNext()} once.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty; the HIterator has not returned any element.</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testHasNextOnEmptyStack() {
        HIterator iterator = stackTest.iterator();
        assertFalse("Error: The method shouldn't return true on an empty stack.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that hasNext() reports correctly whether elements remain during a complete forward scan.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard behavior of hasNext() checked before every step of a full traversal of a two-element stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" (stack from bottom to top: [A, B]) and creates an HIterator. Asserts that {@code hasNext()} is true, calls {@code next()} (returns "A"), asserts that {@code hasNext()} is still true, calls {@code next()} again (returns "B") and finally asserts that {@code hasNext()} is false.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the HIterator is positioned after the last element.</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns true, true and then false.</li>
     * </ul>
     */
    @Test
    public void testHasNextStandardBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HIterator iterator = stackTest.iterator();
        assertTrue("Error: Must return true so recognize the first element.", iterator.hasNext());
        iterator.next(); // make the pointer advance
        assertTrue("Error: Must return true so recognize the second element.", iterator.hasNext());
        iterator.next();
        assertFalse("Error: Musr return false so shouldn't recognize any more elements.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that consecutive calls to hasNext() do not move the HIterator forward.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks that hasNext() is a pure query with no side effects on the cursor position.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HIterator and calls {@code hasNext()} three times in a row, asserting true each time. Then calls {@code next()} and asserts that it still returns "A", i.e. the cursor has not been advanced by hasNext().</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A]); the HIterator is positioned after "A".</li>
     *   <li><strong>Expected Results:</strong> The three {@code hasNext()} calls return true; {@code next()} returns "A".</li>
     * </ul>
     */
    @Test
    public void testHasNextMultipleCalls() {
        stackTest.push("A");
        HIterator iterator = stackTest.iterator();
        assertTrue("Error: First call to hasNext() should be true.", iterator.hasNext());
        assertTrue("Error: Second call to hasNext() should still be true.", iterator.hasNext());
        assertTrue("Error: Third call to hasNext() should still be true.", iterator.hasNext());
        assertEquals("Error: The element has to be A.", "A", iterator.next());
    }

    // test for HIterator's next() method

    /**
     * <strong>Summary:</strong> Verifies that next() throws NoSuchElementException on an HIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: requesting an element when the iteration has no elements at all.</li>
     *   <li><strong>Test Description:</strong> Obtains an HIterator from the empty stack and calls {@code next()} once.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.util.NoSuchElementException} is thrown by {@code next()}.</li>
     * </ul>
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testNextOnEmptyStackThrowsNoSuchElementException() {
        HIterator iterator = stackTest.iterator();
        iterator.next();
    }

    /**
     * <strong>Summary:</strong> Verifies that next() throws NoSuchElementException once all the elements have already been returned.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: going past the end of a non-empty collection (the stack itself is not modified).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HIterator and calls {@code next()} twice, consuming both elements ("A" and then "B"). Then calls {@code next()} a third time.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the HIterator is positioned after the last element.</li>
     *   <li><strong>Expected Results:</strong> The first two calls return normally; the third call throws a {@code java.util.NoSuchElementException}.</li>
     * </ul>
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testNextOnClearedStackThrowsNoSuchElementException() {
        stackTest.push("A");
        stackTest.push("B");
        HIterator iterator = stackTest.iterator();
        iterator.next();
        iterator.next();
        iterator.next(); // has to fail because the pointer has reached the limit of the Vector
    }

    /**
     * <strong>Summary:</strong> Verifies that next() returns the elements in the right order, from the bottom (index 0) to the top of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard behavior of next(): correctness of the returned values and of the traversal order.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and then "B" ("A" is at index 0, "B" is on top), creates an HIterator and calls {@code next()} twice, comparing each returned value with the expected one.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the HIterator is positioned after the last element.</li>
     *   <li><strong>Expected Results:</strong> The first {@code next()} returns "A", the second returns "B".</li>
     * </ul>
     */
    @Test
    public void testNextStandardBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HIterator iterator = stackTest.iterator();
        assertEquals("Error: First element should be A.", "A", iterator.next());
        assertEquals("Error: Second element should be B.", "B", iterator.next());
    }

    // test for HIterator's remove() method

    /**
     * <strong>Summary:</strong> Verifies that remove() throws IllegalStateException when called on an HIterator of an empty stack before any next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: remove() called when there is no element previously returned by next().</li>
     *   <li><strong>Test Description:</strong> Obtains an HIterator from the empty stack and immediately calls {@code remove()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    // HIteratorRemove for not confuse with HCollection's remove
    public void testHIteratorRemoveOnEmptyIteratorThrowsIllegalStateException() {
        HIterator iterator = stackTest.iterator();
        iterator.remove();
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() throws IllegalStateException when called on a non-empty stack before any next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: remove() called without a previous next(), even though elements are available.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 19 (20 elements, 19 on top), creates an HIterator and immediately calls {@code remove()} without calling {@code next()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: it still contains the 20 Integers 0..19 in insertion order.</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown and no element is removed.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testHIteratorRemoveOnPopulatedIteratorWithoutNextThrowsIllegalStateException() {
        for (int i = 0; i < 20; i++)
        {
            stackTest.push(i);
        }
        HIterator iterator = stackTest.iterator();
        iterator.remove(); 
    }

    /**
     * <strong>Summary:</strong> Verifies that calling remove() twice in a row, without a next() in between, throws IllegalStateException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: each next() allows at most one remove().</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HIterator, calls {@code next()} (returns "A") and {@code remove()} (removes "A", legal call). Then calls {@code remove()} a second time without calling {@code next()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Only the first remove() has taken effect: the stack contains only "B".</li>
     *   <li><strong>Expected Results:</strong> The first {@code remove()} succeeds; the second throws a {@code myAdapter.IllegalStateException}.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testHIteratorRemoveDoubleThrowsIllegalStateException() {
        stackTest.push("A");
        stackTest.push("B");
        HIterator iterator = stackTest.iterator();
        iterator.next();
        iterator.remove(); 
        iterator.remove(); 
    }

    /**
     * <strong>Summary:</strong> Verifies that a remove() on the HIterator decreases the size of the backing stack by one.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks only the effect of remove() on size(), removing an element in the middle of the stack; which element is removed is checked by testHIteratorRemoveStandardBehavior().</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2 and 3 (stack [1, 2, 3], 3 on top), creates an HIterator, calls {@code next()} twice (returns 1 and then 2) and calls {@code remove()}, which removes 2 (index 1). Then reads {@code stackTest.size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [1, 3].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveDecreasesSize() {
        stackTest.push(1);
        stackTest.push(2);
        stackTest.push(3);
        HIterator iterator = stackTest.iterator(); 
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals("Error: Stack size must be 2 after one removal.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() deletes exactly the element returned by the last call to next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the identity of the removed element: the element returned by next() (the bottom element, index 0) must be removed, while the other one (the top) must stay in the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1 and then the Integer 2 (stack [1, 2], 2 on top). Creates an HIterator and calls {@code next()}, which returns 1, then calls {@code remove()}. Finally checks the remaining content with two queries: {@code peek()} and {@code contains(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains only the Integer 2.</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns 2 (the top element is unchanged) and {@code contains(1)} returns false (1 has been removed).</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveStandardBehavior() {
        stackTest.push(1);
        stackTest.push(2);
        HIterator iterator = stackTest.iterator();
        iterator.next();
        iterator.remove();
        assertEquals("Error: 2 should be the top of the stack", 2, stackTest.peek());
        assertFalse("Error: 1 shouldn't be an element of the stack.", stackTest.contains(1));
    }

    // additional tests for HIterator's remove(): different positions and multiple removals

    /**
     * <strong>Summary:</strong> Verifies that remove() deletes the last element of the iteration, i.e. the top of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Position coverage: removal at the end of the stack (the other tests remove at index 0 and in the middle).</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2 and 3 (stack [1, 2, 3], 3 on top), creates an HIterator and calls {@code next()} three times (returns 1, 2 and 3), then calls {@code remove()}. Finally checks {@code size()}, {@code peek()}, {@code contains(3)} and the HIterator's {@code hasNext()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [1, 2]; the HIterator is at the end of the stack.</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2, {@code peek()} returns 2, {@code contains(3)} returns false and {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveTopElement() {
        stackTest.push(1);
        stackTest.push(2);
        stackTest.push(3);
        HIterator iterator = stackTest.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals("Error: Stack size must be 2 after one removal.", 2, stackTest.size());
        assertEquals("Error: 2 should be the new top of the stack.", 2, stackTest.peek());
        assertFalse("Error: 3 shouldn't be an element of the stack.", stackTest.contains(3));
        assertFalse("Error: The iteration must be over after removing the last element.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that after a remove() in the middle of the stack the iteration continues from the following element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Cursor consistency after a removal: the element after the removed one must not be skipped.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HIterator, calls {@code next()} twice (checking it returns "A" and "B") and calls {@code remove()} (removes "B"). Then checks that {@code next()} returns "C" and that {@code hasNext()} is false; finally checks {@code size()}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, C]; the HIterator is at the end of the stack.</li>
     *   <li><strong>Expected Results:</strong> The {@code next()} after the removal returns "C", {@code hasNext()} returns false, {@code size()} returns 2, {@code get(0)} returns "A" and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveMiddleElementAndContinue() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HIterator iterator = stackTest.iterator();
        assertEquals("Error: First element should be A.", "A", iterator.next());
        assertEquals("Error: Second element should be B.", "B", iterator.next());
        iterator.remove();
        assertEquals("Error: After removing B the next element must be C.", "C", iterator.next());
        assertFalse("Error: C was the last element.", iterator.hasNext());
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: Element A should be at index 0.", "A", stackTest.get(0));
        assertEquals("Error: Element C should be at index 1.", "C", stackTest.get(1));
    }

    /**
     * <strong>Summary:</strong> Verifies that calling next() and remove() on every element empties the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Multiple consecutive removals: every element is removed, in every position, during a single forward scan.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HIterator and, while {@code hasNext()} is true, calls {@code next()} and {@code remove()}, counting the removals. Then checks the counter and {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> Exactly 3 removals are performed (no element is skipped) and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveAllElements() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HIterator iterator = stackTest.iterator();
        int removed = 0;
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            removed++;
        }
        assertEquals("Error: Every element must be visited and removed.", 3, removed);
        assertTrue("Error: The stack should be empty.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() works correctly when several non-adjacent elements are removed during the same scan.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Multiple removals in different positions (bottom excluded, middle and top) within one iteration.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2, 3, 4, 5 and 6, creates an HIterator and scans the whole stack calling {@code remove()} on every even value (2, 4 and 6). Then checks {@code size()} and {@code get()} at indices 0, 1 and 2.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: [1, 3, 5].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 3; {@code get(0)}, {@code get(1)} and {@code get(2)} return 1, 3 and 5.</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveSeveralPositions() {
        for (int i = 1; i <= 6; i++) {
            stackTest.push(i);
        }
        HIterator iterator = stackTest.iterator();
        while (iterator.hasNext()) {
            int value = (Integer) iterator.next();
            if (value % 2 == 0) {
                iterator.remove();
            }
        }
        assertEquals("Error: Three elements should remain.", 3, stackTest.size());
        assertEquals("Error: Element at index 0 should be 1.", 1, stackTest.get(0));
        assertEquals("Error: Element at index 1 should be 3.", 3, stackTest.get(1));
        assertEquals("Error: Element at index 2 should be 5.", 5, stackTest.get(2));
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() deletes a null element and leaves equal elements around it untouched.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling and duplicates: the removal is by position, not by value.</li>
     *   <li><strong>Test Description:</strong> Pushes "A", {@code null} and "A", creates an HIterator, calls {@code next()} twice (returns "A" and null) and calls {@code remove()}. Then checks {@code size()}, {@code contains(null)}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, A].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2, {@code contains(null)} returns false, {@code get(0)} and {@code get(1)} both return "A".</li>
     * </ul>
     */
    @Test
    public void testHIteratorRemoveNullBetweenDuplicates() {
        stackTest.push("A");
        stackTest.push(null);
        stackTest.push("A");
        HIterator iterator = stackTest.iterator();
        iterator.next();
        assertNull("Error: The second element should be null.", iterator.next());
        iterator.remove();
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertFalse("Error: null should have been removed.", stackTest.contains(null));
        assertEquals("Error: Element at index 0 should be A.", "A", stackTest.get(0));
        assertEquals("Error: Element at index 1 should be A.", "A", stackTest.get(1));
    }
}
