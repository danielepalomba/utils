package org.app.exception;

public class NotEmptyArea extends Exception{
    public NotEmptyArea(){
        super("Attenzione! Se esci i seriali presenti non verranno salvati.");
    }
}
