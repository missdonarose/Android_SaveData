package com.example.dabduljalal.savingdataexample;

import java.io.File;

/**
 * Created by DAbduljalal on 10/11/2015.
 */
public class TestFile {

    public static void main(String[] args) {
        String mydata = "test 12";
        char[] dataInChar = new char[10];
        mydata.getChars(0, mydata.length(), dataInChar, 0);
        System.out.println(dataInChar);

        char[] data = {'a','b'};
        String finalData="";
        System.out.println(data);
        System.out.println(data.toString());
        System.out.println(new String(data)+" My new string");
        System.out.println(String.valueOf(data));
    }

}
