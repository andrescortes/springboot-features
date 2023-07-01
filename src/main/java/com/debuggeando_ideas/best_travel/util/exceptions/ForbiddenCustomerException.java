package com.debuggeando_ideas.best_travel.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public ForbiddenCustomerException() {
        super("This customer is blocked");
    }
}
