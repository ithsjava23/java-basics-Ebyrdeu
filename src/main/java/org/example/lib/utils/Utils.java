package org.example.lib.utils;

import java.util.*;


/**
 * The Utils class provides some useful methods to reduce repeated code
 */
public class Utils {
    private Utils() {
    }

    /**
     * Method that simplifies scanner method for user input
     *
     * @return String always returned  in lowerCase
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
     * Method that combines both log and prompt method
     *
     * @return String value from prompt
     * @implNote Use When you need both logging and prompting to user
     */
    public static String logPrompt(String logText) {
        log(logText);
        return prompt();
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
