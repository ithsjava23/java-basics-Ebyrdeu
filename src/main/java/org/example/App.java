package org.example;


import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("sv-SE"));
        Scanner scanner = new Scanner(System.in);
        Window.menu(scanner);
    }
}
