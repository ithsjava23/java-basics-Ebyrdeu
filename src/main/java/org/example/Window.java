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
             * arg 1 -  Format K to 00-01 as string
             * Parse to integer necessary as prompt returns @type String
             * */
            data.put(String.format("%02d", i), Integer.parseInt(Utils.prompt()));
        }

        if (Utils.logPrompt("Show inputs before continue y/n \n").equals("y"))
            data.forEach((i, o) -> Utils.format("%s: %d Öre \n", i, o));

        menu();
    }

    private void minMaxAverage() {
        menu();
    }

    /**
     * Sorting a List from descending order
     * */
    private void sort() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(data.entrySet());
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
