package org.example.lib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


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
        System.out.print(text);
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

    /**
     * Format to 00-00 type of clock
     *
     * @param clock return any 0-9 type of string otherwise it's going to fail
     * @return String with calculated time to second be always +1
     */
    public static String clockFormat(String clock) {
        return String.format("%s-%02d", clock, Integer.parseInt(clock) + 1);
    }

    /**
     * Method that find best 4 consecutive hours
     *
     * @param list takes any list where is K String and V Integer
     * @return List<String> that contains best 4 consecutive hours
     */
    public static List<String> getBestHours(List<Map.Entry<String, Integer>> list) {
        int minTotalPrice = Integer.MAX_VALUE;

        List<String> bestHours = new ArrayList<>();

        for (int i = 0; i <= list.size() - 4; i++) {
            int total = 0;

            List<String> consecutiveHours = new ArrayList<>();

            for (int j = i; j < i + 4; j++) {
                Map.Entry<String, Integer> entry = list.get(j);
                total += entry.getValue();
                consecutiveHours.add(entry.getKey());
            }

            if (total < minTotalPrice) {
                minTotalPrice = total;
                bestHours = new ArrayList<>(consecutiveHours);
            }
        }
        return bestHours;
    }


}
