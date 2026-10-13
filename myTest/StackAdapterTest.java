package myTest;

import static org.junit.Assert.*; // for using the static methods and avoid redundant Assert.method().

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import myAdapter.*;


/** 
 * <strong>Summary:</strong> This Test Suite aims to test the functionalities of the StackAdapter class. 
 * The suite is divided into sections using comments to categorize tests covering Stack-specific operations 
 * and HCollection/HList inherited methods, ensuring full compatibility with the J2SE 1.4.2 collections framework.
 * 
 * <ul>
 *   <li><strong>Test Case Design:</strong> The tests have been developed following the Test Driven
 *   Development methodology, meaning they were implemented before their respective methods. The test cases 
 *   are designed to verify standard behaviors, boundary conditions, multiple types, and exception handling.
 *   The goal is to make sure the adaptee works exactly like a 1.4.2 Stack.</li>  
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
public class StackAdapterTest {
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

    // TEST REGARDING THE INITIALIZATION HANDLING

    //TEST FOR STACK SPECIFIC METHODS:
    
    //test of stack's empty() method

    /**
     * <strong>Summary:</strong> Verifies that a newly created stack is empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the initial state of the data structure.</li>
     *   <li><strong>Test Description:</strong> Calls {@code empty()} on {@code stackTest} without inserting any element.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code empty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testEmptyOnNewStack() { 
        assertTrue("Error: A new stack ought be empty. ", stackTest.empty());
    }
    
    /**
     * <strong>Summary:</strong> Verifies that a stack containing one element is not recognized as empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the smallest non-empty stack (one element).</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1 and calls {@code empty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [1].</li>
     *   <li><strong>Expected Results:</strong> {@code empty()} returns false.</li>
     * </ul>
     */
    @Test
    public void testEmptyOnOneElementStack() { 
        stackTest.push(1);
        assertFalse("Error: A stack with an element shouldn't be empty. ", stackTest.empty());
    }
    
    /**
     * <strong>Summary:</strong> Verifies that a stack containing several elements is not recognized as empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: stack with more than one element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 4, 3, 2 and 1 (1 on top) and calls {@code empty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains four elements: [4, 3, 2, 1].</li>
     *   <li><strong>Expected Results:</strong> {@code empty()} returns false.</li>
     * </ul>
     */
    @Test
    public void testEmptyOnManyElementStack() { 
        stackTest.push(4);
        stackTest.push(3);
        stackTest.push(2);
        stackTest.push(1);
        assertFalse("Error: A stack with an element shouldn't be empty. ", stackTest.empty());
    }
    
    //test of stack's peek() method  
    
    /**
     * <strong>Summary:</strong> Verifies that peek() on an empty stack throws EmptyStackException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: reading the top of a stack that has no elements.</li>
     *   <li><strong>Test Description:</strong> Calls {@code peek()} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.util.EmptyStackException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.util.EmptyStackException.class)
    public void testPeekOnEmptyStackThrowsException() { 
        stackTest.peek();
    }

    /**
     * <strong>Summary:</strong> Verifies that peek() on a one-element stack returns that element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard return value of peek() in the smallest non-empty case.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, calls {@code peek()} and compares the returned value with 1.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack still contains exactly one element: [1].</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns the Integer 1.</li>
     * </ul>
     */
    @Test
    public void testPeekOnOneElementStack() { 
        stackTest.push(1);
        int element = (int) stackTest.peek();
        assertEquals("Error: the element returned from peek doesn't match the expected. ", 1, element);
    }

    /**
     * <strong>Summary:</strong> Verifies that peek() does not remove the top element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks that peek() is non-destructive by comparing the size after the call.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2, 3 and 4 (4 on top), calls {@code peek()} (the returned value is ignored) and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, 2, 3, 4].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 4.</li>
     * </ul>
     */
    @Test
    public void testSizeAfterPeek() {
        stackTest.push(1);
        stackTest.push(2);
        stackTest.push(3);
        stackTest.push(4);
        stackTest.peek();
        assertEquals("Error: size discrepancy. ", 4, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that peek() returns the last pushed element in a stack with several elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard return value of peek(): the top is the most recently pushed element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2, 3 and 4 in this order, calls {@code peek()} and compares the returned value with 4.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, 2, 3, 4].</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns the Integer 4.</li>
     * </ul>
     */
    @Test 
    public void testTheReturnedElementByPeek() {
        stackTest.push(1);
        stackTest.push(2);
        stackTest.push(3);
        stackTest.push(4);
        int element = (int) stackTest.peek();
        assertEquals("Error: the returned element differs from the expected", 4, element);
    }


    //test of stack's pop() method 
    
    /**
     * <strong>Summary:</strong> Verifies that pop() on an empty stack throws EmptyStackException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling: removing the top of a stack that has no elements.</li>
     *   <li><strong>Test Description:</strong> Calls {@code pop()} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.util.EmptyStackException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.util.EmptyStackException.class)
    public void testPopOnEmptyStackThrowsException() { 
        stackTest.pop();
    }

    /**
     * <strong>Summary:</strong> Verifies that pop() on a one-element stack returns that element and leaves the stack empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard behavior of pop() in the smallest non-empty case: return value and removal.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, calls {@code pop()} and compares the returned value with 1; then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code pop()} returns the Integer 1 and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testPopOnOneElementStackAndSize() {
        stackTest.push(1);
        int element = (int) stackTest.pop();
        assertEquals("Error: the element returned from pop doesn't match the expected. ", 1, element);
        assertTrue("Error: the stack isn't empty as it should be", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that pop() returns the top element of a stack with several elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard return value of pop(): the most recently pushed element (LIFO order).</li>
     *   <li><strong>Test Description:</strong> Pushes the Characters 'A', 'B', 'C' and 'D' in this order, calls {@code pop()} and compares the returned value with 'D'.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: ['A', 'B', 'C'].</li>
     *   <li><strong>Expected Results:</strong> {@code pop()} returns the Character 'D'.</li>
     * </ul>
     */
    @Test 
    public void testPopOnRegularStack() {
        stackTest.push('A');
        stackTest.push('B');
        stackTest.push('C');
        stackTest.push('D');
        char topElement = (char) stackTest.pop();
        assertEquals("Error: the returned value isn't the value at the top", 'D', topElement);
    }

    /**
     * <strong>Summary:</strong> Verifies that pop() actually removes the top element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the effect of pop() on size(); the returned value is checked by testPopOnRegularStack().</li>
     *   <li><strong>Test Description:</strong> Pushes the Characters 'A', 'B', 'C' and 'D', calls {@code pop()} (the returned value is ignored) and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: ['A', 'B', 'C'].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 3.</li>
     * </ul>
     */
    @Test
    public void testSizeAfterPop() {
        stackTest.push('A');
        stackTest.push('B');
        stackTest.push('C');
        stackTest.push('D');
        stackTest.pop();
        assertEquals("Error: size discrepancy. ", 3, stackTest.size());
    }

    
    //test of stack's push(Object item) method  
   
    /**
     * <strong>Summary:</strong> Verifies that push() on an empty stack increases its size to one.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Effect of a single insertion on size().</li>
     *   <li><strong>Test Description:</strong> Pushes the String "Test using a string" and reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: ["Test using a string"].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testPushIncreasesSize() { 
        stackTest.push("Test using a string");
        assertEquals("Error: Stack size has to be 1 after one push. ", 1, stackTest.size());
    }
    
    /**
     * <strong>Summary:</strong> Verifies that push() returns its argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard return value of push(), as specified by java.util.Stack.</li>
     *   <li><strong>Test Description:</strong> Calls {@code push("Test")} and compares the returned object with the String "Test" passed as argument.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: ["Test"].</li>
     *   <li><strong>Expected Results:</strong> {@code push()} returns "Test".</li>
     * </ul>
     */
    @Test
    public void testPushReturnsRightElement() {
        String test = "Test";
        String element = (String) stackTest.push(test);
        assertEquals("Error: The item returned differs from the one passed as an argument.", test, element);
    }

    /**
     * <strong>Summary:</strong> Verifies that push() places the new element on top of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the insertion position: the last pushed element must be the top.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "First" and then the String "Second", then calls {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: ["First", "Second"].</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns "Second".</li>
     * </ul>
     */
    @Test
    public void testPushPlacesElementAtTop() {
        stackTest.push("First");
        stackTest.push("Second");
        assertEquals("Error: The top of the stack doesn't match with the right element.", "Second", stackTest.peek());
    }

    /**
     * <strong>Summary:</strong> Verifies that push() accepts elements of different types and null.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements and null reference: the stack must accept any Object.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "Hi", the Integer 1, the Character 'A' and {@code null} in this order, then reads {@code size()} and {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains four elements: ["Hi", 1, 'A', null].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 4 and {@code peek()} returns null.</li>
     * </ul>
     */
    @Test
    public void testPushDifferentTypesAndNull() {
        stackTest.push("Hi"); // String
        stackTest.push(1); // Integer
        stackTest.push('A'); // Character
        stackTest.push(null); // Null
        assertEquals("Error: Stack should accept all types and contain 4 elements.", 4, stackTest.size());
        assertNull("Error: The top element should be null.", stackTest.peek());
    }


    //test of stack's search(Object o) method  
    
    
    /**
     * <strong>Summary:</strong> Verifies that search() returns 1 for the top element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case of the 1-based distance: the top element is at distance 1.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "One" and calls {@code search("One")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: ["One"].</li>
     *   <li><strong>Expected Results:</strong> {@code search()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testSearchTopElementOnOneElementStack() {
        stackTest.push("One");
        int position = stackTest.search("One");
        assertEquals("Error: The top element has to be at distance 1.", 1, position);
    }

    /**
     * <strong>Summary:</strong> Verifies that search() returns the correct 1-based distance from the top.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: element in the middle of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C", "D" and "E" ("E" on top) and calls {@code search("B")}; counting from the top, "B" is the fourth element.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C, D, E].</li>
     *   <li><strong>Expected Results:</strong> {@code search()} returns 4.</li>
     * </ul>
     */
    @Test
    public void testSearchRightElement() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        stackTest.push("E");
        int position = stackTest.search("B");
        assertEquals("Error: The element should be at position 4 from the top.", 4, position);
    }

    /**
     * <strong>Summary:</strong> Verifies that, with duplicates, search() returns the distance of the occurrence nearest to the top.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicate elements: only the occurrence closest to the top must be considered.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "A" and "C" ("C" on top) and calls {@code search("A")}. "A" appears at distance 2 and at distance 4 from the top.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, A, C].</li>
     *   <li><strong>Expected Results:</strong> {@code search()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testSearchWithDuplicates() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("A");
        stackTest.push("C");
        int position = stackTest.search("A");
        assertEquals("Error: Search should return 2. ", 2, position);
    }
    
    /**
     * <strong>Summary:</strong> Verifies that search() returns -1 for an element that is not in the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Missing element case.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and calls {@code search("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code search()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testSearchMissingElement() {
        stackTest.push("A");
        int position = stackTest.search("B");
        assertEquals("Error: Search should return -1, because of the missing element. ", -1, position);
    }


    // TEST FOR HCOLLECTION INTERFACE METHODS:

    //test of HCollection's size() method
    
    /**
     * <strong>Summary:</strong> Verifies that size() returns 1 after one add(Object) on an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Effect of a single HCollection insertion on size().</li>
     *   <li><strong>Test Description:</strong> Calls {@code add(1)} (Integer) and reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [1].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void sizeAfterOneAdd() {
        stackTest.add(1);
        assertEquals("Error: Wrong size after one add. ", 1, stackTest.size());
    }

    
    /**
     * <strong>Summary:</strong> Verifies that size() is equal to the number of add(Object) calls performed.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Effect of several insertions on size().</li>
     *   <li><strong>Test Description:</strong> Calls {@code add()} four times with the Integers 1, 2, 3 and 4 and reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains four elements: [1, 2, 3, 4].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 4.</li>
     * </ul>
     */
    @Test 
    public void sizeAfterMultipleAdd() {
        stackTest.add(1);
        stackTest.add(2);
        stackTest.add(3);
        stackTest.add(4);
        assertEquals("Error: Wrong size after multiple adds. ", 4, stackTest.size());
    }

    //test of HCollection's isEmpty() method
   
    /**
     * <strong>Summary:</strong> Verifies that isEmpty() returns true on a newly created stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the initial state through the HCollection method isEmpty().</li>
     *   <li><strong>Test Description:</strong> Calls {@code isEmpty()} without inserting any element.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test 
    public void isEmptyAfterCreation() {
        assertTrue("Error: Stack isn't empty. ", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that isEmpty() returns false after a single push().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the smallest non-empty stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1 and calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [1].</li>
     *   <li><strong>Expected Results:</strong> {@code isEmpty()} returns false.</li>
     * </ul>
     */
    @Test 
    public void isEmptyAfterPush()
    {
        stackTest.push(1);
        assertFalse("Error: Empty stack. ", stackTest.isEmpty());
    }
    
    //test of HCollection's contains(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that contains() detects an element that is present.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: element present in the middle of a large stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 19 (20 elements) and calls {@code contains(15)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: Integers 0..19 in insertion order.</li>
     *   <li><strong>Expected Results:</strong> {@code contains(15)} returns true.</li>
     * </ul>
     */
    @Test
    public void testContainsRegularBehavior() {
        for(int i = 0; i < 20; i++){
            stackTest.push(i);
        }
        assertTrue("Error: the method doesn't recoginze the presence of the item correctly", stackTest.contains(15));
    }

    /**
     * <strong>Summary:</strong> Verifies that contains() returns false for an element that is not present.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Missing element case, using an object of a different type from the stored ones.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 19 and calls {@code contains("A")} (a String).</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: Integers 0..19 in insertion order.</li>
     *   <li><strong>Expected Results:</strong> {@code contains("A")} returns false.</li>
     * </ul>
     */
    @Test 
    public void testContainsMissingElement() {
        for(int i = 0; i < 20; i++){
            stackTest.push(i);
        }
        assertFalse("Error: the method find an element that is not on the stack", stackTest.contains("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that contains(null) detects a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling: null must be compared with ==, not with equals().</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null} and calls {@code contains(null)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [null].</li>
     *   <li><strong>Expected Results:</strong> {@code contains(null)} returns true.</li>
     * </ul>
     */
    @Test 
    public void testContainsWithNullElement() {
        stackTest.push(null);
        assertTrue("Error: the method doesn't handle the null case properly", stackTest.contains(null));
    }


    //test of HCollection's iterator() method

    /**
     * <strong>Summary:</strong> Verifies that iterator() returns a non-null HIterator.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks that the factory method always returns a valid instance, even on an empty stack.</li>
     *   <li><strong>Test Description:</strong> Calls {@code iterator()} on the empty stack and checks the returned reference.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> The returned HIterator is not null.</li>
     * </ul>
     */
    @Test
    public void testIteratorRegularBehavior() {
        HIterator iterator = stackTest.iterator();
        assertNotNull("Error: the method should return a valid instance but return null.", iterator);
    }
    
    /**
     * <strong>Summary:</strong> Verifies that an HIterator created on a stack that has been emptied sees no elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The stack is modified before the creation of the HIterator.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, removes it with {@code pop()} and only then creates an HIterator; calls {@code hasNext()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testIteratorOnEmptiedStack() {
        stackTest.push(1);
        stackTest.pop();
        HIterator iterator = stackTest.iterator();
        assertFalse("Error: The HIterator sees element in the stack when it should see it as empty.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that an HIterator reflects changes made to the stack after its creation.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The HIterator works directly on the backing stack (it is not a snapshot): a modification after its creation must be visible.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, creates an HIterator, then removes the element with {@code pop()} and calls {@code hasNext()} on the HIterator.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testIteratorReflectsPostInstantiationChanges() {
        stackTest.push(1);
        HIterator iterator = stackTest.iterator();
        stackTest.pop();
        assertFalse("Error: The HIterator should reflect dynamically that the stack has no elements.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that the HIterator returned by iterator() sees the elements of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the link between a new HIterator and a non-empty stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, creates an HIterator and calls {@code hasNext()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1].</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns true.</li>
     * </ul>
     */
    @Test
    public void testIteratorRegularStackLinking() {
        stackTest.push(1);
        HIterator iterator = stackTest.iterator();
        assertTrue("Error: The returned HIterator should be able to see the stack's elements.", iterator.hasNext());
    }

    /**
     * <strong>Summary:</strong> Verifies that two HIterators obtained from the same stack have independent cursors.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Independence of multiple iterators on the same collection.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates two HIterators, calls {@code next()} only on the first one (it returns "A") and then calls {@code next()} on the second one.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged ([A, B]); both HIterators are positioned after "A".</li>
     *   <li><strong>Expected Results:</strong> The {@code next()} of the second HIterator returns "A" (it was not moved by the first one).</li>
     * </ul>
     */
    @Test
    public void testIteratorOnMultipleInstancesAreDisconnected() {
        stackTest.push("A");
        stackTest.push("B");
        HIterator firstHIterator = stackTest.iterator();
        HIterator secondHIterator = stackTest.iterator();
        firstHIterator.next();
        assertEquals("Error: The HIterators must be independent, so the second HIterator must be still at the beginning.", "A", secondHIterator.next());
    }

    //test of HCollection's toArray() method

    /**
     * <strong>Summary:</strong> Verifies that toArray() returns an array whose length equals the size of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the length of the returned array.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 9 (10 elements), calls {@code toArray()} and compares the array length with {@code stackTest.size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: Integers 0..9.</li>
     *   <li><strong>Expected Results:</strong> The array length is 10, equal to {@code size()}.</li>
     * </ul>
     */
    @Test 
    public void testToArrayMatchCorrectSize() {
        for (int i = 0; i < 10; i++) {
            stackTest.push(i);
        }
        Object[] array = stackTest.toArray();
        int size = array.length;
        assertEquals("Error: the array size doesn't match the stack size", stackTest.size(), size);
    }

    /**
     * <strong>Summary:</strong> Verifies that toArray() returns all the elements in the proper sequence (from bottom to top).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks content and order of the returned array.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", calls {@code toArray()} and checks the elements at indices 0, 1 and 2.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code array[0]} is "A", {@code array[1]} is "B" and {@code array[2]} is "C".</li>
     * </ul>
     */
    @Test
    public void testToArrayRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        Object[] array = stackTest.toArray();
        assertEquals("Error: Element at index 0 mismatch.", "A", array[0]);
        assertEquals("Error: Element at index 1 mismatch.", "B", array[1]);
        assertEquals("Error: Element at index 2 mismatch.", "C", array[2]);
    }

    /**
     * <strong>Summary:</strong> Verifies that the array returned by toArray() is "safe": modifying it does not modify the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Independence between the returned array and the internal storage.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, 2, 3 and 4, calls {@code toArray()}, overwrites {@code array[0]} with the String "A" and then calls {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [0, 1, 2, 3, 4].</li>
     *   <li><strong>Expected Results:</strong> {@code peek()} returns the Integer 4.</li>
     * </ul>
     */
    @Test
    public void testToArrayReturnSafeArray() {
        for(int i = 0; i < 5; i++) {
            stackTest.push(i);
        }
        Object[] array = stackTest.toArray();
        array[0] = "A"; // modify to the new array
        assertEquals("Error: The original stack has changed.", 4, stackTest.peek());
    }

    //test of HCollection's toArray(Object[] a) method

    /**
     * <strong>Summary:</strong> Verifies that toArray(Object[]) allocates a new array when the argument is too small.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument capacity smaller than the size of the stack (length 0 against 3 elements).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", calls {@code toArray(new Object[0])} and checks the length and the content of the returned array.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C].</li>
     *   <li><strong>Expected Results:</strong> The returned array has length 3 and contains "A", "B", "C" at indices 0, 1, 2.</li>
     * </ul>
     */
    @Test
    public void testToArrayObjectOnASmallerCase() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        Object[] small = new Object[0];
        // If the array given isn't large enough, the method shouldn't crash.
        Object[] arrayToReturn = stackTest.toArray(small);
        assertEquals("Error: The returned array must have the size of the stack.", 3, arrayToReturn.length);
        assertEquals("Error: Element at mismatch.", "A", arrayToReturn[0]);
        assertEquals("Error: Element at mismatch.", "B", arrayToReturn[1]);
        assertEquals("Error: Element at mismatch.", "C", arrayToReturn[2]);
    }

    /**
     * <strong>Summary:</strong> Verifies that toArray(Object[]) fills and returns the same array when its length equals the size of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument capacity exactly equal to the size of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", creates {@code regular = new Object[3]}, calls {@code toArray(regular)}, checks the elements at indices 0, 1, 2 and checks with {@code assertSame} that the returned reference is {@code regular}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; {@code regular} contains [A, B, C].</li>
     *   <li><strong>Expected Results:</strong> The returned array is the same object passed as argument and contains "A", "B", "C".</li>
     * </ul>
     */
    @Test
    public void testToArrayObjectRegularCase() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        Object[] regular = new Object[3];
        Object[] arrayToReturn = stackTest.toArray(regular);
        assertEquals("Error: Element mismatch.", "A", arrayToReturn[0]);
        assertEquals("Error: Element mismatch.", "B", arrayToReturn[1]);
        assertEquals("Error: Element mismatch.", "C", arrayToReturn[2]);
        // assert whether 2 variables point to the same memory region 
        assertSame("Error: The returned must point to the same memory region as the passed array.", regular, arrayToReturn);
    }

    /**
     * <strong>Summary:</strong> Verifies that, when the argument is larger than the stack, the element right after the last one is set to null.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument capacity greater than the size of the stack (null terminator).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates {@code big = new Object[3]} and writes "fake" in {@code big[1]}; calls {@code toArray(big)}, checks with {@code assertSame} that {@code big} itself is returned, that index 0 contains "A" and that index 1 is null.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A]; {@code big} is [A, null, null].</li>
     *   <li><strong>Expected Results:</strong> The returned array is {@code big}, {@code big[0]} is "A" and {@code big[1]} (previously "fake") is null.</li>
     * </ul>
     */
    @Test
    public void testToArrayObjectLargerCase() {
        stackTest.push("A");
        Object[] big = new Object[3];
        big[1] = "fake"; // fake data just to verify that it will be overwritten       
        Object[] arrayToReturn = stackTest.toArray(big);
        assertSame("Error: The returned must point to the same memory region as the passed array.", big, arrayToReturn);
        assertEquals("Error: Element mismatch.", "A", arrayToReturn[0]);
        assertNull("Error: The element that follow the Hcollection must be null due to the bigger size.", arrayToReturn[1]);
    }

    /**
     * <strong>Summary:</strong> Verifies that toArray(Object[]) throws ArrayStoreException when an element cannot be stored in the given array type.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Runtime type mismatch between the stack elements and the component type of the argument.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1 and calls {@code toArray(new String[1])}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1].</li>
     *   <li><strong>Expected Results:</strong> An {@code ArrayStoreException} is thrown.</li>
     * </ul>
     */
    @Test(expected = ArrayStoreException.class)
    public void testToArrayObjectThrowsArrayStoreException() {
        stackTest.push(1); 
        String[] array = new String[1];
        stackTest.toArray(array); // can't fit an Integer in a string array.
    }

    /**
     * <strong>Summary:</strong> Verifies that toArray(null) throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and calls {@code toArray(null)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayObjectThrowsNullPointerException() {
        stackTest.push("A");
        stackTest.toArray(null);
    }

    //test of HCollection's add(Object o) method

    //test of HCollection's remove(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) on an empty stack returns false.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Removal from an empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code remove("A")} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code remove()} returns false.</li>
     * </ul>
     */
    @Test
    public void testRemoveOnEmptyStackReturnsFalse() {
        assertFalse("Error: Removing from an empty stack must return false.", stackTest.remove("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) removes an element that is present and returns true.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard successful removal.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code remove("A")} and then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code remove()} returns true and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testRemoveStandardBehavior() {
        stackTest.push("A");
        boolean remove = stackTest.remove("A");
        assertTrue("Error: remove() must remove the element and return true.", remove);
        assertTrue("Error: Size should be zero after removing the only element.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) returns false and leaves the stack unchanged when the element is missing.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Removal of an element that is not in the collection.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code remove("B")} and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code remove()} returns false and {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testRemoveMissingElementReturnsFalse() {
        stackTest.push("A");
        boolean remove = stackTest.remove("B");
        assertFalse("Error: remove() shouldn't remove the element if the element isn't in the stack.", remove);
        assertEquals("Error: Size should remain the same if no element has been removed.", 1, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(null) removes a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling during removal.</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null}, calls {@code remove(null)} and then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code remove(null)} returns true and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testRemoveNullElement() {
        stackTest.push(null);
        boolean remove = stackTest.remove(null);
        assertTrue("Error: a null element should be removed.", remove);
        assertTrue("Error: Size should be zero after removing the only element.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(Object) removes only one occurrence when the element is duplicated.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicate elements: exactly one instance must be removed.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "B", calls {@code remove("B")} and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, B] (one "B" is still present).</li>
     *   <li><strong>Expected Results:</strong> {@code remove()} returns true and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testRemoveOnDuplicates() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("B");
        boolean remove = stackTest.remove("B");     
        assertTrue("Error: remove() must remove the first recurring element and return true.", remove);
        assertEquals("Error: Only one element should be removed, leaving size at 2.", 2, stackTest.size());
    }
    
    //test of HCollection's containsAll(HCollection c) method

    /**
     * <strong>Summary:</strong> Verifies that containsAll(null) throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Calls {@code containsAll(null)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllThrowsNullPointerException() {
        stackTest.containsAll(null);
    }

    /**
     * <strong>Summary:</strong> Verifies that containsAll() returns true when every element of the argument is in the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument that is a proper subset of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1, 2 and 3 into {@code stackTest}; creates a second StackAdapter {@code collection} containing 1 and 2; calls {@code stackTest.containsAll(collection)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: {@code stackTest} is [1, 2, 3], {@code collection} is [1, 2].</li>
     *   <li><strong>Expected Results:</strong> {@code containsAll()} returns true.</li>
     * </ul>
     */
    @Test
    public void testContainsAllRegularBehavior() {
        stackTest.push(1);
        stackTest.push(2);
        stackTest.push(3);
        StackAdapter collection = new StackAdapter();
        collection.push(1);
        collection.push(2);
        assertTrue("Error: Can't be false, because the stack has all elements of the collection.", stackTest.containsAll(collection));
    }

    /**
     * <strong>Summary:</strong> Verifies that containsAll() returns false when at least one element of the argument is missing.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Argument that only partially overlaps the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1 and 2 into {@code stackTest}; creates a second StackAdapter {@code collection} containing 1 and 3 (3 is not in {@code stackTest}); calls {@code stackTest.containsAll(collection)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: {@code stackTest} is [1, 2], {@code collection} is [1, 3].</li>
     *   <li><strong>Expected Results:</strong> {@code containsAll()} returns false.</li>
     * </ul>
     */
    @Test
    public void testContainsAllMissingElements() {
        stackTest.push(1);
        stackTest.push(2);
        StackAdapter collection = new StackAdapter();
        collection.push(1);
        collection.push(3);
        assertFalse("Error: Must return false beacuse the stack doesn't have all the element of the collection.", stackTest.containsAll(collection));
    }

    /**
     * <strong>Summary:</strong> Verifies that containsAll() returns true when the argument is empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the empty collection is contained in any collection.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" into {@code stackTest}, creates an empty StackAdapter and passes it to {@code containsAll()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code containsAll()} returns true.</li>
     * </ul>
     */
    @Test
    public void testContainsAllEmptyCollection() {
        stackTest.push("A");
        StackAdapter emptyCollection = new StackAdapter();
        assertTrue("Error: Can't return false when passed an empty collection.", stackTest.containsAll(emptyCollection));
    }

    //test of HCollection's addAll(HCollection) method

    /**
     * <strong>Summary:</strong> Verifies that addAll(null) throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Calls {@code addAll(null)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testAddAllThrowsNullPointerException() {
        stackTest.addAll(null);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(HCollection) accepts a collection that contains a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling: a null inside the argument must be inserted like any other element.</li>
     *   <li><strong>Test Description:</strong> Creates a StackAdapter {@code collection} containing the String "A" and {@code null}, calls {@code stackTest.addAll(collection)} and checks the returned value.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains two elements: ["A", null].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns true.</li>
     * </ul>
     */
    @Test
    public void testAddAllWorksWithCollectionOfNullElments() {
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        collection.push(null);
        boolean isWorking = stackTest.addAll(collection);
        assertTrue("Error: The stack was modified, so addAll must return true.", isWorking);
    }
    
    /**
     * <strong>Summary:</strong> Verifies that addAll(HCollection) inserts all the elements of the argument and returns true.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard insertion of a non-empty collection into an empty stack.</li>
     *   <li><strong>Test Description:</strong> Creates a StackAdapter {@code collection} containing the Strings "A" and "B", calls {@code stackTest.addAll(collection)} and then reads {@code stackTest.size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains two elements: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns true and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test  // HCollection for differentiate with HList's addAll
    public void testHCollectionAddAllRegularBehavior() {
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        collection.push("B");
        boolean isChanged = stackTest.addAll(collection);
        assertTrue("Error: Must return true because the stack was modified.", isChanged);
        assertEquals("Error: The stack size should be 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(HCollection) with an empty argument returns false and does not change the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: empty argument.</li>
     *   <li><strong>Test Description:</strong> Creates an empty StackAdapter, passes it to {@code stackTest.addAll()} and then reads {@code stackTest.size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns false and {@code size()} returns 0.</li>
     * </ul>
     */
    @Test
    public void testAddAllWithEmptyArgument() {
        StackAdapter empty = new StackAdapter();
        boolean isChanged = stackTest.addAll(empty);
        assertFalse("Error: Must return false because no element was added to the stack.", isChanged);
        assertEquals("Error: The stack size should be 0.", 0, stackTest.size());
    }

    //test of HCollection's removeAll(HCollection c) method

    /**
     * <strong>Summary:</strong> Verifies that removeAll(null) throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Calls {@code removeAll(null)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllThrowsNullPointerException() {
        stackTest.removeAll(null);
    }

    /**
     * <strong>Summary:</strong> Verifies that removeAll() removes the elements contained in the argument and returns true.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: the argument overlaps two of the three elements of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C" into {@code stackTest}; creates a StackAdapter {@code collection} containing "A" and "B"; calls {@code stackTest.removeAll(collection)}, then reads {@code size()} and calls {@code contains("A")} and {@code contains("C")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains only "C"; {@code collection} is unchanged.</li>
     *   <li><strong>Expected Results:</strong> {@code removeAll()} returns true, {@code size()} returns 1, {@code contains("A")} returns false and {@code contains("C")} returns true.</li>
     * </ul>
     */
    @Test
    public void testRemoveAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        collection.push("B");
        boolean isChanged = stackTest.removeAll(collection);
        assertTrue("Error: The method return false even though the stack was modified.", isChanged);
        assertEquals("Error: The stack size should be 1.", 1, stackTest.size());
        assertFalse("Error: Element A should have been removed.", stackTest.contains("A"));
        assertTrue("Error: Element C should still be inside the stack", stackTest.contains("C"));
    }

    /**
     * <strong>Summary:</strong> Verifies that removeAll() returns false when no element of the stack is in the argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Disjoint collections: nothing must be removed.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" into {@code stackTest}; creates a StackAdapter {@code collection} containing "Z"; calls {@code stackTest.removeAll(collection)} and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code removeAll()} returns false and {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testRemoveAllNoElementRemoved() {
        stackTest.push("A");
        StackAdapter collection = new StackAdapter();
        collection.push("Z");
        boolean isChanged = stackTest.removeAll(collection);
        assertFalse("Error: Must return false because no elements were removed.", isChanged);
        assertEquals("Error: The stack size shouldn't change.", 1, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that removeAll() removes every occurrence of the elements in the argument, not just the first one.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicate elements: all the instances of a matching element must be removed.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "A", "C", "A" into {@code stackTest} (three occurrences of "A"); creates a StackAdapter {@code collection} containing only "A"; calls {@code stackTest.removeAll(collection)}, then reads {@code size()} and calls {@code contains()} with "A", "B" and "C".</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains two elements: [B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code removeAll()} returns true, {@code size()} returns 2, {@code contains("A")} returns false, {@code contains("B")} and {@code contains("C")} return true.</li>
     * </ul>
     */
    @Test
    public void testRemoveAllWithDuplicates() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("A");
        stackTest.push("C");
        stackTest.push("A");
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        boolean isChanged = stackTest.removeAll(collection);
        assertTrue("Error: The method must return true because the stack was modified.", isChanged);
        assertEquals("Error: The stack size must be 2 after removing all three 'A's.", 2, stackTest.size());
        assertFalse("Error: Element A should have been removed.", stackTest.contains("A"));
        assertTrue("Error: Element B should still be inside the stack", stackTest.contains("B"));
        assertTrue("Error: Element C should still be inside the stack", stackTest.contains("C"));
    }
    
    //test of HCollection's retainAll(HCollection c) method
    
    /**
     * <strong>Summary:</strong> Verifies that retainAll(null) throws NullPointerException.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument.</li>
     *   <li><strong>Test Description:</strong> Calls {@code retainAll(null)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllThrowsNullPointerException() {
        stackTest.retainAll(null);
    }

    /**
     * <strong>Summary:</strong> Verifies that retainAll() keeps only the elements contained in the argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: the argument overlaps two of the three elements of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C" into {@code stackTest}; creates a StackAdapter {@code collection} containing "A" and "B"; calls {@code stackTest.retainAll(collection)}, then reads {@code size()} and calls {@code contains()} with "A", "B" and "C".</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains two elements: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code retainAll()} returns true, {@code size()} returns 2, {@code contains("A")} and {@code contains("B")} return true, {@code contains("C")} returns false.</li>
     * </ul>
     */
    @Test
    public void testRetainAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        collection.push("B");
        boolean isChanged = stackTest.retainAll(collection);
        assertTrue("Error: The method return false even though the stack was modified", isChanged);
        assertEquals("Error: Only element C should be removed.", 2, stackTest.size());
        assertTrue("Error: Element A should still be in the stack.", stackTest.contains("A"));
        assertTrue("Error: Element B should still be in the stack.", stackTest.contains("B"));
        assertFalse("Error: Element C shouldn't still be in the stack.", stackTest.contains("C"));
    }

    /**
     * <strong>Summary:</strong> Verifies that retainAll() empties the stack when no element is in the argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Disjoint collections: every element must be removed.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" into {@code stackTest}; creates a StackAdapter {@code collection} containing "B"; calls {@code stackTest.retainAll(collection)} and then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code retainAll()} returns true and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testRetainAllNoOverlap() {
        stackTest.push("A");
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        boolean isChanged = stackTest.retainAll(collection);
        assertTrue("Error: Must return true because all elements were removed.", isChanged);
        assertTrue("Error: The stack should be empty.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that retainAll() returns false when every element of the stack is in the argument.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The argument is a superset of the stack: nothing must be removed.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" into {@code stackTest}; creates a StackAdapter {@code collection} containing "A", "B" and "C"; calls {@code stackTest.retainAll(collection)}, then reads {@code size()} and calls {@code contains("A")} and {@code contains("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code retainAll()} returns false, {@code size()} returns 2, both {@code contains()} calls return true.</li>
     * </ul>
     */
    @Test
    public void testRetainAllUnchangedStack() {
        stackTest.push("A");
        stackTest.push("B");
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        collection.push("B");
        collection.push("C");
        boolean isChanged = stackTest.retainAll(collection);
        assertFalse("Error: Must return false because no elements were removed.", isChanged);
        assertEquals("Error: No element should be removed.", 2, stackTest.size());
        assertTrue("Error: Element A should still be in the stack.", stackTest.contains("A"));
        assertTrue("Error: Element B should still be in the stack.", stackTest.contains("B"));
    }

    //test of HCollection's clear() method

    /**
     * <strong>Summary:</strong> Verifies that clear() on an empty stack leaves it empty.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: clearing an empty collection must not fail.</li>
     *   <li><strong>Test Description:</strong> Calls {@code clear()} on the empty stack and then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> No exception is thrown and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testClearOnEmptyStack() {
        stackTest.clear();
        assertTrue("Error: An empty stack has to remain empty.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that clear() removes all the elements of a large stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard behavior on a stack with many elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 99 (100 elements), calls {@code clear()}, then calls {@code isEmpty()} and {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code isEmpty()} returns true and {@code size()} returns 0.</li>
     * </ul>
     */
    @Test
    public void testClearRegularBehavior() {
        for (int i = 0; i < 100; i++)
        {
            stackTest.push(i);
        }
        stackTest.clear();
        assertTrue("Error: The stack has to be empty.", stackTest.isEmpty());
        assertEquals("Error: The stack size must be 0.", 0, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that, after clear(), a previously inserted element is no longer in the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Checks the content (not only the size) after clear().</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code clear()} and then calls {@code contains("A")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code contains("A")} returns false.</li>
     * </ul>
     */
    @Test
    public void testClearRemovesElement() {
        stackTest.push("A");
        stackTest.clear();
        assertFalse("Error: No element should still be in the stack after clear().", stackTest.contains("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that an HIterator created after clear() sees no elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Interaction between clear() and a new HIterator.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code clear()}, creates an HIterator and calls {@code hasNext()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns false.</li>
     * </ul>
     */
    @Test
    public void testClearReflectsIterators() {
        stackTest.push("A");
        stackTest.clear();
        HIterator iterator = stackTest.iterator();
        assertFalse("Error: The stack should be empty so the HIterator should not find any elements.", iterator.hasNext());
    }

   //test of HCollection's equals(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that a stack is equal to itself (reflexive property).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Reflexivity of equals().</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 1 and 2 and calls {@code stackTest.equals(stackTest)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, 2].</li>
     *   <li><strong>Expected Results:</strong> {@code equals()} returns true.</li>
     * </ul>
     */
    @Test
    public void testEqualsOnSameStack() {
        stackTest.push(1);
        stackTest.push(2);
        assertTrue("Error: A stack has to be equal to itself.", stackTest.equals(stackTest));
    }

    /**
     * <strong>Summary:</strong> Verifies that two distinct stacks with the same elements in the same order are equal.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard comparison between two different objects with equal content.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C" into {@code stackTest} and into a second StackAdapter {@code other}, then calls {@code stackTest.equals(other)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: [A, B, C].</li>
     *   <li><strong>Expected Results:</strong> {@code equals()} returns true.</li>
     * </ul>
     */
    @Test
    public void testEqualsRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        StackAdapter other = new StackAdapter();
        other.push("A");
        other.push("B");
        other.push("C");
        assertTrue("Error: Two stacks identical must be equal.", stackTest.equals(other));
    }

    /**
     * <strong>Summary:</strong> Verifies the symmetric property of equals().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Symmetry: a.equals(b) implies b.equals(a).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" into {@code stackTest} and into a second StackAdapter {@code stackTestB}, then calls {@code stackTest.equals(stackTestB)} and {@code stackTestB.equals(stackTest)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> Both calls return true.</li>
     * </ul>
     */
    @Test
    public void testEqualsSymmetry() {
        stackTest.push("A");
        stackTest.push("B");
        StackAdapter stackTestB = new StackAdapter();
        stackTestB.push("A");
        stackTestB.push("B");
        assertTrue("Error: Stack A should be equal to Stack B.", stackTest.equals(stackTestB));
        assertTrue("Error: If A is equals to B, then B must be equal to A.", stackTestB.equals(stackTest));
    }

    /**
     * <strong>Summary:</strong> Verifies that two stacks with the same elements in a different order are not equal.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Order matters: equals() compares the elements position by position.</li>
     *   <li><strong>Test Description:</strong> Pushes "A" then "B" into {@code stackTest} and "B" then "A" into a second StackAdapter {@code other}, then calls {@code stackTest.equals(other)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: {@code stackTest} is [A, B], {@code other} is [B, A].</li>
     *   <li><strong>Expected Results:</strong> {@code equals()} returns false.</li>
     * </ul>
     */
    @Test
    public void testEqualsOnDifferentOrder() {
        stackTest.push("A");
        stackTest.push("B");
        StackAdapter other = new StackAdapter();
        other.push("B");
        other.push("A");
        assertFalse("Error: Two stacks with the same elements but different order shouldn't be equal.", stackTest.equals(other));
    }

    /**
     * <strong>Summary:</strong> Verifies that equals() returns false when the argument is null or is not an HList.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Type safety and null handling of equals().</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", then calls {@code equals(null)} and {@code equals(1)} (an Integer).</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> Both calls return false.</li>
     * </ul>
     */
    @Test
    public void testEqualsOnNullAndDifferentType() {
        stackTest.push("A");
        assertFalse("Error: Comparing to null must return false.", stackTest.equals(null));
        assertFalse("Error: Comparing to a completely different object type must return false.", stackTest.equals(1));
    }

    //test of HCollection's hashCode() method

    /**
     * <strong>Summary:</strong> Verifies that two equal stacks have the same hash code.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Contract between equals() and hashCode(): equal objects must have equal hash codes.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" into {@code stackTest} and into a second StackAdapter {@code other}, then compares {@code stackTest.hashCode()} with {@code other.hashCode()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> The two hash codes are equal.</li>
     * </ul>
     */
    @Test
    public void testHashCodeRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        StackAdapter other = new StackAdapter();
        other.push("A");
        other.push("B");
        assertEquals("Error: Identical stacks must return the same hash code.", stackTest.hashCode(), other.hashCode());
    }

    /**
     * <strong>Summary:</strong> Verifies that calling hashCode() twice on an unmodified stack returns the same value.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Consistency of hashCode() over time when the stack does not change.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", stores {@code stackTest.hashCode()} in a variable, calls {@code hashCode()} again and compares the two values.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> The two values are equal.</li>
     * </ul>
     */
    @Test 
    public void testHashCodeOnSameStack() {
        stackTest.push("A");
        stackTest.push("B");
        int hashCode = stackTest.hashCode();
        assertTrue("Error: Hash code on the same stack must be the same." , (stackTest.hashCode() == hashCode));
    
    }
    
    /**
     * <strong>Summary:</strong> Verifies that two empty stacks have the same hash code.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: hash code of the empty list (1 by the J2SE formula).</li>
     *   <li><strong>Test Description:</strong> Creates a second empty StackAdapter {@code empty} and compares {@code stackTest.hashCode()} with {@code empty.hashCode()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are still empty.</li>
     *   <li><strong>Expected Results:</strong> The two hash codes are equal.</li>
     * </ul>
     */
    @Test
    public void testHashCodeOnEmptyStack() {
        StackAdapter empty = new StackAdapter();
        assertEquals("Error: The hash code generated from two empty stacks must be identical.", stackTest.hashCode(), empty.hashCode());
    }

    /**
     * <strong>Summary:</strong> Verifies that the hash code changes when an element is added.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The hash code must depend on the content of the stack.</li>
     *   <li><strong>Test Description:</strong> Stores the hash code of the empty stack, pushes the String "A", computes the hash code again and compares the two values.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [A].</li>
     *   <li><strong>Expected Results:</strong> The two hash codes are different.</li>
     * </ul>
     */
    @Test
    public void testHashCodeChangesAfterModification() {
        int firstHash = stackTest.hashCode();
        stackTest.push("A");
        int secondHash = stackTest.hashCode();
        assertNotEquals("Error: Hash codes cannot match if the stacks that produces them differs.", firstHash, secondHash);
    }

    /**
     * <strong>Summary:</strong> Verifies that stacks with the same elements in a different order have different hash codes.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> The hash code must depend on the order of the elements.</li>
     *   <li><strong>Test Description:</strong> Pushes "A" then "B" into {@code stackTest} and "B" then "A" into a second StackAdapter {@code other}, then compares the two hash codes.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> Both stacks are unchanged: {@code stackTest} is [A, B], {@code other} is [B, A].</li>
     *   <li><strong>Expected Results:</strong> The two hash codes are different.</li>
     * </ul>
     */
    @Test
    public void testHashCodeDifferentOrder() {
        stackTest.push("A");
        stackTest.push("B");
        StackAdapter other = new StackAdapter();
        other.push("B");
        other.push("A");
        assertNotEquals("Error: Stacks with the same elements but in a different order must generate two different hash codes.", stackTest.hashCode(), other.hashCode());
    }
    
    // TEST FOR HLIST INTERFACE METHODS:

    // test of HList's get(int index) method

    /**
     * <strong>Summary:</strong> Verifies that get(int) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Calls {@code get(-1)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.get(-1);
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) throws IndexOutOfBoundsException for an index equal to size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range: index == size() is the first invalid value.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code get(stackTest.size())}, i.e. {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetIndexEqualToStackSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.get(stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) throws IndexOutOfBoundsException for an index greater than size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index well beyond the upper bound.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code get(stackTest.size() + 2)}, i.e. {@code get(3)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testGetIndexTooLargeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.get(stackTest.size() + 2);
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) returns the element at the given position without removing it.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: an index in the middle and the last valid index (top of the stack).</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 9 (so the element at index i is i), calls {@code get(5)} and {@code get(9)}, then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: Integers 0..9.</li>
     *   <li><strong>Expected Results:</strong> {@code get(5)} returns 5, {@code get(9)} returns 9 and {@code size()} returns 10.</li>
     * </ul>
     */
    @Test
    public void testGetRegularBehavior() {
        for (int i = 0; i < 10; i++)
        {
            stackTest.push(i);
        }
        assertEquals("Error: The element at index 5 should be 5.", 5, stackTest.get(5));
        assertEquals("Error: The element at index 9 should be 9.", 9, stackTest.get(9));
        assertEquals("Error: No element should be removed.", 10, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) returns a null element correctly.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null} and calls {@code get(0)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [null].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns null.</li>
     * </ul>
     */
    @Test
    public void testGetNullElement() {
        stackTest.push(null);
        assertNull("Error: Null must be the element at index 0.", stackTest.get(0));
    }

    /**
     * <strong>Summary:</strong> Verifies that get(int) returns elements of different types correctly.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi" and the Character 'A', calls {@code get()} with indices 0, 1 and 2 and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, "Hi", 'A'].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns 1, {@code get(1)} returns "Hi", {@code get(2)} returns 'A' and {@code size()} returns 3.</li>
     * </ul>
     */
    @Test
    public void testGetDifferentTypes() {
        stackTest.push(1); // Integer
        stackTest.push("Hi"); // String
        stackTest.push('A'); // Character
        assertEquals("Error: The element at index 0 should be 1.", 1, stackTest.get(0));
        assertEquals("Error: The element at index 1 should be Hi.", "Hi", stackTest.get(1));
        assertEquals("Error: The element at index 2 should be A.", 'A', stackTest.get(2));
        assertEquals("Error: Stack size must be 3.", 3, stackTest.size());
    }

    // test of HList's set(int index, Object element) method

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Calls {@code set(-1, "A")} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSetNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.set(-1, "A");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) throws IndexOutOfBoundsException for an index equal to size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range: index == size() is the first invalid value.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code set(stackTest.size(), "B")}, i.e. {@code set(1, "B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSetIndexEqualToStackSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.set(stackTest.size(), "B");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) throws IndexOutOfBoundsException for an index greater than size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index well beyond the upper bound.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code set(stackTest.size() + 2, "B")}, i.e. {@code set(3, "B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSetIndexGreaterThanStackSizeThrowsIndexOutOfBoundsException(){
        stackTest.push("A");
        stackTest.set(stackTest.size() + 2, "B");
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) replaces the element at the given index and returns the previous one.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard behavior: return value, new content and unchanged size.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", calls {@code set(0, "C")} and checks the returned value; then checks {@code get(0)}, {@code contains("B")} and {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [C, B].</li>
     *   <li><strong>Expected Results:</strong> {@code set()} returns "A", {@code get(0)} returns "C", {@code contains("B")} returns true and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testSetRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        Object old = stackTest.set(0, "C");
        assertEquals("Error: Must return the changed element", "A", old);
        assertEquals("Error: The new element must be at index 0.", "C", stackTest.get(0));
        assertTrue("Error: B still has to be in the stack.", stackTest.contains("B"));
        assertEquals("Error: Size must remain 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) accepts null as the new element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code set(0, null)} and then calls {@code get(0)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: [null].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns null.</li>
     * </ul>
     */
    @Test
    public void testSetNullElement() {
        stackTest.push("A");
        stackTest.set(0, null);
        assertNull("Error: Null must be the only element in the stack.", stackTest.get(0));
    }

    /**
     * <strong>Summary:</strong> Verifies that set(int, Object) accepts replacement elements of a different type.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements: the same position is overwritten with objects of different types.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1; calls {@code set(0, "Hi")} and checks {@code get(0)}; calls {@code set(0, 'A')} and checks {@code get(0)} again; finally reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains one element: ['A'].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns "Hi" after the first set and 'A' after the second; {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testSetDifferentTypes() {
        stackTest.push(1); // Integer
        stackTest.set(0, "Hi");
        assertEquals("Error: The element at index 0 should be Hi.", "Hi", stackTest.get(0));
        stackTest.set(0, 'A');
        assertEquals("Error: The element at index 0 should be A.", 'A', stackTest.get(0));
        assertEquals("Error: Stack size must be 1.", 1, stackTest.size());
    }

    // test of HList's add(int index, Object element) method

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Calls {@code add(-1, "A")} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddNegativeIndexThrowsIndexOutOfBoundsException() {
        stackTest.add(-1, "A");
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) throws IndexOutOfBoundsException for an index greater than size() (index == size() is allowed).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range for insertion (index &gt; size()).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code add(stackTest.size() + 2, "B")}, i.e. {@code add(3, "B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddIndexGreaterThanSizeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.add(stackTest.size() + 2, "B");
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) inserts the element at the given index and shifts the following ones to the right.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Insertion at index 0 (bottom of the stack), the case with the most shifted elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" (stack [A, B]), calls {@code add(0, "C")}, then reads {@code size()} and {@code get()} at indices 0, 1 and 2.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: [C, A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code size()} returns 3; {@code get(0)} returns "C", {@code get(1)} returns "A", {@code get(2)} returns "B".</li>
     * </ul>
     */
    @Test
    public void testAddIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.add(0, "C");
        assertEquals("Error: Size must be 3.", 3, stackTest.size());
        assertEquals("Error: Element C should be at index 0.", "C", stackTest.get(0));
        assertEquals("Error: Element A should be shifted to index 1.", "A", stackTest.get(1));
        assertEquals("Error: Element B should be shifted to index 2.", "B", stackTest.get(2));
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) with index == size() appends the element on top of the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the largest valid index for insertion.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code add(stackTest.size(), "B")} (i.e. {@code add(1, "B")}), then checks {@code get(1)}, {@code get(0)} and {@code peek()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code get(1)} returns "B", {@code get(0)} returns "A" and {@code peek()} returns "B".</li>
     * </ul>
     */
    @Test
    public void testAddIndexAtEndAppendElement() {
        stackTest.push("A");
        stackTest.add(stackTest.size(), "B");
        assertEquals("Error: Element B should be at index 1.", "B", stackTest.get(1));
        assertEquals("Error: Element A should be at index 0.", "A", stackTest.get(0));
        assertEquals("Error: Element B should be the top of the stack.", "B", stackTest.peek());
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) accepts a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code add(0, null)}, then checks {@code get(0)}, {@code get(1)} and {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [null, A].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns null, {@code get(1)} returns "A" and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testAddIndexNullElement() {
        stackTest.push("A");
        stackTest.add(0, null);
        assertNull("Error: Null must be the element at index 0.", stackTest.get(0));
        assertEquals("Error: The element at index 1 should be A.", "A", stackTest.get(1));
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that add(int, Object) inserts elements of different types at the correct positions.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements, inserted at index 0 and at an inner index.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1; calls {@code add(0, "Hi")} and checks that index 0 is "Hi" and index 1 is 1; calls {@code add(1, 'A')} and checks that index 1 is 'A' and index 2 is 1; finally reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: ["Hi", 'A', 1].</li>
     *   <li><strong>Expected Results:</strong> All the {@code get()} checks match the values above and {@code size()} returns 3.</li>
     * </ul>
     */
    @Test
    public void testAddIndexDifferentTypes() {
        stackTest.push(1);
        stackTest.add(0, "Hi"); // String now at 0, Integer shifted to 1
        assertEquals("Error: The element at index 0 should be Hi.", "Hi", stackTest.get(0));
        assertEquals("Error: The element at index 1 should be 1.", 1, stackTest.get(1));
        stackTest.add(1, 'A'); // Character now at 1, Integer shifted to 2
        assertEquals("Error: The element at index 1 should be A.", 'A', stackTest.get(1));
        assertEquals("Error: The element at index 2 should be 1", 1, stackTest.get(2));
        assertEquals("Error: Stack size must be 3.", 3, stackTest.size());
    }

    // test of HList's addAll(int index, HCollection c) method

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) throws NullPointerException when the collection is null.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Exception handling for a null argument (with a valid index).</li>
     *   <li><strong>Test Description:</strong> Calls {@code addAll(0, null)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.NullPointerException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testAddAllIndexNullCollection() {
        stackTest.addAll(0, null);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Creates an empty StackAdapter {@code collection} and calls {@code stackTest.addAll(-1, collection)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddAllNegativeIndexThrowsIndexOutOfBoundsException() {
        StackAdapter collection = new StackAdapter();
        stackTest.addAll(-1,  collection);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) throws IndexOutOfBoundsException for an index greater than size() (index == size() is allowed).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range for insertion (index &gt; size()). A non-empty collection is used, so the
     *   exception must come from the index check and not from an early return on an empty argument.</li>
     *   <li><strong>Test Description:</strong> Creates a StackAdapter {@code collection} containing the String "A" and calls {@code stackTest.addAll(stackTest.size() + 2, collection)}, i.e. index 2 on an empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testAddAllIndexGreaterThanSizeThrowsIndexOutOfBoundsException() {
        StackAdapter collection = new StackAdapter();
        collection.push("A");
        stackTest.addAll(stackTest.size() + 2, collection);
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) inserts all the elements at the given index, in iteration order, and shifts the following ones.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: insertion in the middle of the stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "D" into {@code stackTest}; creates a StackAdapter {@code collection} containing "B" and "C"; calls {@code stackTest.addAll(1, collection)}, then reads {@code size()} and {@code get()} at indices 0..3.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> {@code stackTest} contains four elements: [A, B, C, D].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns true, {@code size()} returns 4, indices 0..3 contain "A", "B", "C", "D".</li>
     * </ul>
     */
    @Test  // HList for differentiate with HCollection's addAll
    public void testHListAddAllRegularBehavior() {
        stackTest.push("A");
        stackTest.push("D");
        StackAdapter collection = new StackAdapter();
        collection.push("B");
        collection.push("C");
        boolean isChanged = stackTest.addAll(1, collection);
        assertTrue("Error: Must return true if the stack was modified.", isChanged);
        assertEquals("Error: Size must be 4.", 4, stackTest.size());
        assertEquals("Error: Element A should be at index 0.", "A", stackTest.get(0));
        assertEquals("Error: Element B should be at index 1.", "B", stackTest.get(1));
        assertEquals("Error: Element C should be at index 2.", "C", stackTest.get(2));
        assertEquals("Error: Element D should be at index 3.", "D", stackTest.get(3));
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) with an empty collection returns false and does not change the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: empty argument.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", creates an empty StackAdapter, calls {@code stackTest.addAll(0, collection)} and then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code addAll()} returns false and {@code size()} returns 1.</li>
     * </ul>
     */
    @Test
    public void testAddAllWithEmptyCollection() {
        stackTest.push("A");
        StackAdapter collection = new StackAdapter();
        boolean isChanged = stackTest.addAll(0, collection);
        assertFalse("Error: No changes were made so it should return false.", isChanged);
        assertEquals("Error: Size shouldn't alter.", 1, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) accepts a collection that contains null.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A"; creates a StackAdapter {@code collection} containing only {@code null}; calls {@code stackTest.addAll(0, collection)}, then checks {@code get(0)}, {@code get(1)} and {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [null, A].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns null, {@code get(1)} returns "A" and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testAddAllCollectionWithNullElement() {
        stackTest.push("A");
        StackAdapter collection = new StackAdapter();
        collection.push(null);
        stackTest.addAll(0, collection);
        assertNull("Error: Null must be the element at index 0.", stackTest.get(0));
        assertEquals("Error: The element at index 1 should be A.", "A", stackTest.get(1));
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that addAll(int, HCollection) inserts elements of different types keeping their order.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements inserted at index 0.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1; creates a StackAdapter {@code collection} containing the String "Hi" and the Character 'A'; calls {@code stackTest.addAll(0, collection)}, then checks {@code get()} at indices 0, 1, 2 and {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains three elements: ["Hi", 'A', 1].</li>
     *   <li><strong>Expected Results:</strong> {@code get(0)} returns "Hi", {@code get(1)} returns 'A', {@code get(2)} returns 1 and {@code size()} returns 3.</li>
     * </ul>
     */
    @Test
    public void testAddAllIndexDifferentTypes() {
        stackTest.push(1);
        StackAdapter collection = new StackAdapter();
        collection.push("Hi"); // String
        collection.push('A');  // Character
        stackTest.addAll(0, collection); 
        assertEquals("Error: The element at index 0 should be Hi.", "Hi", stackTest.get(0));
        assertEquals("Error: The element at index 1 should be A.", 'A', stackTest.get(1));
        assertEquals("Error: The element at index 2 should be 1.", 1, stackTest.get(2));
        assertEquals("Error: Stack size must be 3.", 3, stackTest.size());
    }

    // test of HList's remove(int index) method

    /**
     * <strong>Summary:</strong> Verifies that remove(int) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and calls {@code remove(-1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveIndexNegativeThrowsIndexOutOfBoundsException() {
        stackTest.push("A");
        stackTest.remove(-1);
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) throws IndexOutOfBoundsException for an index equal to size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range: index == size() is the first invalid value.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code remove(stackTest.size())}, i.e. {@code remove(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveIndexEqualToStackSizeThrowsIndexOutOfBoundsException(){
        stackTest.push("A");
        stackTest.remove(stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) throws IndexOutOfBoundsException for an index greater than size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Index well beyond the upper bound.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code remove(stackTest.size() + 2)}, i.e. {@code remove(3)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testRemoveIndexGreaterThanStackSizeThrowsIndexOutOfBoundException() {
        stackTest.push("A");
        stackTest.remove(stackTest.size() + 2);
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) removes the element at the given index, returns it and shifts the following ones to the left.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: removal of the middle element.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", calls {@code remove(1)} and checks the returned value, then reads {@code size()}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, C].</li>
     *   <li><strong>Expected Results:</strong> {@code remove(1)} returns "B", {@code size()} returns 2, {@code get(0)} returns "A" and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testRemoveIndexRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        Object removed = stackTest.remove(1);
        assertEquals("Error: remove() must return the deleted element.", "B", removed);
        assertEquals("Error: Size must be 2.", 2, stackTest.size());
        assertEquals("Error: Element A should be at index 0.", "A", stackTest.get(0));
        assertEquals("Error: Element C should be at index 1.", "C", stackTest.get(1));
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(0) on a one-element stack returns that element and empties the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: the only valid index of a one-element stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A", calls {@code remove(0)} and checks the returned value, then calls {@code isEmpty()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is empty.</li>
     *   <li><strong>Expected Results:</strong> {@code remove(0)} returns "A" and {@code isEmpty()} returns true.</li>
     * </ul>
     */
    @Test
    public void testRemoveIndexOnSingleElement() {
        stackTest.push("A");
        Object removed = stackTest.remove(0);
        assertEquals("Error: remove() must return the deleted element.", "A", removed);
        assertTrue("Error: The stack should be empty.", stackTest.isEmpty());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) removes and returns a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes "A", {@code null} and "B" (null at index 1), calls {@code remove(1)} and checks the returned value, then reads {@code size()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code remove(1)} returns null and {@code size()} returns 2.</li>
     * </ul>
     */
    @Test
    public void testRemoveIndexNullElement() {
        stackTest.push("A");
        stackTest.push(null);
        stackTest.push("B");
        assertNull("Error: remove() should return the deleted null element.", stackTest.remove(1));
        assertEquals("Error: Stack size must be 2.", 2, stackTest.size());
    }

    /**
     * <strong>Summary:</strong> Verifies that remove(int) works with elements of different types.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi" and the Character 'A', calls {@code remove(1)} and checks the returned value, then checks {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [1, 'A'].</li>
     *   <li><strong>Expected Results:</strong> {@code remove(1)} returns "Hi", {@code get(0)} returns 1 and {@code get(1)} returns 'A'.</li>
     * </ul>
     */
    @Test
    public void testRemoveIndexDifferentTypes() {
        stackTest.push(1); 
        stackTest.push("Hi"); 
        stackTest.push('A');
        Object removed = stackTest.remove(1);
        assertEquals("Error: The removed element should be Hi.", "Hi", removed);
        assertEquals("Error: The element 1 should be at index 0.", 1, stackTest.get(0));
        assertEquals("Error: The element A should be at index 1.", 'A', stackTest.get(1));
    }

    // test of HList's indexOf(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) returns -1 on an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code indexOf("A")} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testIndexOfOnEmptyStack() {
        assertEquals("Error: indexOf must return -1 on an empty stack.", -1, stackTest.indexOf("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) returns the index of the first occurrence when the element is duplicated.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicate elements: the lowest index must be returned.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integers 0, 1, ..., 9 and then the Integer 0 again (0 is at index 0 and at index 10), and calls {@code indexOf(0)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [0, 1, ..., 9, 0].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf(0)} returns 0.</li>
     * </ul>
     */
    @Test
    public void testIndexOfRegularBehavior() {
        for (int i = 0; i < 10; i ++)
        {
            stackTest.push(i);
        }
        stackTest.push(0);
        assertEquals("Error: indexOf should return the first occurrence of A.", 0, stackTest.indexOf(0));
    }

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) returns -1 for an element that is not in the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Missing element case.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and calls {@code indexOf("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testIndexOfMissingElement() {
        stackTest.push("A");
        assertEquals("Error: indexOf should return -1 if the element isn't in the stack.", -1, stackTest.indexOf("B"));
    }

    /**
     * <strong>Summary:</strong> Verifies that indexOf(null) finds a null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes "A", {@code null} and "B" and calls {@code indexOf(null)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, null, B].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf(null)} returns 1.</li>
     * </ul>
     */
    @Test
    public void testIndexOfNullElement() {
        stackTest.push("A");
        stackTest.push(null);
        stackTest.push("B");
        assertEquals("Error: indexOf should return the index of the null element.", 1, stackTest.indexOf(null));
    }

    /**
     * <strong>Summary:</strong> Verifies that indexOf(Object) finds elements of different types.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi" and the Character 'A', then calls {@code indexOf()} for each of them.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, "Hi", 'A'].</li>
     *   <li><strong>Expected Results:</strong> {@code indexOf(1)} returns 0, {@code indexOf("Hi")} returns 1 and {@code indexOf('A')} returns 2.</li>
     * </ul>
     */
    @Test
    public void testIndexOfDifferentTypes() {
        stackTest.push(1);
        stackTest.push("Hi");
        stackTest.push('A');
        assertEquals("Error: The element 1 should be at index 0.", 0, stackTest.indexOf(1));
        assertEquals("Error: The element Hi should be at index 1.", 1, stackTest.indexOf("Hi"));
        assertEquals("Error: The element A should be at index 2.", 2, stackTest.indexOf('A'));
    }
    
    // test of HList's lastIndexOf(Object o) method

    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(Object) returns -1 on an empty stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code lastIndexOf("A")} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfOnEmptyStack() {
        assertEquals("Error: lastIndexOf should return -1 on an empty stack.", -1, stackTest.lastIndexOf("A"));
    }
 
    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(Object) returns the index of the last occurrence when the element is duplicated.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Duplicate elements: the highest index must be returned.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "A" and "C" ("A" at indices 0 and 2) and calls {@code lastIndexOf("A")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, A, C].</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf("A")} returns 2.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("A");
        stackTest.push("C");
        assertEquals("Error: Must return the last occurrence, so must return index 2.", 2, stackTest.lastIndexOf("A"));
    }

    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(Object) returns -1 for an element that is not in the stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Missing element case.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and calls {@code lastIndexOf("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf()} returns -1.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfMissingElement() {
        stackTest.push("A");
        assertEquals("Error: lastIndexOf must return -1 if the element isn't in the stack.", -1, stackTest.lastIndexOf("B"));
    }

    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(null) returns the index of the last null element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling with duplicates.</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null}, "A" and {@code null} (null at indices 0 and 2) and calls {@code lastIndexOf(null)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [null, A, null].</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf(null)} returns 2.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfNullElement() {
        stackTest.push(null);
        stackTest.push("A");
        stackTest.push(null);
        assertEquals("Error: Must return the last occurrence of null, so must return index 2.", 2, stackTest.lastIndexOf(null));
    }

    /**
     * <strong>Summary:</strong> Verifies that lastIndexOf(Object) finds elements of different types, returning the highest index for duplicates.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements, one of which is duplicated.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi", the Character 'A' and the String "Hi" again, then calls {@code lastIndexOf()} with "Hi", 1 and 'A'.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, "Hi", 'A', "Hi"].</li>
     *   <li><strong>Expected Results:</strong> {@code lastIndexOf("Hi")} returns 3, {@code lastIndexOf(1)} returns 0 and {@code lastIndexOf('A')} returns 2.</li>
     * </ul>
     */
    @Test
    public void testLastIndexOfDifferentTypes() {
        stackTest.push(1);
        stackTest.push("Hi");
        stackTest.push('A');
        stackTest.push("Hi"); 
        assertEquals("Error: The last occourrence of Hi should be at index 3.", 3, stackTest.lastIndexOf("Hi"));
        assertEquals("Error: The element 1 should be at index 0.", 0, stackTest.lastIndexOf(1));
        assertEquals("Error: The element A should be at index 2.", 2, stackTest.lastIndexOf('A'));
    }

    // test of HList's listIterator() method

    /**
     * <strong>Summary:</strong> Verifies that listIterator() on an empty stack returns a valid HListIterator with no elements in either direction.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Empty collection.</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator()} on the empty stack, checks that the result is not null and calls {@code hasNext()} and {@code hasPrevious()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> The HListIterator is not null; {@code hasNext()} and {@code hasPrevious()} return false.</li>
     * </ul>
     */
    @Test
    public void testListIteratorOnEmptyStack() {
        HListIterator iterator = stackTest.listIterator();
        assertNotNull("Error: The HListIterator mustn't be null.", iterator);
        assertFalse("Error: hasNext() must be false on an empty stack.", iterator.hasNext());
        assertFalse("Error: hasPrevious() must be false on an empty stack.", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator() starts before the first element (index 0).
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Initial cursor position on a non-empty stack.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", calls {@code listIterator()} and then calls {@code nextIndex()} and {@code hasPrevious()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 0 and {@code hasPrevious()} returns false.</li>
     * </ul>
     */
    @Test
    public void testListIteratorRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: The HListIterator must start at index 0.", 0, iterator.nextIndex());
        assertFalse("Error: Shouldn't have previous elements at the start of the list.", iterator.hasPrevious());
    }

    /**
     * <strong>Summary:</strong> Verifies that two HListIterators obtained from the same stack have independent cursors.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Independence of multiple iterators on the same list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates two HListIterators, calls {@code next()} only on the first one, then calls {@code nextIndex()} on both.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 1 for the first HListIterator and 0 for the second one.</li>
     * </ul>
     */
    @Test
    public void testListIteratorSeparateInstances() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator first = stackTest.listIterator();
        HListIterator second = stackTest.listIterator();
        first.next();
        assertEquals("Error: The first iterator should be at index 1.", 1, first.nextIndex());
        assertEquals("Error: The second iterator should still be at index 0.", 0, second.nextIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that the HListIterator returned by listIterator() traverses the elements in proper sequence.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Forward traversal order (from index 0 to the top).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", creates an HListIterator and calls {@code next()} twice.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> The first {@code next()} returns "A", the second returns "B".</li>
     * </ul>
     */
    @Test
    public void testListIteratorCorrectElements() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: First element must be A.", "A", iterator.next());
        assertEquals("Error: Second element must be B.", "B", iterator.next());
    }

    /**
     * <strong>Summary:</strong> Verifies that the HListIterator returned by listIterator() returns a null element correctly.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling.</li>
     *   <li><strong>Test Description:</strong> Pushes {@code null}, creates an HListIterator and calls {@code next()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [null].</li>
     *   <li><strong>Expected Results:</strong> {@code next()} returns null.</li>
     * </ul>
     */
    @Test
    public void testListIteratorHandlesNullElement() {
        stackTest.push(null);
        HListIterator iterator = stackTest.listIterator();
        assertNull("Error: Must return the null element.", iterator.next());
    }

    /**
     * <strong>Summary:</strong> Verifies that the HListIterator returned by listIterator() traverses elements of different types.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi" and the Character 'A', creates an HListIterator and calls {@code next()} three times.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, "Hi", 'A'].</li>
     *   <li><strong>Expected Results:</strong> {@code next()} returns 1, "Hi" and 'A' in this order.</li>
     * </ul>
     */
    @Test
    public void testListIteratorHandlesDifferentTypes() {
        stackTest.push(1);
        stackTest.push("Hi");
        stackTest.push('A');
        HListIterator iterator = stackTest.listIterator();
        assertEquals("Error: First element must be 1.", 1, iterator.next());
        assertEquals("Error: Second element must be Hi.", "Hi", iterator.next());
        assertEquals("Error: Third element must be A.", 'A', iterator.next());
    }

    // test of HList's listIterator(int index) method

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) throws IndexOutOfBoundsException for a negative index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of the valid index range (index &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Calls {@code listIterator(-1)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testListIteratorIndexNegative() {
        stackTest.listIterator(-1);
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) throws IndexOutOfBoundsException for an index greater than size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of the valid index range (index &gt; size(); index == size() is allowed).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code listIterator(stackTest.size() + 1)}, i.e. {@code listIterator(2)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testListIteratorIndexTooLarge() {
        stackTest.push("A");
        stackTest.listIterator(stackTest.size() + 1);
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator(int) places the cursor at the requested index.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: starting index in the middle of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", calls {@code listIterator(1)}, then calls {@code nextIndex()} and {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C]; the cursor is back at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code nextIndex()} returns 1 and {@code previous()} returns "A".</li>
     * </ul>
     */
    @Test
    public void testHListListIteratorRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HListIterator iterator = stackTest.listIterator(1);
        assertEquals("Error: Must return 1.", 1, iterator.nextIndex());
        assertEquals("Error: Must return the element at index 0.", "A", iterator.previous());
    }

    /**
     * <strong>Summary:</strong> Verifies that listIterator(size()) creates an HListIterator positioned after the last element.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Boundary case: index equal to size().</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B", calls {@code listIterator(stackTest.size())} (i.e. index 2), then calls {@code hasNext()}, {@code hasPrevious()} and {@code previousIndex()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> {@code hasNext()} returns false, {@code hasPrevious()} returns true and {@code previousIndex()} returns 1 (size() - 1).</li>
     * </ul>
     */
    @Test
    public void testListIteratorIndexAtEnd() {
        stackTest.push("A");
        stackTest.push("B");
        HListIterator iterator = stackTest.listIterator(stackTest.size());
        assertFalse("Error: Must be false when positioned at the end.", iterator.hasNext());
        assertTrue("Error: Must be true when positioned at the end.", iterator.hasPrevious());
        assertEquals("Error: The previous index must be size() - 1.", stackTest.size() - 1, iterator.previousIndex());
    }

    /**
     * <strong>Summary:</strong> Verifies that an HListIterator created with listIterator(int) returns a null element when moving backward.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Null handling during backward traversal.</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" and {@code null}, calls {@code listIterator(stackTest.size())} (i.e. index 2) and then calls {@code previous()}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, null]; the cursor is at index 1.</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns null.</li>
     * </ul>
     */
    @Test
    public void testListIteratorIndexHandlesNullElement() {
        stackTest.push("A");
        stackTest.push(null);
        HListIterator iterator = stackTest.listIterator(stackTest.size());
        assertNull("Error: The HListIterator must safely return the null element.", iterator.previous());
    }

    /**
     * <strong>Summary:</strong> Verifies that an HListIterator created with listIterator(int) traverses backward elements of different types.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Heterogeneous elements, backward traversal from the end of the list.</li>
     *   <li><strong>Test Description:</strong> Pushes the Integer 1, the String "Hi" and the Character 'A', calls {@code listIterator(stackTest.size())} (i.e. index 3) and calls {@code previous()} three times.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [1, "Hi", 'A']; the cursor is at index 0.</li>
     *   <li><strong>Expected Results:</strong> {@code previous()} returns 'A', "Hi" and 1 in this order.</li>
     * </ul>
     */
    @Test
    public void testListIteratorIndexHandlesDifferentTypes() {
        stackTest.push(1);
        stackTest.push("Hi");
        stackTest.push('A');
        HListIterator iterator = stackTest.listIterator(stackTest.size());
        assertEquals("Error: Must return A.", 'A', iterator.previous());
        assertEquals("Error: Must return Hi.", "Hi", iterator.previous());
        assertEquals("Error: Must return 1.", 1, iterator.previous());
    }

    // test of HList's subList(int fromIndex, int toIndex) method

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) throws IndexOutOfBoundsException for a negative fromIndex.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Lower bound of fromIndex (fromIndex &lt; 0).</li>
     *   <li><strong>Test Description:</strong> Calls {@code subList(-1, 0)} on the empty stack.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is still empty.</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListNegativeFromIndex() {
        stackTest.subList(-1, 0);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) throws IndexOutOfBoundsException when toIndex is greater than size().
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Upper bound of toIndex (toIndex &gt; size()).</li>
     *   <li><strong>Test Description:</strong> Pushes the String "A" (size 1) and calls {@code subList(0, stackTest.size() + 1)}, i.e. {@code subList(0, 2)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListToIndexTooLarge() {
        stackTest.push("A");
        stackTest.subList(0, stackTest.size() + 1);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) throws IndexOutOfBoundsException when fromIndex is greater than toIndex.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Inverted range (fromIndex &gt; toIndex) with both indices individually valid.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A" and "B" and calls {@code subList(1, 0)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B].</li>
     *   <li><strong>Expected Results:</strong> A {@code java.lang.IndexOutOfBoundsException} is thrown.</li>
     * </ul>
     */
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSubListInvalidRange() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.subList(1, 0);
    }

    /**
     * <strong>Summary:</strong> Verifies that subList(int, int) returns a view with the correct size and elements.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Standard case: a range in the middle of the stack (fromIndex inclusive, toIndex exclusive).</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B", "C" and "D", calls {@code subList(1, 3)}, then reads the subList {@code size()}, {@code get(0)} and {@code get(1)}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack is unchanged: [A, B, C, D]; the subList is the view [B, C].</li>
     *   <li><strong>Expected Results:</strong> The subList {@code size()} returns 2, {@code get(0)} returns "B" and {@code get(1)} returns "C".</li>
     * </ul>
     */
    @Test
    public void testSubListRegularBehavior() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        stackTest.push("D");
        HList listSub = stackTest.subList(1, 3);
        assertEquals("Error: SubList size should be exactly 2.", 2, listSub.size());
        assertEquals("Error: Element at index 0 of subList should be B.", "B", listSub.get(0));
        assertEquals("Error: Element at index 1 of subList should be C.", "C", listSub.get(1));
    }

    /**
     * <strong>Summary:</strong> Verifies that a structural modification made through the subList is reflected on the parent stack.
     * 
     * <ul>
     *   <li><strong>Test Case Design:</strong> Backing between the subList view and the parent stack, using clear() on the view.</li>
     *   <li><strong>Test Description:</strong> Pushes the Strings "A", "B" and "C", obtains {@code subList(1, 2)} (the view [B]) and calls {@code clear()} on it; then checks the subList {@code isEmpty()} and the parent {@code size()}, {@code get(1)} and {@code contains("B")}.</li>
     *   <li><strong>Pre-Condition:</strong> {@code stackTest} is a new, empty StackAdapter created by setUp(); no element has been inserted yet.</li>
     *   <li><strong>Post-Condition:</strong> The stack contains two elements: [A, C]; the subList is empty.</li>
     *   <li><strong>Expected Results:</strong> The subList {@code isEmpty()} returns true; the parent {@code size()} returns 2, {@code get(1)} returns "C" and {@code contains("B")} returns false.</li>
     * </ul>
     */
    @Test
    public void testSubListModificationsReflectOnParent() {
        stackTest.push("A");
        stackTest.push("B");
        stackTest.push("C");
        HList listSub = stackTest.subList(1, 2);
        listSub.clear(); 
        assertTrue("Error: SubList should be empty after clear().", listSub.isEmpty());
        assertEquals("Error: Stack size should now be 2.", 2, stackTest.size());
        assertEquals("Error: Stack element at index 1 should now be C.", "C", stackTest.get(1));
        assertFalse("Error: B shouldn't still be in the stack.", stackTest.contains("B"));
    }
}