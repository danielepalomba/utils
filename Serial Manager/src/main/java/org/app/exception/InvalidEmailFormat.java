package org.app.exception;

public class InvalidEmailFormat extends Exception {
    public InvalidEmailFormat() {
        super("Il formato email non è valido");
    }
}
