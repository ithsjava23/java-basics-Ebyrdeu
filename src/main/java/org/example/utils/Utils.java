package org.example.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Scanner;

public class Utils {
    public static String prompt() {
        Scanner s = new Scanner(System.in);
        return s.nextLine().toLowerCase();
    }

    public static int currentHour() {

        ZoneId zone = ZoneId.of("Europe/Stockholm");
        Instant now = Instant.now();
        return now.atZone(zone).getHour();
    }
}
