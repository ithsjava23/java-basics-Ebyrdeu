package org.example;

import org.example.lib.utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Window class provides a console-based GUI for handling electricity price data.
 */
public class Window {
    private final static TreeMap<String, Integer> TREE_MAP = new TreeMap<>();
    private final static String MENU = """
            Elpriser
            ========
            1. Inmatning
            2. Min, Max och Medel
            3. Sortera
            4. Bästa Laddningstid (4h)
            5. Visualisering
            e. Avsluta
            """;

    private Window() {
    }

    /**
     * Main method to start the application.
     */
    public static void menu() {
        menuOptions(Utils.logPrompt(MENU));
    }

    /**
     * Process the user's menu choice.
     *
     * @param opt The user's menu choice.
     */
    private static void menuOptions(String opt) {
        switch (opt) {
            case "1" -> input();
            case "2" -> minMaxAverage();
            case "3" -> sort();
            case "4" -> bestChargeTime();
            case "5" -> showGraph();
            case "e" -> Utils.log("Exiting the program...");
            default -> menu();
        }
    }

    /**
     * Method for user input of electricity prices for each hour.
     */
    private static void input() {
        for (int i = 0; i < 24; i++) {
            Utils.format("%02d-%02d o'clock price: ", i, i + 1);

            // Parse user input as an integer and store it in the TreeMap.
            TREE_MAP.put(String.format("%02d", i), Integer.parseInt(Utils.prompt()));
        }
        menu();
    }

    /**
     * Calculate and display the minimum, maximum, and average electricity prices.
     */
    private static void minMaxAverage() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(TREE_MAP.entrySet());
        AtomicReference<Double> averagePrice = new AtomicReference<>(0.0);

        // Calculate and display the minimum price.
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Utils.format("Lägsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Calculate and display the maximum price.
        list.sort(Map.Entry.comparingByValue());
        Utils.format("Högsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Calculate and display the average price.
        TREE_MAP.forEach((s, integer) -> averagePrice.updateAndGet(v -> v + integer));
        Utils.format("Medelpris: %.2f öre/kWh", (averagePrice.get() / TREE_MAP.size()));

        menu();
    }

    /**
     * Sort and display electricity prices in descending order.
     */
    private static void sort() {
        // Convert to List<Map.Entry<K, V>> for easy sort method
        List<Map.Entry<String, Integer>> list = new ArrayList<>(TREE_MAP.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : list)
            Utils.format("%s %02d Öre", Utils.clockFormat(entry.getKey()), entry.getValue());

        menu();
    }

    /**
     * Find and display the best charging times based on the lowest prices.
     */
    private static void bestChargeTime() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(TREE_MAP.entrySet());
        List<String> bestHours = Utils.getBestHours(list);

        Utils.format("Best Hours: %s", bestHours.get(0));

        list.sort(Map.Entry.comparingByValue());

        double averagePrice = 0;
        for (int i = 0; i < 4; i++)
            averagePrice += list.get(i).getValue();

        Utils.format("Medelpris:  %.1f öre/kWh", averagePrice / 4.0);

        menu();
    }

    /**
     * Display a graphical representation of electricity prices.
     */
    public static void showGraph() {
        int max = TREE_MAP.values().stream().max(Integer::compareTo).orElse(0);
        int min = TREE_MAP.values().stream().min(Integer::compareTo).orElse(0);

        int numSteps = 5;

        // Calculate the size of each step on the y-axis.
        int stepSize = (max - min) / numSteps;

        // Check Max value Map have to see what step to use for rendering y-axis/columns
        for (int i = numSteps; i >= 0; i--) {

            // Calculate the y-axis value for the current step
            int yValue = min + i * stepSize;

            // Render the y-axis/columns and labels
            if (yValue + 10 >= max || yValue == min) System.out.printf("%3d| ", yValue);
            else Utils.log("   | ");

            // Render bars depending on value we have
            TREE_MAP.forEach((s, integer) -> {
                if (integer >= yValue) Utils.log(" * ");
                else System.out.print("   ");
            });

            // Print new line
            System.out.println();
        }

        // Render the x-axis/rows
        Utils.log("   |------------------------------------------------------------------------ \n");
        Utils.log("   | ");
        TREE_MAP.forEach((s, integer) -> Utils.log(s + " "));

        System.out.println();

        menu();
    }

}
