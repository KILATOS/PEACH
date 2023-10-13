package org.peach.app.exceptions;

public class CannotDeleteUserException extends RuntimeException{
    public CannotDeleteUserException() {
        super();
    }

    public CannotDeleteUserException(String message) {
        super(message);
    }
}
