package org.example;

import org.example.lib.utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Window class provides a console-based GUI for handling electricity price data.
 */
public class Window {
    private final static TreeMap<String, Integer> ELECTRICITY_PRICES = new TreeMap<>();
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
     * This method initiates the main menu of the application, allowing the user to interact with it.
     * The menu will continue to display until the user chooses to exit.
     *
     * @param scanner The Scanner object used to read user input.
     */
    public static void menu(Scanner scanner) {
        boolean continueMenu;
        do {
            Utils.log(MENU);
            continueMenu = menuOptions(scanner.nextLine(), scanner);
        } while (continueMenu);
    }

    /**
     * Process the user's menu choice and execute the corresponding action.
     *
     * @param choice  The user's menu choice.
     * @param scanner The Scanner object used to read user input.
     * @return True if the menu should continue to be displayed; false if the user chooses to exit.
     */
    private static boolean menuOptions(String choice, Scanner scanner) {
        switch (choice) {
            case "1" -> input(scanner);
            case "2" -> displayMinMaxAverage();
            case "3" -> sortElectricityPrices();
            case "4" -> findBestChargeTime();
            case "5" -> showElectricityPriceGraph();
            case "e", "E" -> {
                return false;
            }
            default -> Utils.log("Wrong Key");
        }
        return true;
    }

    /**
     * Allows the user to input electricity prices for each hour of the day.
     *
     * @param scanner The Scanner object used to read user input.
     */
    private static void input(Scanner scanner) {
        for (int i = 0; i < 24; i++) {
            Utils.format("%02d-%02d o'clock price: ", i, i + 1);

            // Parse user input as an integer and store it in the TreeMap.
            ELECTRICITY_PRICES.put(String.format("%02d", i), Integer.parseInt(scanner.nextLine()));
        }

    }

    /**
     * Calculate and display the minimum, maximum, and average electricity prices.
     */
    private static void displayMinMaxAverage() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(ELECTRICITY_PRICES.entrySet());
        AtomicReference<Double> averagePrice = new AtomicReference<>(0.0);

        // Calculate and display the minimum price.
        list.sort(Map.Entry.comparingByValue());
        Utils.format("Lägsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Calculate and display the maximum price.
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Utils.format("Högsta pris: %s, %d öre/kWh", Utils.clockFormat(list.get(0).getKey()), list.get(0).getValue());

        // Calculate and display the average price.
        ELECTRICITY_PRICES.forEach((s, integer) -> averagePrice.updateAndGet(v -> v + integer));
        Utils.format("Medelpris: %.2f öre/kWh", (averagePrice.get() / ELECTRICITY_PRICES.size()));

    }

    /**
     * Sort and display electricity prices in descending order.
     */
    private static void sortElectricityPrices() {
        // Convert to List<Map.Entry<K, V>> for easy sort method
        List<Map.Entry<String, Integer>> list = new ArrayList<>(ELECTRICITY_PRICES.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : list)
            Utils.format("%s %02d öre", Utils.clockFormat(entry.getKey()), entry.getValue());

    }

    /**
     * Find and display the best charging times based on the lowest prices.
     */
    private static void findBestChargeTime() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(ELECTRICITY_PRICES.entrySet());
        List<String> bestHours = Utils.getBestHours(list);

        Utils.format("Påbörja laddning klockan %s", bestHours.get(0));

        list.sort(Map.Entry.comparingByValue());

        double averagePrice = 0;
        for (int i = 0; i < 4; i++)
            averagePrice += list.get(i).getValue();

        Utils.format("Medelpris 4h: %.1f öre/kWh", averagePrice / 4.0);


    }

    /**
     * Display a graphical representation of electricity prices.
     */
    public static void showElectricityPriceGraph() {
        int max = ELECTRICITY_PRICES.values().stream().max(Integer::compareTo).orElse(0);
        int min = ELECTRICITY_PRICES.values().stream().min(Integer::compareTo).orElse(0);

        int numSteps = 5;

        // Calculate the size of each step on the y-axis.
        int stepSize = ((max - min) / numSteps);

        // Check Max value Map have to see what step to use for rendering y-axis/columns
        for (int i = numSteps; i >= 0; i--) {

            // Calculate the y-axis value for the current step
            int yValue = min + i * stepSize;

            // Render the y-axis/columns and labels
            if (yValue + 10 >= max || yValue == min) System.out.printf("%3d| ", yValue);
            else Utils.log("   | ");

            // Render bars depending on value we have
            ELECTRICITY_PRICES.forEach((s, integer) -> {
                if (integer >= yValue) Utils.log(" x ");
                else System.out.print("");
            });

            // Print new line
            System.out.println();
        }

        // Render the x-axis/rows
        Utils.log("   |------------------------------------------------------------------------ \n");
        Utils.log("   | ");
        ELECTRICITY_PRICES.forEach((s, integer) -> Utils.log(s + " "));

        System.out.println();

    }

}
