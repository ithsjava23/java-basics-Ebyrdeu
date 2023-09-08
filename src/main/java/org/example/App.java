package org.example;


import java.util.Locale;

public class App {

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("sv-SE"));
        Window.menu();
    }
}
