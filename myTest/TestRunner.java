package myTest;

import org.junit.runner.*;
import org.junit.runner.notification.Failure; // to import the specific Failure class
import java.util.*;

/**
 * Main class to execute the JUnit test suites.
 */
public class TestRunner {
    /**
     * Main method that runs all tests and prints the results.
     * @param args command line arguments.
     */
    public static void main(String args[]){
        // executes the test classes and saves the outcome in the Result object
        Result res = JUnitCore.runClasses(StackAdapterTest.class, StackIteratorTest.class, StackListIteratorTest.class, StackSubListTest.class);

        // Print the results
        System.out.println("Numero complessivo test eseguiti: " + res.getRunCount());
        System.out.println("Test falliti: " + res.getFailureCount());
        System.out.println("Tempo di esecuzione totale: " + res.getRunTime() + " ms ");
        
        // Prints any failed tests
        if (res.getFailureCount() > 0){
            List<Failure> fail = res.getFailures();
            ListIterator<Failure> iter_fail = fail.listIterator(); // iterator to scan the failure list 
            while (iter_fail.hasNext()) {
                System.out.println(iter_fail.next());
            }
        }

        // Print the final result
        if(res.wasSuccessful()){
            System.out.println("Tutti i test sono passati con successo");
        } else {
            System.out.println("Ci sono dei test che sono falliti");
        }
    }    
}