package org.example;

import org.example.lib.utils.Utils;

import java.util.*;

/**
 * Window class provide Console GUI
 */
public class Window {
    private final static Map<String, Integer> data = new HashMap<>();

    private final static String MENU = """
            Elpriser
            ========
            1. Inmatning
            2. Min, Max och Medel
            3. Sortera
            4. Bästa Laddningstid (4h)
            e. Avsluta
            """;

    /**
     * Menu for second option in MENU constant
     */
    private final static String MENU_2 = """
            Min, Max och Medel
            ==================
            1. Min
            2. Max
            3. Medel
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
    public void showGraph() {
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
            data.put(String.format("%02d", i), Integer.parseInt(Utils.prompt()));
        }

        if (Utils.logPrompt("Show inputs before continue y/n \n").equals("y"))
            data.forEach((i, o) -> Utils.format("%s: %d Öre \n", i, o));

        menu();
    }

    /**
     * Method that allow to show Min Max and Avrage value on user provided inputs*/
    // TODO: Need optimization and refactoring
    // NOTE: For now do work that needed
    private void minMaxAverage() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(data.entrySet());
        int temp = 0;

        // Min
        list.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));
        for (Map.Entry<String, Integer> entry : list) {
            Utils.log("Min: " + entry.getValue() + "\n");
            break;
        }

        // Max
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String, Integer> entry : list) {
            Utils.log("Max: " + entry.getValue() + "\n");
            break;
        }
        // Average
        for (Map.Entry<String, Integer> entry : list) {
            temp += entry.getValue();
        }

        Utils.log("Average: " + (temp / 3) + "\n");

        menu();
    }

    /**
     * Sorting a List based on user desire
     */
    private void sort() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(data.entrySet());

        if (Utils.logPrompt("Do you want to sort by Descending or Ascending order d/a \n").equals("d"))
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        else
            list.sort(Map.Entry.comparingByValue(Comparator.naturalOrder()));

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
