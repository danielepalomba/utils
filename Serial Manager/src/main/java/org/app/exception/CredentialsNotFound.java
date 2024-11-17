package org.app.exception;

public class CredentialsNotFound extends Exception{
    public CredentialsNotFound() {
        super("Le credenziali non sono inserite!");
    }
}
