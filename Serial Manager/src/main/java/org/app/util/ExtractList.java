package org.app.util;

import org.app.exception.DuplicateSerials;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExtractList {

    public static List<String> extractSerialsToSave(String text) throws Exception{
        List<String> serials = new ArrayList<>();
        String[] serialsArray = text.split("\n");

        for (String s : serialsArray) {
            serials.add(s.trim());
        }

        Set<String> result = DuplicateFinder.findDuplicates(serials);

        if(result.isEmpty()){
            return serials;
        }else{
          throw new DuplicateSerials();
        }
    }
}
