package hw03.testing.exceptions;

public class TestFailureException extends Exception {

    public TestFailureException (String message) {
        super(message);
    }
}