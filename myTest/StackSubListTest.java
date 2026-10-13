package myTest;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/** 
 * <strong>Summary:</strong> This Test Suite verifies the behavior of the subList view implemented 
 * within the StackAdapter. The suite is divided into sections using comments to group tests by the method 
 * being tested. The tests check bounds, structural modifications, and the correct reflection of changes 
 * between the subList and its backing parent stack, maintaining compatibility with the J2SE 1.4.2 collections framework.
 * 
 * <ul>
 *   <li><strong>Test Case Design:</strong> The tests have been developed following the Test Driven
 *   Development methodology. The goal is to validate the View Pattern, ensuring that operations 
 *   on the subList accurately manipulate only the specified range of the underlying stack.</li>  
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
public class StackSubListTest {
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

    // TEST FOR SUBLIST (StackSubList) METHODS: 

    // test for StackSubList's size() method

    /**
     * <strong>Summary:</strong> Verifies that size() returns the number of elements in the range of the subList.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: range in the middle of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", creates {@code subList(1, 3)} (view [B, C]) and calls {@code size()} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C, D]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testSizeRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList subList = stackTest.subList(1, 3);
        assertEquals("Error: SubList size must be 2. ", 2, subList.size());
    }

    // test for StackSubList's isEmpty() method 
        
    /**
     * <strong>Summary:</strong> Verifies that a subList with fromIndex equal to toIndex is empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: empty range (fromIndex == toIndex) on a non-empty stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1 and 2, creates {@code subList(1, 1)} and calls {@code isEmpty()} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, 2]; the subList is empty.</li>
     *   <li><strong>Expected Results:</strong> The subList {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testIsEmptyRegularBehavior() {
        stackTest.push(1);
        stackTest.push(2);
        HList subList = stackTest.subList(1, 1);
        assertTrue("Error: SubList ought be empty. ", subList.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that a subList that contains elements is not empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Range covering the whole stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1 and 2, creates {@code subList(0, 2)} (view [1, 2]) and calls {@code isEmpty()} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, 2]; the subList is [1, 2].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code isEmpty()} returns false.</li>
     * </ul>
     */
    @Test
    public void testIsEmptyOnPopulatedSubList() {
        stackTest.push(1);
        stackTest.push(2);
        HList subList = stackTest.subList(0, 2);
        assertFalse("Error: A subList with elements shouldn't be empty. ", subList.isEmpty());
    }

    // test for StackSubList's clear() method

    /**
     * <strong>Summary:</strong> Verifies that clear() on a subList removes only the elements of its range from the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Structural modification through the view: elements outside the range must not be touched.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code clear()} on it; then calls the subList {@code isEmpty()} and the stack {@code size()} and {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [A]; the subList is empty.</li>
     *   <li><strong>Expected Results:</strong> The subList {@code isEmpty()} returns true, the stack {@code size()} returns 1 and {@code peek()} returns "A".</li>
     * </ul>
     */
    @Test
    public void testClearRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        subList.clear();
        assertTrue("Error: The subList isn't empty as it should be.", subList.isEmpty());
        assertEquals("Error: The parent stack size must be 1.", 1, stackTest.size());
        assertEquals("Error: The parent stack should still contain A.", "A", stackTest.peek());
    }

    // test for StackSubList's get(int index) method

    /**
     * <strong>Summary:</strong> Verifies that get(int) on a subList throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid subList index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code get(-1)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        subList.get(-1);
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) on a subList throws IndexOutOfBoundsException for an index equal to the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the subList range: the index is valid for the parent stack but not for the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(1, 2)} (view [B], size 1) and calls {@code get(subList.size())}, i.e. {@code get(1)}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [B].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetIndexEqualToSubListSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(1, 2);
        subList.get(subList.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) on a subList throws IndexOutOfBoundsException for an index greater than the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index beyond the upper bound of the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(1, 2)} (view [B], size 1) and calls {@code get(subList.size() + 1)}, i.e. {@code get(2)}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [B].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetIndexGreaterThanSubListSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(1, 2);
        subList.get(subList.size() + 1);
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) on a subList uses indices relative to the start of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index mapping: subList index i corresponds to parent index fromIndex + i.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code get(0)} and {@code get(1)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns "B" and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testGetRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        assertEquals("Error: The element at index 0 should be B.", "B", subList.get(0));
        assertEquals("Error: The element at index 1 should be C.", "C", subList.get(1));
    }

    // test for StackSubList's set(int index, Object element) method

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) on a subList throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid subList index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code set(-1, "B")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSetNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        subList.set(-1, "B");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) on a subList throws IndexOutOfBoundsException for an index equal to the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the subList range.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(1, 2)} (view [B], size 1) and calls {@code set(subList.size(), "C")}, i.e. {@code set(1, "C")}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [B].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSetIndexEqualToSubListSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(1, 2);
        subList.set(subList.size(), "C");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) on a subList replaces the element and the change is visible in the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Non-structural modification through the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 2)} (view [B]), calls {@code set(0, "D")} on it and checks the returned value; then checks the subList {@code get(0)} and the stack {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, D, C]; the subList is [D].</li>
     *   <li><strong>Expected Results:</strong> {@code set()} returns "B"; the subList {@code get(0)} and the stack {@code get(1)} both return "D".</li>
     * </ul>
     */
    @Test
    public void testSetRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 2);
        Object old = subList.set(0, "D");
        assertEquals("Error: Must return the changed element.", "B", old);
        assertEquals("Error: The new element must be at index 0 of subList.", "D", subList.get(0));
        assertEquals("Error: The new element must be at index 1 of parent stack.", "D", stackTest.get(1));
    }

    // test for StackSubList's add(int index, Object element) method 

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) on a subList throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid subList insertion range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code add(-1, "B")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        subList.add(-1, "B");
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) on a subList throws IndexOutOfBoundsException for an index greater than the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the subList insertion range (index &gt; size()); the index would still be valid for the parent stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 1)} (view [A], size 1) and calls {@code add(subList.size() + 1, "C")}, i.e. {@code add(2, "C")}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddIndexGreaterThanSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 1);
        subList.add(subList.size() + 1, "C");
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) with index equal to the subList size appends the element at the end of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the largest valid insertion index of the view, which is in the middle of the parent stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(0, 2)} (view [A, B]) and calls {@code add(subList.size(), "D")} on it; then checks the subList {@code size()} and {@code get(2)}, and the stack {@code size()}, {@code get(2)} and {@code get(3)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, B, D, C]; the subList is [A, B, D].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 3 and {@code get(2)} returns "D"; the stack {@code size()} returns 4, {@code get(2)} returns "D" and {@code get(3)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testAddIndexEqualToSubListSizeRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(0, 2); // "A", "B"
        subList.add(subList.size(), "D");
        assertEquals("Error: SubList size must be 3.", 3, subList.size());
        assertEquals("Error: Element D should be at index 2 of subList.", "D", subList.get(2));
        assertEquals("Error: Stack size must be 4.", 4, stackTest.size());
        assertEquals("Error: Element D should be at index 2 of parent stack.", "D", stackTest.get(2));
        assertEquals("Error: Element C should be shifted to index 3 of parent stack.", "C", stackTest.get(3));
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) on a subList shifts the following elements to the right in both the view and the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion at the beginning of a view that starts in the middle of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 2)} (view [B]) and calls {@code add(0, "D")} on it; then checks the subList {@code size()} and {@code get(0)}, and the stack {@code size()}, {@code get(1)} and {@code get(2)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, D, B, C]; the subList is [D, B].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 2 and {@code get(0)} returns "D"; the stack {@code size()} returns 4, {@code get(1)} returns "D" and {@code get(2)} returns "B".</li>
     * </ul>
     */
    @Test
    public void testAddIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 2);
        subList.add(0, "D"); 
        assertEquals("Error: SubList size must be 2.", 2, subList.size());
        assertEquals("Error: Element D should be at index 0 of subList.", "D", subList.get(0));
        assertEquals("Error: Stack size must be 4.", 4, stackTest.size());
        assertEquals("Error: Element D should be at index 1 of parent stack.", "D", stackTest.get(1));
        assertEquals("Error: Element B should be shifted to index 2 of parent stack.", "B", stackTest.get(2));
    }

    // test for StackSubList's remove(int index) method 

    /**
     * <strong>Summary:</strong> Verifies that remove(int) on a subList throws IndexOutOfBoundsException for an index equal to the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the subList range; the index would still be valid for the parent stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 1)} (view [A], size 1) and calls {@code remove(subList.size())}, i.e. {@code remove(1)}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveIndexEqualToStackSizeThrowsIndexOutOfBoundsException(){
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 1);
        subList.remove(subList.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) on a subList removes the element from both the view and the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Structural modification through the view, removing its first element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code remove(0)} on it, checking the returned value; then checks the subList {@code size()}, and the stack {@code size()} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C]; the subList is [C].</li>
     *   <li><strong>Expected Results:</strong> {@code remove(0)} returns "B", the subList {@code size()} returns 1, the stack {@code size()} returns 2 and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testRemoveIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        Object removed = subList.remove(0); 
        assertEquals("Error: remove() must return the deleted element.", "B", removed);
        assertEquals("Error: SubList size must be 1.", 1, subList.size());
        assertEquals("Error: Parent stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: Element C should be shifted to index 1 in parent stack.", "C", stackTest.get(1));
    }

    // test for StackSubList's indexOf(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) on a subList returns an index relative to the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index mapping: the element is at parent index 2 but at view index 1.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code indexOf("C")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf("C")} returns 1.</li>
     * </ul>
     */
    @Test
    public void testIndexOfRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        assertEquals("Error: Must return the first occurrence of C in subList.", 1, subList.indexOf("C"));
    }

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) on a subList returns -1 for an element that is in the parent stack but outside the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Element present in the parent but outside the range of the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code indexOf("A")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf("A")} returns -1.</li>
     * </ul>
     */
    @Test
    public void testIndexOfMissingElement() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); 
        assertEquals("Error: Must return -1 if the element is outside the bounds.", -1, subList.indexOf("A"));
    }

    // test for StackSubList's lastIndexOf(Object o) method
     
    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(Object) on a subList returns the last occurrence inside the view and ignores elements outside it.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicates inside the view and an element present only outside the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "B" and "C", creates {@code subList(0, 3)} (view [A, B, B]) and calls {@code lastIndexOf("B")} and {@code lastIndexOf("C")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, B, C]; the subList is [A, B, B].</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf("B")} returns 2 and {@code lastIndexOf("C")} returns -1.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(0, 3); // "A", "B", "B"
        assertEquals("Error: Must return index 2.", 2, subList.lastIndexOf("B"));
        assertEquals("Error: Must return -1 because searching for an element outside bounds.", -1, subList.lastIndexOf("C"));
    }

    // test for StackSubList's contains(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that contains(Object) on a subList finds elements inside the view and not those outside it.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> One element inside the view and one present only in the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 2)} (view [B]) and calls {@code contains("B")} and {@code contains("A")} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B].</li>
     *   <li><strong>Expected Results:</strong> {@code contains("B")} returns true and {@code contains("A")} returns false.</li>
     * </ul>
     */
    @Test
    public void testContainsRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 2); 
        assertTrue("Error: the method doesn't find the item.", subList.contains("B"));
        assertFalse("Error: the method finds an element that is not on the subList.", subList.contains("A"));
    }

    // test for StackSubList's toArray() method 

    /**
     * <strong>Summary:</strong> Verifies that toArray() on a subList returns only the elements of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Length and content of the returned array.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]), calls {@code toArray()} on it and checks the array length and the element at index 0.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> The array length is 2 and {@code array[0]} is "B".</li>
     * </ul>
     */
    @Test 
    public void testToArrayRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        Object[] array = subList.toArray();
        assertEquals("Error: the array size doesn't match the subList size", 2, array.length);
        assertEquals("Error: Wrong element at index 0 of the array.", "B", array[0]);
    }

    // test for StackSubList's toArray(Object[] a) method
    
    /**
     * <strong>Summary:</strong> Verifies that toArray(null) on a subList throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code toArray(null)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testToArrayObjectThrowsNullPointerException() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        subList.toArray(null);
    }

    /**
     * <strong>Summary:</strong> Verifies that toArray(Object[]) on a subList fills and returns the given array when its length equals the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument capacity exactly equal to the size of the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and {@code regular = new Object[2]}, calls {@code toArray(regular)} on the subList; checks the elements at indices 0 and 1 and checks with {@code assertSame} that {@code regular} itself is returned.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; {@code regular} contains [B, C].</li>
     *   <li><strong>Expected Results:</strong> The returned array is {@code regular} and contains "B" and "C".</li>
     * </ul>
     */
    @Test
    public void testToArrayObjectRegularCase() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        Object[] regular = new Object[2];
        Object[] arrayToReturn = subList.toArray(regular);
        assertEquals("Error: Element at index 0 must be B.", "B", arrayToReturn[0]);
        assertEquals("Error: Element at index 0 must be C.", "C", arrayToReturn[1]);
        assertSame("Error: The returned array must point to the same memory region as the passed array.", regular, arrayToReturn);
    }

    // test for StackSubList's add(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that add(Object) on a subList appends the element at the end of the view, which is inside the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion through a view that does not reach the top of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 1)} (view [A]) and calls {@code add("C")} on it, checking the returned value; then checks the subList {@code size()}, and the stack {@code size()} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C, B]; the subList is [A, C].</li>
     *   <li><strong>Expected Results:</strong> {@code add()} returns true, the subList {@code size()} returns 2, the stack {@code size()} returns 3 and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testAddObjectRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 1);
        assertTrue("Error: add() must return true.", subList.add("C"));
        assertEquals("Error: SubList size must be 2.", 2, subList.size());
        assertEquals("Error: Parent stack size must be 3.", 3, stackTest.size());
        assertEquals("Error: C should be at parent index 1.", "C", stackTest.get(1));
    }

    // test for StackSubList's remove(Object o) 

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) on a subList removes the element from both the view and the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard removal of an element inside the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and calls {@code remove("B")} on it, checking the returned value; then checks the subList {@code size()} and the stack {@code contains("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C]; the subList is [C].</li>
     *   <li><strong>Expected Results:</strong> {@code remove("B")} returns true, the subList {@code size()} returns 1 and the stack {@code contains("B")} returns false.</li>
     * </ul>
     */
    @Test
    public void testRemoveObjectStandardBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        assertTrue("Error: Must remove the element and return true.", subList.remove("B"));
        assertEquals("Error: SubList size should be 1.", 1, subList.size());
        assertFalse("Error: B should be removed from parent too.", stackTest.contains("B"));
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) on a subList does not remove an element that is outside the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Element present in the parent but outside the range of the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(1, 2)} (view [B]) and calls {@code remove("A")} on it, checking the returned value; then checks the stack {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [B].</li>
     *   <li><strong>Expected Results:</strong> {@code remove("A")} returns false and the stack {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testRemoveObjectMissingElementReturnsFalse() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(1, 2);
        assertFalse("Error: Shouldn't remove an element outside subList bounds.", subList.remove("A"));
        assertEquals("Error: Size should remain the same.", 2, stackTest.size());
    }

    // test for StackSubList's containsAll(HCollection c) method 

    /**
     * <strong>Summary:</strong> Verifies that containsAll(HCollection) on a subList returns true when every element of the argument is inside the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument with exactly the same elements as the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and a StackAdapter {@code collection} containing "B" and "C"; calls {@code containsAll(collection)} on the subList.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code containsAll()} returns true.</li>
     * </ul>
     */
    @Test
    public void testContainsAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        collection.push("C");
        assertTrue("Error: Must return true because collection has the same elements as subList.", subList.containsAll(collection));
    }

    // test for StackSubList's addAll(HCollection c) method

    /**
     * <strong>Summary:</strong> Verifies that addAll(HCollection) on a subList inserts all the elements at the end of the view and shifts the following parent elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion of several elements through a view that does not reach the top of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "D", creates {@code subList(0, 1)} (view [A]) and a StackAdapter {@code collection} containing "B" and "C"; calls {@code addAll(collection)} on the subList, checking the returned value; then checks the stack {@code size()}, the subList {@code size()} and the stack {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, B, C, D]; the subList is [A, B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns true, the stack {@code size()} returns 4, the subList {@code size()} returns 3 and {@code peek()} returns "D".</li>
     * </ul>
     */
    @Test
    public void testAddAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("D");
        HList subList = stackTest.subList(0, 1);
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        collection.push("C");
        assertTrue("Error: Must return true because the stack was modified.", subList.addAll(collection));
        assertEquals("Error: The parent stack size should be 4.", 4, stackTest.size());
        assertEquals("Error: The subList size should be 3.", 3, subList.size());
        assertEquals("Error: The element D should be shifted to the end.", "D", stackTest.peek());
    }

    // test for StackSubList's addAll(int index, HCollection c) method 

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) on a subList throws NullPointerException when the collection is null.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument (with a valid index).</li>
     *   <li><strong>Test Description:</strong> Creates {@code subList(0, 0)} on the empty stack and calls {@code addAll(0, null)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack and the subList are still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testAddAllIndexNullCollectionThrowsNullPointerException() {
        HList subList = stackTest.subList(0, 0);
        subList.addAll(0, null);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) on a subList throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid subList insertion range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Creates {@code subList(0, 0)} on the empty stack and an empty StackAdapter {@code collection}, then calls {@code addAll(-1, collection)} on the subList.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack and the subList are still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddAllIndexNegativeThrowsIndexOutOfBoundsException() {
        HList subList = stackTest.subList(0, 0);
        StackAdapter collection = new StackAdapter();
        subList.addAll(-1, collection);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) on a subList throws IndexOutOfBoundsException for an index greater than the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the subList insertion range (index &gt; size()).</li>
     *   <li><strong>Test Description:</strong> Creates {@code subList(0, 0)} on the empty stack and an empty StackAdapter {@code collection}, then calls {@code addAll(subList.size() + 1, collection)}, i.e. index 1, on the subList.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack and the subList are still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddAllIndexGreaterThanSubListSizeThrowsIndexOutOfBoundsException() {
        HList subList = stackTest.subList(0, 0);
        StackAdapter collection = new StackAdapter();
        subList.addAll(subList.size() + 1, collection);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) on a subList inserts the elements at the given view index, in iteration order.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion at the beginning of a view that starts in the middle of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "D" and "E", creates {@code subList(1, 3)} (view [D, E]) and a StackAdapter {@code collection} containing "B" and "C"; calls {@code addAll(0, collection)} on the subList, checking the returned value; then checks the stack {@code size()}, the subList {@code size()}, and the stack {@code get(1)} and {@code get(3)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, B, C, D, E]; the subList is [B, C, D, E].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns true, the stack {@code size()} returns 5, the subList {@code size()} returns 4, the stack {@code get(1)} returns "B" and {@code get(3)} returns "D".</li>
     * </ul>
     */
    @Test
    public void testAddAllIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("D");
        stackTest.push("E");
        HList subList = stackTest.subList(1, 3); // "D", "E"
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        collection.push("C");
        assertTrue("Error: Must return true if the subList was modified.", subList.addAll(0, collection));
        assertEquals("Error: Stack size must be 5.", 5, stackTest.size());
        assertEquals("Error: SubList size must be 4.", 4, subList.size());
        assertEquals("Error: Element B should be at index 1 of stack.", "B", stackTest.get(1));
        assertEquals("Error: Element D should be at index 3 of stack.", "D", stackTest.get(3));
    }

    // test for StackSubList's removeAll(HCollection c) method

    /**
     * <strong>Summary:</strong> Verifies that removeAll(HCollection) on a subList removes the matching elements of the view from the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument that partially overlaps the view (it also contains an element, "Z", that is nowhere in the stack).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and a StackAdapter {@code collection} containing "B" and "Z"; calls {@code removeAll(collection)} on the subList, checking the returned value; then checks the stack {@code size()}, the subList {@code size()} and the stack {@code contains("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C]; the subList is [C].</li>
     *   <li><strong>Expected Results:</strong> {@code removeAll()} returns true, the stack {@code size()} returns 2, the subList {@code size()} returns 1 and {@code contains("B")} returns false.</li>
     * </ul>
     */
    @Test
    public void testRemoveAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        collection.push("Z");
        
        assertTrue("Error: Returned false even though the subList was modified.", subList.removeAll(collection));
        assertEquals("Error: The parent stack size should be 2.", 2, stackTest.size());
        assertEquals("Error: SubList size should be 1.", 1, subList.size());
        assertFalse("Error: Element B should have been removed.", stackTest.contains("B"));
    }

    // test for StackSubList's retainAll(HCollection c) method
    
    /**
     * <strong>Summary:</strong> Verifies that retainAll(HCollection) on a subList removes only the elements of the view that are not in the argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Elements outside the view ("A") must be kept even if they are not in the argument.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and a StackAdapter {@code collection} containing only "C"; calls {@code retainAll(collection)} on the subList, checking the returned value; then checks the stack {@code size()}, the subList {@code size()} and the stack {@code contains()} for "A", "B" and "C".</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C]; the subList is [C].</li>
     *   <li><strong>Expected Results:</strong> {@code retainAll()} returns true, the stack {@code size()} returns 2, the subList {@code size()} returns 1; {@code contains("A")} and {@code contains("C")} return true, {@code contains("B")} returns false.</li>
     * </ul>
     */
    @Test
    public void testRetainAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        StackAdapter collection = new StackAdapter();
        collection.push("C");
        assertTrue("Error: Returned false even though the stack was modified", subList.retainAll(collection));
        assertEquals("Error: Parent stack size should be 2.", 2, stackTest.size());
        assertEquals("Error: subList size should be 1.", 1, subList.size());
        assertTrue("Error: Element A should still be in the stack.", stackTest.contains("A"));
        assertFalse("Error: Element B shouldn't be in the stack.", stackTest.contains("B"));
        assertTrue("Error: Element C should still be in the stack.", stackTest.contains("C"));
    }

    // test for StackSubList's equals(Object o) method
   
    /**
     * <strong>Summary:</strong> Verifies that a subList is equal to another HList with the same elements in the same order.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Comparison between a view and an independent StackAdapter with equal content.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]) and a StackAdapter {@code other} containing "B" and "C"; calls {@code subList.equals(other)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> All the structures are unchanged: the stack is [A, B, C], the subList and {@code other} are [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code equals()} returns true.</li>
     * </ul>
     */
    @Test
    public void testEqualsRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        StackAdapter other = new StackAdapter();
        other.push("B");
        other.push("C");
        assertTrue("Error: Two identical lists must be equal.", subList.equals(other));
    }

    /**
     * <strong>Summary:</strong> Verifies that a subList is equal to itself (reflexive property).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Reflexivity of equals() on a view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 2)} and calls {@code subList.equals(subList)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code equals()} returns true.</li>
     * </ul>
     */
    @Test
    public void testEqualsOnSameSubList() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 2);
        assertTrue("Error: A subList must be equal to itself.", subList.equals(subList));
    }

    /**
     * <strong>Summary:</strong> Verifies that equals() on a subList returns false when the argument is null or is not an HList.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Type safety and null handling of equals().</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code equals(null)} and {@code equals(1)} (an Integer) on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> Both calls return false.</li>
     * </ul>
     */
    @Test
    public void testEqualsOnNullAndDifferentType() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        assertFalse("Error: Comparing to null must return false.", subList.equals(null));
        assertFalse("Error: Comparing to a different object type must return false.", subList.equals(1));
    }

    // test for StackSubList's hashCode() method
   
    /**
     * <strong>Summary:</strong> Verifies that a subList and an equal HList have the same hash code.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Contract between equals() and hashCode() across different HList implementations.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(1, 2)} (view [B]) and a StackAdapter {@code other} containing "B"; compares {@code other.hashCode()} with {@code subList.hashCode()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> All the structures are unchanged: the stack is [A, B], the subList and {@code other} are [B].</li>
     *   <li><strong>Expected Results:</strong> The two hash codes are equal.</li>
     * </ul>
     */
    @Test
    public void testHashCodeRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(1, 2);
        StackAdapter other = new StackAdapter();
        other.push("B");
        assertEquals("Error: Identical collections must return the same hash code.", other.hashCode(), subList.hashCode());
    }

    // test for StackSubList subList(int fromIndex, int toIndex) method
    
    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) called on a subList throws IndexOutOfBoundsException for a negative fromIndex.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of fromIndex (fromIndex &lt; 0) on a view.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code subList(0, 1)} (view [A]) and calls {@code subList(-1, 0)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListNegativeFromIndexThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        HList subList = stackTest.subList(0, 1);
        subList.subList(-1, 0);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) called on a subList throws IndexOutOfBoundsException when toIndex is greater than the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of toIndex on a view; the index would still be valid for the parent stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 1)} (view [A], size 1) and calls {@code subList(0, subList.size() + 1)}, i.e. {@code subList(0, 2)}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListToIndexGreaterThanSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 1);
        subList.subList(0, subList.size() + 1);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) called on a subList throws IndexOutOfBoundsException when fromIndex is greater than toIndex.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Inverted range (fromIndex &gt; toIndex) with both indices individually valid for the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(0, 2)} (view [A, B]) and calls {@code subList(1, 0)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [A, B].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListInvalidRangeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(0, 2);
        subList.subList(1, 0);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) called on a subList returns a correct nested view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Recursive subListing: the indices of the nested view are relative to the outer view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", creates {@code subListOne = subList(1, 4)} (view [B, C, D]) and {@code subListTwo = subListOne.subList(1, 3)}; checks {@code size()}, {@code get(0)} and {@code get(1)} of {@code subListTwo}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C, D]; {@code subListOne} is [B, C, D] and {@code subListTwo} is [C, D].</li>
     *   <li><strong>Expected Results:</strong> {@code subListTwo.size()} returns 2, {@code get(0)} returns "C" and {@code get(1)} returns "D".</li>
     * </ul>
     */
    @Test
    public void testSubListOnSubList() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList subListOne = stackTest.subList(1, 4); // "B", "C", "D"
        HList subListTwo = subListOne.subList(1, 3); // "C", "D"
        assertEquals("Error: The second subList's size should be 2.", 2, subListTwo.size());
        assertEquals("Error: Element mismatch.", "C", subListTwo.get(0));
        assertEquals("Error: Element mismatch.", "D", subListTwo.get(1));
    }

    // test for iterator() method 

    /**
     * <strong>Summary:</strong> Verifies that the HIterator of a subList visits only the elements of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The iteration must stop at the end of the view even if the parent has more elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 2)} (view [B]) and obtains its {@code iterator()}; calls {@code hasNext()}, {@code next()} and {@code hasNext()} again.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the HIterator is at the end of the view.</li>
     *   <li><strong>Expected Results:</strong> The first {@code hasNext()} returns true, {@code next()} returns "B" and the second {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testIteratorRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 2);
        HIterator iterator = subList.iterator();
        assertTrue("Error: Iterator should have a next element.", iterator.hasNext());
        assertEquals("Error: Next element should be B.", "B", iterator.next());
        assertFalse("Error: Iterator should not see elements past the subList.", iterator.hasNext());
    }

    // test for listIterator() method 

    /**
     * <strong>Summary:</strong> Verifies that listIterator() on a subList starts at index 0 of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Initial cursor position on a view that starts in the middle of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]), calls {@code listIterator()} on it and then {@code nextIndex()} and {@code hasPrevious()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the subList is [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 0 and {@code hasPrevious()} returns false.</li>
     * </ul>
     */
    @Test
    public void testListIteratorRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3);
        HListIterator iterator = subList.listIterator();
        assertEquals("Error: The HListIterator must start at index 0.", 0, iterator.nextIndex());
        assertFalse("Error: Shouldn't have previous elements at the start of the list.", iterator.hasPrevious());
    }

    // test for listIterator(int index) method 

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) on a subList throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Creates {@code subList(0, 0)} on the empty stack and calls {@code listIterator(-1)} on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack and the subList are still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testListIteratorIndexNegativeThrowsIndexOutOfBoundsException() {
        HList subList = stackTest.subList(0, 0);
        subList.listIterator(-1);
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) on a subList throws IndexOutOfBoundsException for an index greater than the subList size.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range (index &gt; size()).</li>
     *   <li><strong>Test Description:</strong> Creates {@code subList(0, 0)} on the empty stack and calls {@code listIterator(subList.size() + 1)}, i.e. {@code listIterator(1)}, on it.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack and the subList are still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testListIteratorIndexGreaterThanSubListSizeThrowsIndexOutOfBoundsException() {
        HList subList = stackTest.subList(0, 0);
        subList.listIterator(subList.size() + 1);
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) on a subList places the cursor at the requested index of the view.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index mapping between the view and the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code subList(1, 3)} (view [B, C]), calls {@code listIterator(1)} on it and then {@code nextIndex()} and {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the cursor is at index 0 of the view.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 1 and {@code previous()} returns "B" (view index 0, parent index 1).</li>
     * </ul>
     */
    @Test
    public void testListIteratorIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList subList = stackTest.subList(1, 3); // "B", "C"
        HListIterator iterator = subList.listIterator(1);
        assertEquals("Error: Must return 1.", 1, iterator.nextIndex());
        assertEquals("Error: Must return the element at index 0.", "B", iterator.previous());
    }

    // test for removal through the subList's iterators

    /**
     * <strong>Summary:</strong> Verifies that remove() on the HIterator of a subList, after next(), removes the element from both the view and the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Forward removal through a view that starts in the middle of the parent: elements outside the view must not be touched.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", creates {@code subList(1, 3)} (view [B, C]) and its {@code iterator()}; calls {@code next()} (checking it returns "B") and {@code remove()}. Then checks the subList {@code size()} and {@code get(0)}, and the stack {@code size()}, {@code get(1)}, {@code contains("B")} and {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, C, D]; the subList is [C].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 1 and {@code get(0)} returns "C"; the stack {@code size()} returns 3, {@code get(1)} returns "C", {@code contains("B")} returns false and {@code peek()} returns "D".</li>
     * </ul>
     */
    @Test
    public void testSubListIteratorRemoveForward() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList subList = stackTest.subList(1, 3);
        HIterator iterator = subList.iterator();
        assertEquals("Error: next() should return B.", "B", iterator.next());
        iterator.remove();
        assertEquals("Error: SubList size must be 1.", 1, subList.size());
        assertEquals("Error: C should be at index 0 of the subList.", "C", subList.get(0));
        assertEquals("Error: Stack size must be 3.", 3, stackTest.size());
        assertEquals("Error: C should be at index 1 of the stack.", "C", stackTest.get(1));
        assertFalse("Error: B should have been removed from the stack.", stackTest.contains("B"));
        assertEquals("Error: D must still be the top of the stack.", "D", stackTest.peek());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() on the HListIterator of a subList, after previous(), removes the element from both the view and the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Backward removal of the last element of a view that does not reach the top of the parent.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", creates {@code subList(1, 3)} (view [B, C]) and its {@code listIterator(2)}; calls {@code previous()} (checking it returns "C") and {@code remove()}. Then checks the subList {@code size()} and {@code get(0)}, the stack {@code size()} and {@code get(2)}, and the HListIterator's {@code hasNext()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, B, D]; the subList is [B]; the cursor is at the end of the view.</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 1 and {@code get(0)} returns "B"; the stack {@code size()} returns 3 and {@code get(2)} returns "D"; {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testSubListIteratorRemoveBackward() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList subList = stackTest.subList(1, 3);
        HListIterator iterator = subList.listIterator(2);
        assertEquals("Error: previous() should return C.", "C", iterator.previous());
        iterator.remove();
        assertEquals("Error: SubList size must be 1.", 1, subList.size());
        assertEquals("Error: B should be at index 0 of the subList.", "B", subList.get(0));
        assertEquals("Error: Stack size must be 3.", 3, stackTest.size());
        assertEquals("Error: D should be at index 2 of the stack.", "D", stackTest.get(2));
        assertFalse("Error: The cursor should be at the end of the subList.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that removing every element through the HIterator of a subList empties the view and leaves the rest of the parent stack untouched.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Multiple consecutive removals through a view; the iteration must stop at the end of the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", creates {@code subList(1, 3)} (view [B, C]) and its {@code iterator()}; while {@code hasNext()} is true calls {@code next()} and {@code remove()}. Then checks the subList {@code isEmpty()} and the stack {@code size()}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is [A, D]; the subList is empty.</li>
     *   <li><strong>Expected Results:</strong> The subList {@code isEmpty()} returns true; the stack {@code size()} returns 2, {@code get(0)} returns "A" and {@code get(1)} returns "D".</li>
     * </ul>
     */
    @Test
    public void testSubListIteratorRemoveAllElements() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList subList = stackTest.subList(1, 3);
        HIterator iterator = subList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertTrue("Error: The subList should be empty.", subList.isEmpty());
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
        assertEquals("Error: A should be at index 0 of the stack.", "A", stackTest.get(0));
        assertEquals("Error: D should be at index 1 of the stack.", "D", stackTest.get(1));
    }

    /**
     * <strong>Summary:</strong> Verifies that remove() on the HIterator of a subList throws IllegalStateException if next() has not been called.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling of the subList iterator.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates {@code subList(0, 2)} (view [A, B]), obtains its {@code iterator()} and immediately calls {@code remove()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted and no subList exists yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B]; the subList is [A, B].</li>
     *   <li><strong>Expected Results:</strong> A {@code myAdapter.IllegalStateException} is thrown.</li>
     * </ul>
     */
    @Test(expected = myAdapter.IllegalStateException.class)
    public void testSubListIteratorRemoveWithoutNextThrowsIllegalStateException() {
        stackTest.push("A");
        stackTest.push("B");
        HList subList = stackTest.subList(0, 2);
        HIterator iterator = subList.iterator();
        iterator.remove();
    }
}
