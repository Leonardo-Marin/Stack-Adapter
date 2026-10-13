package myTest;

import static org.junit.Assert.*; // for using the static methods and avoid redundant Assert.method().

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;


/** 
 * <strong>Summary:</strong> This Test Suite verifies the behavior of the bidirectional HListIterator implementation 
 * within the StackAdapter. The suite is divided into sections using comments to group tests by the method 
 * being tested. The tests check how to go backwards, track the index, and change items in the list, 
 * maintaining compatibility with the J2SE 1.4.2 collections framework.
 * 
 * <ul>
 *   <li><strong>Test Case Design:</strong> The tests have been developed following the Test Driven
 *   Development methodology, meaning they were implemented before their respective methods.
 *   The goal is to validate the cursor position during multiple forward/backward movements,
 *   and to ensure that insertions and removals correctly update the underlying stack while respecting the limits of J2SE 1.4.2.</li>  
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
public class StackListIteratorTest {
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
    
    // TEST FOR HLISTITERATOR (StackListIterator) INTERFACE METHODS:

    // test of HListIterator's hasPrevious() method
    
    /**
     * <strong>Summary:</strong> Verifies that hasPrevious() returns false on an HListIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and then calls {@code hasPrevious()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty; the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code hasPrevious()} returns false.</li>
     * </ul>
     */
    @Test
    public void testHasPreviousOnEmptyStack() {
        HListIterator iterator = stackTest.listIterator();
        assertFalse("Error: Must return false on an empty stack.", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that hasPrevious() returns true after the cursor has moved forward.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: cursor between the first and the second element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator()}, calls {@code next()} (returns "A") and then calls {@code hasPrevious()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 1, between "A" and "B".</li>
     *   <li><strong>Expected Results:</strong> {@code hasPrevious()} returns true.</li>
     * </ul>
     */
    @Test
    public void testHasPreviousRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        assertTrue("Error: Must return true after advancing the cursor on a multi-element stack.", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that hasPrevious() returns false when the cursor is at the beginning of a non-empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: cursor at index 0 with elements available only in the forward direction.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator()} and calls {@code hasPrevious()} without moving the cursor.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code hasPrevious()} returns false.</li>
     * </ul>
     */
    @Test
    public void  testHasPreviousAtStartReturnsFalse() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertFalse("Error: Before any next() call, so before any advancement, must be false", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that hasPrevious() follows the cursor when it moves forward and then back.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Cursor state tracking during a forward step followed by a backward step.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HListIterator, calls {@code next()} and asserts that {@code hasPrevious()} is true; then calls {@code previous()} (returns "A") and asserts that {@code hasPrevious()} is false.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A]); the cursor is back at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code hasPrevious()} returns true after next() and false after previous().</li>
     * </ul>
     */
    @Test
    public void testHasPreviousAfterNextAndPrevious() {
        stackTest.push("A");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        assertTrue("Error: Should be true because the pointer has an element behind it.", iterator.hasPrevious());
        iterator.previous();
        assertFalse("Error: Should return false after moving back to the starting point.", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that consecutive calls to hasPrevious() do not move the cursor.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks that hasPrevious() is a pure query with no side effects.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(2)} (cursor at the end) and calls {@code hasPrevious()} three times in a row.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is still at index 2.</li>
     *   <li><strong>Expected Results:</strong> All three calls return true.</li>
     * </ul>
     */
    @Test 
    public void testHasPreviousContinuousCheck()
    {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(2); // start at index two
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasPrevious());
    }

    // test of HListIterator's previous() method 
    
    /**
     * <strong>Summary:</strong> Verifies that previous() throws NoSuchElementException on an HListIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: no element in either direction.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and then calls {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.util.NoSuchElementException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testPreviousOnEmptyStackThrowsNoSuchElementException() {
        HListIterator iterator = stackTest.listIterator();
        iterator.previous();
    }

    /**
     * <strong>Summary:</strong> Verifies that previous() throws NoSuchElementException when the cursor is at the beginning of a non-empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: going before the first element.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HListIterator with {@code listIterator()} (cursor at index 0) and calls {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A]); the cursor is still at index 0.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.util.NoSuchElementException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.util.NoSuchElementException.class)
    public void testPreviousAtStartThrowsNoSuchElementException() {
        stackTest.push("A");
        HListIterator iterator = stackTest.listIterator();
        iterator.previous();
    }

    /**
     * <strong>Summary:</strong> Verifies that previous() called right after next() returns the same element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard return value of previous(): alternating next() and previous() returns the same element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator, stores the result of {@code next()} ("A") and the result of the following {@code previous()}, then compares the two values.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is back at index 0.</li>
     *   <li><strong>Expected Results:</strong> Both calls return "A", so the two stored values are equal.</li>
     * </ul>
     */
    @Test
    public void testPreviousStandardBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        Object next = iterator.next();
        Object previous = iterator.previous();
        assertEquals("Error: Element returned by previous() must match the element just returned by next().", next, previous);
    }

    /**
     * <strong>Summary:</strong> Verifies that previous() returns a null element correctly.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling during backward traversal.</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null}, creates an HListIterator, calls {@code next()} to move over the null element and then calls {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([null]); the cursor is back at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns null.</li>
     * </ul>
     */
    @Test
    public void testPreviousWithNullElement() {
        stackTest.push(null);
        HListIterator iterator = stackTest.listIterator();
        iterator.next(); //advance over the null element
        assertNull("Error: Should be able to correctly returns a null element.", iterator.previous());
    }

    /**
     * <strong>Summary:</strong> Verifies that repeated calls to previous() return the elements in reverse order.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Complete backward traversal starting from the end of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HListIterator with {@code listIterator(3)} (cursor after the last element) and calls {@code previous()} three times.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B, C]); the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns "C", "B" and "A" in this order.</li>
     * </ul>
     */
    @Test
    public void testPreviousOnMultipleCallsReturnBackwardOrder() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HListIterator iterator = stackTest.listIterator(3);
        assertEquals("Error: Should return C.", "C", iterator.previous());
        assertEquals("Error: Should return B.", "B", iterator.previous());
        assertEquals("Error: Should return A.", "A", iterator.previous());
    }

    /**
     * <strong>Summary:</strong> Verifies the returned values when alternating next(), previous() and next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The cursor lies between two elements: moving forward and back over the same position must return the same element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator and calls, in order, {@code next()}, {@code previous()} and {@code next()}, checking each returned value.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 1, between "A" and "B".</li>
     *   <li><strong>Expected Results:</strong> All three calls return "A".</li>
     * </ul>
     */
    @Test
    public void testPreviousRelationshipWithNext() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: Should return A.", "A", iterator.next());
        assertEquals("Error: Should return A again.", "A", iterator.previous());
        assertEquals("Error: Should return A again.", "A", iterator.next());
    }

    // test of HListIterator's nextIndex() method

    /**
     * <strong>Summary:</strong> Verifies that nextIndex() returns 0 on an HListIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and then calls {@code nextIndex()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty; the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 0.</li>
     * </ul>
     */
    @Test
    public void testNextIndexOnEmptyStack() {
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: Must return zero on an empty stack.", 0, iterator.nextIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that nextIndex() returns 0 at the beginning of a non-empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Initial cursor position.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator()} and calls {@code nextIndex()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 0.</li>
     * </ul>
     */
    @Test
    public void testNextIndexAtStart() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: Must return 0 at the start of the list.", 0, iterator.nextIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that nextIndex() is incremented by one after a next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: cursor in the middle of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 9, creates an HListIterator with {@code listIterator(5)} and calls {@code nextIndex()}; then calls {@code next()} (returns 5) and calls {@code nextIndex()} again.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged (Integers 0..9); the cursor is at index 6.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 5 before next() and 6 after next().</li>
     * </ul>
     */
    @Test
    public void testNextIndexRegularBehavior() {
        for (int i = 0; i < 10; i ++)
        {
            stackTest.push(i);
        }
        HListIterator iterator = stackTest.listIterator(5);
        assertEquals("Error: The next index should be 5 before the next call from index 5.", 5, iterator.nextIndex());
        iterator.next();
        assertEquals("Error: The next index should be 6 after the next call from index 5.", 6, iterator.nextIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that nextIndex() returns size() when the cursor is at the end of the list.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: cursor after the last element.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HListIterator, calls {@code next()} to reach the end and compares {@code nextIndex()} with {@code stackTest.size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A]); the cursor is at index 1.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 1, equal to {@code size()}.</li>
     * </ul>
     */
    @Test
    public void testNextIndexAtEndReturnsSize() {
        stackTest.push("A");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        assertEquals("Error: Must equal the stack size when the cursor is at the end.", stackTest.size(), iterator.nextIndex());
    }

    // test of HListIterator's previousIndex() method

    /**
     * <strong>Summary:</strong> Verifies that previousIndex() returns -1 on an HListIterator obtained from an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and then calls {@code previousIndex()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty; the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code previousIndex()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testPreviousIndexOnEmptyStack() {
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: Must return -1 on an empty stack.", -1, iterator.previousIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that previousIndex() returns -1 at the beginning of a non-empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Initial cursor position.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator()} and calls {@code previousIndex()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code previousIndex()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testPreviousIndexAtStart() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: Must return -1 at the start of the list.", -1, iterator.previousIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that previousIndex() is incremented by one after a next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: cursor in the middle of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 9, creates an HListIterator with {@code listIterator(5)} and calls {@code previousIndex()}; then calls {@code next()} (returns 5) and calls {@code previousIndex()} again.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged (Integers 0..9); the cursor is at index 6.</li>
     *   <li><strong>Expected Results:</strong> {@code previousIndex()} returns 4 before next() and 5 after next().</li>
     * </ul>
     */
    @Test
    public void testPreviousIndexRegularBehavior() {
        for (int i = 0; i < 10; i ++)
        {
            stackTest.push(i);
        }
        HListIterator iterator = stackTest.listIterator(5);
        assertEquals("Error: The previous index should be 4 before the next call from index 5.", 4, iterator.previousIndex());
        iterator.next();
        assertEquals("Error: The previous index should be 5 after the next call from index 5.", 5, iterator.previousIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that previousIndex() returns size() - 1 when the cursor is at the end of the list.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: cursor after the last element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator, calls {@code next()} twice and compares {@code previousIndex()} with {@code stackTest.size() - 1}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); the cursor is at index 2.</li>
     *   <li><strong>Expected Results:</strong> {@code previousIndex()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testPreviousIndexAtEnd() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        iterator.next();
        assertEquals("Error: Must return the index of the last element when at the end.", stackTest.size() - 1, iterator.previousIndex());
    }

    // test of HListIterator's remove() method (inherited from HIterator): backward and mixed traversal

    /**
     * <strong>Summary:</strong> Verifies that remove() on an HListIterator throws IllegalStateException if neither next() nor previous() has been called.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: an HListIterator created in the middle of a list has no last returned element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(1)} and immediately calls {@code remove()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testListIteratorRemoveWithoutNextOrPreviousThrowsIllegalStateException() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(1);
        iterator.remove();
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() after previous() deletes the element returned by previous(), here the top of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Backward removal at the end of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HListIterator with {@code listIterator(3)}, calls {@code previous()} (checking it returns "C") and {@code remove()}. Then checks {@code size()} and {@code peek()} on the stack, and {@code hasNext()} and {@code nextIndex()} on the HListIterator.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, B]; the cursor is at index 2 (end of the list).</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2, {@code peek()} returns "B", {@code hasNext()} returns false and {@code nextIndex()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testRemoveAfterPreviousRemovesTopElement() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HListIterator iterator = stackTest.listIterator(3);
        assertEquals("Error: previous() should return C.", "C", iterator.previous());
        iterator.remove();
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: B should be the new top of the stack.", "B", stackTest.peek());
        assertFalse("Error: There are no elements after the cursor.", iterator.hasNext());
        assertEquals("Error: The cursor should be at index 2.", 2, iterator.nextIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() after previous() deletes an element in the middle and leaves the cursor in the right position.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Backward removal in the middle of the list and cursor consistency afterwards.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HListIterator with {@code listIterator(3)}, calls {@code previous()} twice (returns "C", then "B") and {@code remove()} (removes "B"). Then checks {@code size()}, {@code get(0)} and {@code get(1)} on the stack, and {@code nextIndex()} and {@code next()} on the HListIterator.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, C]; after the final next() the cursor is at index 2.</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2, {@code get(0)} returns "A", {@code get(1)} returns "C", {@code nextIndex()} returns 1 and {@code next()} returns "C".</li>
     * </ul>
     */
    @Test
    public void testRemoveAfterPreviousInTheMiddle() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HListIterator iterator = stackTest.listIterator(3);
        iterator.previous();
        assertEquals("Error: previous() should return B.", "B", iterator.previous());
        iterator.remove();
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: Element A should be at index 0.", "A", stackTest.get(0));
        assertEquals("Error: Element C should be at index 1.", "C", stackTest.get(1));
        assertEquals("Error: The cursor should be at index 1.", 1, iterator.nextIndex());
        assertEquals("Error: next() should return C.", "C", iterator.next());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() after previous() deletes the first element (bottom of the stack).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Backward removal at index 0.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates an HListIterator with {@code listIterator(1)}, calls {@code previous()} (checking it returns "A") and {@code remove()}. Then checks {@code size()} and {@code get(0)} on the stack, and {@code hasPrevious()}, {@code nextIndex()} and {@code next()} on the HListIterator.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [B, C]; after the final next() the cursor is at index 1.</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 2, {@code get(0)} returns "B", {@code hasPrevious()} returns false, {@code nextIndex()} returns 0 and {@code next()} returns "B".</li>
     * </ul>
     */
    @Test
    public void testRemoveAfterPreviousAtFirstElement() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HListIterator iterator = stackTest.listIterator(1);
        assertEquals("Error: previous() should return A.", "A", iterator.previous());
        iterator.remove();
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: B should now be at index 0.", "B", stackTest.get(0));
        assertFalse("Error: There are no elements before the cursor.", iterator.hasPrevious());
        assertEquals("Error: The cursor should be at index 0.", 0, iterator.nextIndex());
        assertEquals("Error: next() should return B.", "B", iterator.next());
    }

    /**
     * <strong>Summary:</strong> Verifies that calling previous() and remove() on every element, starting from the end, empties the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Multiple consecutive removals during a complete backward scan.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2, 3, 4 and 5, creates an HListIterator with {@code listIterator(stackTest.size())} and, while {@code hasPrevious()} is true, calls {@code previous()} and {@code remove()}, counting the removals. Then checks the counter and {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> Exactly 5 removals are performed and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testRemoveAllElementsBackward() {
        for (int i = 1; i <= 5; i++) {
            stackTest.push(i);
        }
        HListIterator iterator = stackTest.listIterator(stackTest.size());
        int removed = 0;
        while (iterator.hasPrevious()) {
            iterator.previous();
            iterator.remove();
            removed++;
        }
        assertEquals("Error: Every element must be visited and removed.", 5, removed);
        assertTrue("Error: The stack should be empty.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that two remove() calls after a single previous() throw IllegalStateException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling in the backward direction: each previous() allows at most one remove().</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(2)}, calls {@code previous()} (returns "B"), {@code remove()} (removes "B") and then {@code remove()} again.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Only the first remove() has taken effect: the stack contains only "A".</li>
     *   <li><strong>Expected Results:</strong> The first {@code remove()} succeeds; the second throws a {@code myAdapter.IllegalStateException}.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testRemoveTwiceAfterPreviousThrowsIllegalStateException() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(2);
        iterator.previous();
        iterator.remove();
        iterator.remove();
    }

    /**
     * <strong>Summary:</strong> Verifies remove() when forward and backward movements are mixed.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Removal after next() followed by removal after previous(), in two different positions of the same list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D" and creates an HListIterator. Calls {@code next()} twice (returns "A" and "B") and {@code remove()} (removes "B"); then calls {@code previous()} (checking it returns "A") and {@code remove()} (removes "A"). Finally checks {@code next()}, {@code size()}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [C, D]; after the final next() the cursor is at index 1.</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns "A", the final {@code next()} returns "C", {@code size()} returns 2, {@code get(0)} returns "C" and {@code get(1)} returns "D".</li>
     * </ul>
     */
    @Test
    public void testRemoveMixedDirections() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals("Error: previous() should return A after removing B.", "A", iterator.previous());
        iterator.remove();
        assertEquals("Error: next() should return C.", "C", iterator.next());
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: Element C should be at index 0.", "C", stackTest.get(0));
        assertEquals("Error: Element D should be at index 1.", "D", stackTest.get(1));
    }

    // test of HListIterator's set(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that set(Object) throws IllegalStateException if neither next() nor previous() has been called.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: no element to replace.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and immediately calls {@code set("A")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testSetWithoutNextOrPreviousThrowsIllegalStateException() {
        HListIterator iterator = stackTest.listIterator();
        iterator.set("A");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(Object) throws IllegalStateException if called right after remove().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: the element returned by next() no longer exists after remove().</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HListIterator, calls {@code next()} (returns "A"), calls {@code remove()} (removes "A") and then calls {@code set("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty (remove() succeeded, set() had no effect).</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testSetAfterRemoveThrowsIllegalStateException() {
        stackTest.push("A");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        iterator.remove();
        iterator.set("B");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(Object) throws IllegalStateException if called right after add(Object).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: add() invalidates the last returned element.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an HListIterator, calls {@code next()} (returns "A"), calls {@code add("B")} and then calls {@code set("C")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, B] (add() succeeded, set() had no effect).</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testSetAfterAddThrowsIllegalStateException () {
        stackTest.push("A");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        iterator.add("B");
        iterator.set("C");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(Object) replaces the element returned by next().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard replacement after a forward step.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator, calls {@code next()} (returns "A") and then {@code set("C")}; finally calls {@code contains("A")}, {@code contains("C")} and {@code size()} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [C, B].</li>
     *   <li><strong>Expected Results:</strong> {@code contains("A")} returns false, {@code contains("C")} returns true and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testSetRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        iterator.next();
        iterator.set("C");
        assertFalse("Error: Element A should have been replaced.", stackTest.contains("A"));
        assertTrue("Error: Element C should be in the stack.", stackTest.contains("C"));
        assertEquals("Error: The stack size must be still 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that set(Object) replaces the element returned by previous().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard replacement after a backward step.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(2)}, calls {@code previous()} (returns "B") and then {@code set("C")}; finally calls {@code peek()} and {@code size()} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, C].</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns "C" and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testSetReplaceElementAfterPrevious() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(2);
        iterator.previous();
        iterator.set("C");
        assertEquals("Error: The top element should now be C.", "C", stackTest.peek());
        assertEquals("Error: The stack size must be still 2.", 2, stackTest.size());
    }

    // test of HListIterator's add(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that remove() throws IllegalStateException if called right after add(Object), also when the cursor was moved with previous().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: add() invalidates the last returned element for remove() too (testSetAfterAddThrowsIllegalStateException covers set()),
     *   reached through a backward movement instead of a forward one.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(2)}, calls {@code previous()} (returns "B"),
     *   then {@code add("C")} (inserted at index 1, before "B") and finally {@code remove()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: [A, C, B] (add() succeeded, remove() had no effect).</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown by {@code remove()}.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testRemoveAfterAddThrowsIllegalStateException() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(2);
        iterator.previous();
        iterator.add("C");
        iterator.remove();
    }

    /**
     * <strong>Summary:</strong> Verifies that add(Object) on an HListIterator of an empty stack inserts the element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion into an empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack, calls {@code add("A")}, then calls {@code size()} and {@code contains("A")} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [A]; the cursor is at index 1.</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 1 and {@code contains("A")} returns true.</li>
     * </ul>
     */
    @Test
    public void testAddOnEmptyStack() {
        HListIterator iterator = stackTest.listIterator();
        iterator.add("A");
        assertEquals("Error: Stack size must be 1.", 1, stackTest.size());
        assertTrue("Error: Element A must be in the stack.", stackTest.contains("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that add(Object) inserts the element immediately before the cursor and moves the cursor after it.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Positional accuracy of the insertion in the middle of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator with {@code listIterator(1)} (cursor between "A" and "B") and calls {@code add("C")}. Then calls {@code previous()} and checks it returns "C"; calls {@code next()} (returns "C" again, value not checked) and then {@code next()} and checks it returns "B"; finally calls {@code size()} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: [A, C, B]; the cursor is at index 3 (end of the list).</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns "C", the last {@code next()} returns "B" and {@code size()} returns 3.</li>
     * </ul>
     */
    @Test
    public void testAddRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(1);
        iterator.add("C"); // element is inserted before the cursor and the cursor shift foward afterwards.
        assertEquals("Error: previous() must return C.", "C", iterator.previous());
        iterator.next(); 
        assertEquals("Error: next() must remain unaffected and return B.", "B", iterator.next());
        assertEquals("Error: Size must be 3.", 3, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that add(Object) accepts a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack, calls {@code add(null)}, then calls {@code size()} and {@code peek()} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [null].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 1 and {@code peek()} returns null.</li>
     * </ul>
     */
    @Test
    public void testAddNullElement() {
        HListIterator iterator = stackTest.listIterator();
        iterator.add(null);
        assertEquals("Error: Stack size should be 1.", 1, stackTest.size());
        assertNull("Error: The top element should be null.", stackTest.peek());
    }

    /**
     * <strong>Summary:</strong> Verifies that consecutive add(Object) calls insert elements of different types in order.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements; each insertion is placed after the previous one because the cursor moves forward.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack and then {@code add(1)}, {@code add("Hi")} and {@code add('A')}; finally calls {@code size()}, {@code peek()}, {@code contains(1)} and {@code contains("Hi")} on the stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: [1, "Hi", 'A'].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 3, {@code peek()} returns 'A', {@code contains(1)} and {@code contains("Hi")} return true.</li>
     * </ul>
     */
    @Test
    public void testAddDifferentTypes() {
        HListIterator iterator = stackTest.listIterator();
        iterator.add(1); // Integer
        iterator.add("Hi"); // String
        iterator.add('A'); // Character
        assertEquals("Error: Stack size should be 3.", 3, stackTest.size());
        assertEquals("Error: The top element should be the Character 'A'.", 'A', stackTest.peek());
        assertTrue("Error: Element 1 should be in the stack.", stackTest.contains(1));
        assertTrue("Error: Element Hi should be in the stack.", stackTest.contains("Hi"));
    }
}
