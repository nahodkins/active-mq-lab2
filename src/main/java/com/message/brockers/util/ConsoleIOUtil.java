package com.message.brockers.util;

import java.util.Scanner;

public class ConsoleIOUtil {

    private ConsoleIOUtil() {
    }

    public static String getOutputFromConsole(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
