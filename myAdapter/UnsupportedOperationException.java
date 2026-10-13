package myAdapter;

// Personal decision to implement a separate class for this exception to make it compatible
// with the CLDC 1.1 functionality

/**
 * Thrown to indicate that the requested operation is not supported.
 */
public class UnsupportedOperationException extends RuntimeException {

    /**
     * Constructs an UnsupportedOperationException with no detail message.
     */
    public UnsupportedOperationException() {
        super(); // calls the super-class constructor
    }

    /**
     * Constructs an UnsupportedOperationException with the specified detail message.
     * 
     * @param message the detail message
     */
    public UnsupportedOperationException(String message) {
        super(message);
    }
}