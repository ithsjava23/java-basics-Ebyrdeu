package org.example.lib.utils;

import java.util.Scanner;


/**
 * The Utils class provides some useful methods to reduce repeated code
 */
public class Utils {

    /**
     * Method that simplifies scanner method for user input
     *
     * @return text always returned  in lowerCase
     */
    public static String prompt() {
        return new Scanner(System.in).nextLine().toLowerCase();
    }


    /**
     * Only purpose is make it act like a char type when is empty
     *
     * @param text accepts any String method if not empty
     */

    public static void log(String text) {
        if (text.isBlank()) throw new RuntimeException("Empty String");

        System.out.printf(text);
    }

    /**
     * Work same as printf but with error handling on empty format and arguments
     *
     * @param format a format string acts as printf format
     * @param args   an args Object acts as printf format
     */
    public static void format(String format, Object... args) {
        if (format.isBlank()) throw new RuntimeException("Empty Format");
        if (args.length == 0) throw new RuntimeException("Empty Arguments");
        System.out.printf((format) + "%n", args);
    }

}
