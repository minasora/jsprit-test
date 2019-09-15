package com.graphhopper.jsprit.examples;

import java.util.ArrayList;

public class test12 {
    public static void main(String args[]) {
        ArrayList<String> listFileName = new ArrayList<String>();
        SolomonExample.getAllFileName("D:\\java learn\\jsprit-master\\input", listFileName);
        for (String name : listFileName) {
            System.out.println(name);
        }
    }
}
