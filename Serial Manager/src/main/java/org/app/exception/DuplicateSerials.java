package org.app.exception;

import org.app.util.ExtractList;

public class DuplicateSerials extends Exception {

    public DuplicateSerials(){
        super("Sono presenti seriali duplicati");
    }
}
