package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {
    private static final Scanner SCANNER = new Scanner(System.in);

    private InputUtil() {}

    // ================= SIMPLIFIED INPUT METHODS =================

    /**
     * Gets a string input and ensures it's not empty
     */
    public static String getString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Please try again.\n");
        }
    }

    /**
     * Gets an integer input within a specified range
     */
    public static int getInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(SCANNER.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number.\n");
            }
        }
    }

    /**
     * Gets a Yes/No answer (returns boolean)
     */
    public static boolean getYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = SCANNER.nextLine().trim().toUpperCase();

            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("Invalid input. Please enter only Y or N.");
        }
    }

    // ================= SPECIALIZED VALIDATION METHODS =================
    // (These have unique logic, so keep them separate)

    /**
     * Gets a valid date with minimum age requirement
     */
    public static LocalDate getDate(String prompt, int minAge) {
        while (true) {
            try {
                System.out.print(prompt);
                LocalDate birthDate = LocalDate.parse(SCANNER.nextLine().trim());

                int age = Period.between(birthDate, LocalDate.now()).getYears();

                if (age < minAge) {
                    System.out.printf("Must be at least %d years old. You are %d.\n", minAge, age);
                    continue;
                }

                if (age > 200) {
                    System.out.println("Invalid birthdate. Please verify.");
                    continue;
                }
                return birthDate;

            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please use YYYY-MM-DD.\n");
            }
        }
    }

    /**
     * Gets a valid Philippine phone number
     */
    public static String getPhoneNumber(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + "(Mobile: 09XX-XXX-XXXX, Landline: 02-XXXX-XXXX): ");
            input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Contact number cannot be empty.\n");
                continue;
            }

            String digitsOnly = input.replaceAll("[^0-9]", "");

            // MOBILE: 11 digits starting with 09
            if (digitsOnly.length() == 11 && digitsOnly.startsWith("09")) {
                char thirdDigit = digitsOnly.charAt(2);
                if (thirdDigit >= '1' && thirdDigit <= '9') {
                    return formatMobile(digitsOnly);
                }
            }

            // LANDLINE: 8-10 digits starting with area code
            if (digitsOnly.length() >= 8 && digitsOnly.length() <= 10) {
                if (digitsOnly.startsWith("0")) {
                    return formatLandline(digitsOnly);
                }
            }

            // INTERNATIONAL FORMAT: +639XXXXXXXXX
            if (digitsOnly.length() == 12 && digitsOnly.startsWith("639")) {
                return "+" + digitsOnly.substring(0, 3) + " " +
                        digitsOnly.substring(3, 6) + "-" +
                        digitsOnly.substring(6, 9) + "-" +
                        digitsOnly.substring(9);
            }

            System.out.println("Invalid phone number format.\n");
            System.out.println("Mobile (11 digits): 09XX-XXX-XXXX (e.g., 0915-768-4654)");
            System.out.println("Landline: 0XX-XXX-XXXX (8-10 digits)");
            System.out.println("International: +639XX-XXX-XXXX (12 digits)\n");
        }
    }

    /**
     * Gets a valid address
     */
    public static String getInputAddress(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Address cannot be empty.\n");
                continue;
            }

            if (input.length() < 5) {
                System.out.println("Address is too short. Please provide more details.\n");
                continue;
            }

            if (input.length() > 200) {
                System.out.println("Address is too long. Maximum 200 characters.\n");
                continue;
            }

            if (input.matches(".*[<>\"';].*")) {
                System.out.println("Address contains invalid characters. Please remove <, >, \", ', ;\n");
                continue;
            }

            return input;
        }
    }

    // ================= HELPER METHODS =================

    private static String formatMobile(String digits) {
        if (digits.length() == 11) {
            return digits.substring(0, 4) + "-" +
                    digits.substring(4, 7) + "-" +
                    digits.substring(7);
        }
        return digits;
    }

    private static String formatLandline(String digits) {
        if (digits.length() == 8) {
            return digits.substring(0, 2) + "-" + digits.substring(2);
        } else {
            String s = digits.substring(0, 3) + "-" +
                    digits.substring(3, 6) + "-" +
                    digits.substring(6);
            if (digits.length() == 9 || digits.length() == 10) {
                return s;
            }
        }
        return digits;
    }

    public static void closeScanner() {
        SCANNER.close();
    }
}