package com.debuggeando_ideas.best_travel.util.exceptions;

public class UsernameDocumentNotFoundException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *                {@link #getMessage()} method.
     */
    public UsernameDocumentNotFoundException(String message) {
        super(message);
    }
}
