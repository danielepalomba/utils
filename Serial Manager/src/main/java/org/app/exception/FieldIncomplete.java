package org.app.exception;

public class FieldIncomplete extends RuntimeException {
    public FieldIncomplete() {
        super("Compilare i campi necessari!");
    }
}
