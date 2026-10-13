package myAdapter;

// Personal decision to implement a separate class for this exception to make it compatible
// with the CLDC 1.1 functionality

/**
 * Signals that a method has been invoked at an illegal or inappropriate time. In other words, the Java environment or Java application is not in an appropriate state for the requested operation.
 */
public class IllegalStateException extends RuntimeException {

    /**
     * Constructs an IllegalStateException with no detail message. A detail message is a String that describes this particular exception.
     */
    public IllegalStateException() {
        super(); // calls the super-class constructor
    }

    /**
     * Constructs an IllegalStateException with the specified detail message. A detail message is a String that describes this particular exception.
     * 
     * @param s the String that contains a detailed message
     */
    public IllegalStateException(String s) {
        super(s);
    }
}