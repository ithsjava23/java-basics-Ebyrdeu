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

    /**
     * Main Method to start the app
     *
     * @return Window class if user want to use optional Method showGraph()
     */
    public Window menu() {
        menuOptions(Utils.logPrompt(MENU));
        return this;
    }

    /**
     * Optional method for showing console graph based on data in HashMap
     *
     * @implNote Only use After calling a menu() method as it where you load data first
     */
    // TODO: Optimize min value graph
    public void showGraph() {
        int max = TREE_MAP.values().stream().max(Integer::compareTo).orElse(0);
        int min = TREE_MAP.values().stream().min(Integer::compareTo).orElse(0);

        // Check Max value Map have to see what step to use for rendering y-axis/columns
        int stepSize = (max >= 100) ? 100 : (max >= 10) ? 10 : 1;

        for (int i = max; i >= stepSize; i -= stepSize) {
            // Render the y-axis/columns
            if (max == i) System.out.printf("%3d| ", i);
            else System.out.print("   | ");

            // Render bars depending on value we have
            int finalI = i;
            TREE_MAP.forEach((s, integer) -> {
                if (integer >= finalI) System.out.print(" * ");
                else System.out.print("   ");
            });
            // Print new line
            System.out.println();
        }

        // Check Min value Map rendering y-axis/columns
        System.out.printf("%3d| ", min);
        TREE_MAP.forEach((s, integer) -> {
            if (integer >= min) System.out.print(" * ");
            else System.out.print("   ");
        });
        System.out.println();

        // Render the x-axis/rows
        Utils.log("   |------------------------------------------------------------------------ \n");
        Utils.log("   | ");
        TREE_MAP.forEach((s, integer) -> Utils.log(s + " "));

        menu();
    }

    /**
     * @param opt takes value from MENU constant
     */
    private void menuOptions(String opt) {
        switch (opt) {
            case "1" -> input();
            case "2" -> minMaxAverage();
            case "3" -> sort();
            case "4" -> Utils.log("Bästa Laddningstid");
            case "5" -> showGraph();
            case "e" -> exit();
            default -> menu();
        }
    }

    /**
     * First method that user need to choose when app is started as HashMap at moment is empty
     */
    private void input() {
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
     * Method that allow to show Min Max and Avrage value on user provided inputs
     */
    private void minMaxAverage() {
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
    private void sort() {
        // Convert to List<Map.Entry<K, V>> for easy sort method
        List<Map.Entry<String, Integer>> list = new ArrayList<>(TREE_MAP.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : list)
            Utils.format("%s: %d Öre \n", entry.getKey(), entry.getValue());

        menu();
    }

    /**
     * Method to exit app
     */
    private void exit() {
        if (Utils.logPrompt("Are you sure you want to exit a program y/n").equals("y")) Utils.log("Exited");
        else menu();
    }


}
