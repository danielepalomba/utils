package org.app.util;

import java.util.*;

public class DuplicateFinder {

    public static int count = 0;

    public static Set<String> findDuplicates(String text) {
        List<String> serials = new ArrayList<>();
        String[] serialsArray = text.split("\n");

        count = 0;
        for (String s : serialsArray) {
            if(!s.isEmpty()){ //not include empty line
                if(!s.startsWith("#")){ //not include comments
                    serials.add(s.trim());
                    count++;
                }
            }
        }

        return findDuplicates(serials);
    }

    public static Set<String> findDuplicates(List<String> serials) {
        Set<String> duplicates = new HashSet<>();
        Set<String> uniqueValues = new HashSet<>();

        for(String s : serials) {
            if(!s.isEmpty()){ //not include empty line
                if(!s.startsWith("#")){ //not include comments
                    if(!uniqueValues.add(s))
                        duplicates.add(s);
                }
            }
        }
        return duplicates;
    }
}
