package org.tasktracker.exceptions;

public class DuplicateTaskIdException extends RuntimeException {
    public DuplicateTaskIdException(String message) {
        super(message);
    }

}
