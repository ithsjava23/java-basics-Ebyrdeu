package org.example;

import org.example.lib.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Window class provide Console GUI
 */
public class Window {
    private final static Map<Integer, Object> data = new HashMap<>();
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
     * Main Method to start the app
     *
     * @return Window class if user want to use optional Method showGraph()
     */
    public Window menu() {
        Utils.log(MENU);
        chosenOption(Utils.prompt());
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
    private void chosenOption(String opt) {
        switch (opt) {
            case "1" -> input();
            case "2" -> Utils.log("Min, Max och Medel");
            case "3" -> Utils.log("Sortera");
            case "4" -> Utils.log("Bästa Laddningstid");
            case "e" -> exit();
            default -> menu();
        }
    }

    /**
     * First method that user need to choose when app is started as HashMap at moment is empty
     */
    private void input() {
        for (int i = 0; i < 24; i++) {
            Utils.format("%02d o'clock price: ", i);
            // Note: Parse to integer necessary
            data.put(i, Integer.parseInt(Utils.prompt()));
        }

        Utils.log("Show inputs before continue y/n \n");

        if (Utils.prompt().equals("y")) data.forEach((i, o) -> Utils.format("%02d-%02d: %d Öre \n", i, i + 1, o));

        menu();
    }


    /**
     * Method to exit app
     */
    private void exit() {
        Utils.log("Are you sure you want to exit a program y/n");
        if (Utils.prompt().equals("y")) Utils.log("Exited");
        else menu();
    }


}
