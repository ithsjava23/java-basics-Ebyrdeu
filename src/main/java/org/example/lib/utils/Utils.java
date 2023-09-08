package org.example.lib.utils;

import java.util.*;


/**
 * The Utils class provides some useful methods to reduce repeated code.
 */
public class Utils {
    private Utils() {
    }

    /**
     * Logs a message and throws an exception if the message is empty.
     *
     * @param text The message to log.
     * @throws RuntimeException if the text is empty.
     */
    public static void log(String text) {
        if (text.isEmpty()) throw new RuntimeException("Empty String");
        System.out.print(text);
    }

    /**
     * Formats and prints a message with error handling for empty format or arguments.
     *
     * @param format The format string (acts like printf format).
     * @param args   The arguments (acts like printf arguments).
     * @throws RuntimeException if the format or arguments are empty.
     */
    public static void format(String format, Object... args) {
        if (format.isEmpty()) throw new IllegalArgumentException("Empty Format");
        if (args.length == 0) throw new IllegalArgumentException("Empty Arguments");

        try {
            System.out.printf(format + "\n", args);
        } catch (IllegalFormatException e) {
            throw new IllegalArgumentException("Invalid Format: " + e.getMessage());
        }

    }

    /**
     * Formats a clock string to the "00-00" type.
     *
     * @param clock The input clock string (0-9).
     * @return The formatted clock string with seconds always +1.
     */
    public static String clockFormat(String clock) {
        return String.format("%s-%02d", clock, Integer.parseInt(clock) + 1);
    }

    /**
     * Finds the best 4 consecutive hours in terms of price.
     *
     * @param list A list of entries where K is a String and V is an Integer.
     * @return A list of strings containing the best 4 consecutive hours.
     */
    public static List<String> getBestHours(List<Map.Entry<String, Integer>> list) {
        int minTotalPrice = Integer.MAX_VALUE;

        // Initialize a list to store the best 4 consecutive hours.
        List<String> bestHours = new ArrayList<>();

        // Iterate through the list, considering each possible set of 4 consecutive hours.
        for (int i = 0; i <= list.size() - 4; i++) {
            int total = 0;

            List<String> consecutiveHours = new ArrayList<>();

            // Iterate through the current set of 4 consecutive hours.
            for (int j = i; j < i + 4; j++) {
                Map.Entry<String, Integer> entry = list.get(j);
                total += entry.getValue();
                consecutiveHours.add(entry.getKey());
            }

            // Check if the total price for the current set of hours is lower than the minimum total price found so far.
            if (total < minTotalPrice) {
                minTotalPrice = total;
                bestHours = new ArrayList<>(consecutiveHours);
            }
        }
        return bestHours;
    }


}
