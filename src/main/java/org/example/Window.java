package org.example;

import org.example.lib.utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
     *
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
        for (int i = 0; i < 3; i++) {
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
        AtomicInteger temp = new AtomicInteger();

        // Min
        int min = TREE_MAP.values().stream().min(Integer::compareTo).orElse(0);
        Utils.log("Min: " + min + "\n");

        // Max
        int max = TREE_MAP.values().stream().max(Integer::compareTo).orElse(0);
        Utils.log("Max: " + max + "\n");

        // Average
        TREE_MAP.forEach((s, integer) -> temp.addAndGet(integer));
        Utils.log("Average: " + (temp.get() / TREE_MAP.size()) + "\n");

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
            Utils.format("%s: %d Öre \n", entry.getKey(), entry.getValue());

        menu();
    }

    // TODO: create logic
    private static void bestChargeTime() {
    }

    /**
     * Optional method for showing console graph based on data in HashMap
     *
     * @implNote Only use After calling a menu() method as it where you load data first
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
            if (yValue == max || yValue == min) System.out.printf("%3d| ", yValue);
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
