package org.example;

import org.example.utils.Utils;

public class Window {
    private final static String MENU = """
            Elpriser
            ========
            1. Inmatning
            2. Min, Max och Medel
            3. Sortera
            4. Bästa Laddningstid (4h)
            e. Avsluta
            """;
    private int price;

    public Window menu() {
        System.out.println(MENU);
        chosenOption(Utils.prompt());

        return this;
    }

    public void showGraph() {

        System.out.println("""
               Test
       """);
    }


    private void chosenOption(String opt) {
        switch (opt) {
            case "1" -> input();
            case "e" -> exit();
            default -> menu();
        }
    }


    private void input() {
        System.out.println("Please provide price: ");

    }

    private void exit() {

        System.out.println("Are you sure you want to exit a program y/n");
        if (Utils.prompt().equals("y"))
            System.out.println("Exited");
        else
            menu();
    }


}
