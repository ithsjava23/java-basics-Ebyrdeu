package org.example;

import org.example.lib.utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Window class provide Console GUI
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
     * Main Method to start the app
     */
    public static void menu() {
        menuOptions(Utils.logPrompt(MENU));
    }


    /**
     * @param opt takes value from MENU constant
     */
    private static void menuOptions(String opt) {
        switch (opt) {
            case "1" -> input();
            case "2" -> minMaxAverage();
            case "3" -> sort();
            case "4" -> bestChargeTime();
            case "5" -> showGraph();
            case "e" -> exit();
            default -> menu();
        }
    }

    /**
     * First method that user need to choose when app is started as HashMap at moment is empty
     */
    private static void input() {
        for (int i = 0; i < 24; i++) {
            Utils.format("%02d-%02d o'clock price: ", i, i + 1);

            /*
             * arg 1 -  Format K to 00 as string
             * Parse to integer necessary as prompt returns @type String
             * */
            TREE_MAP.put(String.format("%02d", i), Integer.parseInt(Utils.prompt()));
        }

        if (Utils.logPrompt("Show inputs before continue y/n \n").equals("y"))
            TREE_MAP.forEach((i, o) -> Utils.format("%s: %d Öre \n", i, o));

        menu();
    }

    /**
     * Method that allow to show Min Max and Average value on user provided inputs
     */
    private static void minMaxAverage() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(TREE_MAP.entrySet());
        AtomicReference<Double> averagePrice = new AtomicReference<>(0.0);

        // Min
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Utils.format("Lägsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Max
        list.sort(Map.Entry.comparingByValue());
        Utils.format("Högsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Average
        TREE_MAP.forEach((s, integer) -> averagePrice.updateAndGet(v -> v + integer));
        Utils.format("Medelpris: %.2f öre/kWh", (averagePrice.get() / TREE_MAP.size()));

        menu();
    }

    /**
     * Sorting a List based on user desire
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
     * Method that show to user best cheapest prices
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
     * Method for showing console graph based on data in HashMap
     */
    public static void showGraph() {
        int max = TREE_MAP.values().stream().max(Integer::compareTo).orElse(0);
        int min = TREE_MAP.values().stream().min(Integer::compareTo).orElse(0);

        int numSteps = 5;

        // Calculate the size of each step on the y-axis
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

        System.out.println("\n");
        menu();
    }

    /**
     * Method to exit app
     */
    private static void exit() {
        if (Utils.logPrompt("Are you sure you want to exit a program y/n").equals("y")) Utils.log("Exited");
        else menu();
    }


}
