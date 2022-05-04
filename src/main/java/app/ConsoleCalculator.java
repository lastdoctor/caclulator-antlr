package app;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

public record ConsoleCalculator(Calculator<String, BigDecimal> calculator, Scanner scanner, Set<String> stopWords) {

    public void run() {
        String input;
        while (true) {
            System.out.println("Enter a math expression:");
            input = scanner.nextLine().trim().toLowerCase();
            if (stopWords.contains(input)) {
                System.out.println("Exit");
                break;
            }

            try {
                System.out.println("Result: " + calculator.calculate(input));
            }
            catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }
    }
}
