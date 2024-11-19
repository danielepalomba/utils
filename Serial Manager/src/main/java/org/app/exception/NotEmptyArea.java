package org.app.exception;

public class NotEmptyArea extends Exception{
    public NotEmptyArea(){
        super("Attenzione! Se esci i seriali presenti andranno persi definitivamente!");
    }
}
