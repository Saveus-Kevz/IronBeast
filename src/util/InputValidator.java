package util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static String validateNonEmptyString(Scanner scan, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scan.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Please try again.\n");
        }
    }

    public static int validateIntInRange(Scanner scan, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scan.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number.\n");
            }
        }
    }

    public static LocalDate getDate(Scanner scan, String prompt, int minAge) {
        while (true) {
            try {
                System.out.print(prompt);
                LocalDate birthDate = LocalDate.parse(scan.nextLine().trim());

                // Calculate age
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                if (age < minAge) {
                    System.out.printf("Must be at least %d years old. You are %d.\n", minAge, age);
                    continue;
                }

                // Optional: Check for reasonable maximum age
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

    public static String validateContactNumber(Scanner scan, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + "(Mobile: 09XX-XXX-XXXX, Landline: 02-XXXX-XXXX): ");
            input = scan.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Contact number cannot be empty.\n");
                continue;
            }

            // Remove all non-digits
            String digitsOnly = input.replaceAll("[^0-9]", "");

            // MOBILE: 11 digits starting with 09 (0915-768-4654 = 11 digits)
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

            // INTERNATIONAL FORMAT: +639XXXXXXXXX (12 digits = 63 + 9 + 10 digits)
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

    private static String formatMobile(String digits) {
        // Format: 09XX-XXX-XXXX (11 digits: 09 + 9 digits)
        // Example: 09157684654 -> 0915-768-4654
        if (digits.length() == 11) {
            return digits.substring(0, 4) + "-" +
                    digits.substring(4, 7) + "-" +
                    digits.substring(7);
        }
        return digits;
    }

    private static String formatLandline(String digits) {
        // Format landline based on length
        if (digits.length() == 8) {
            // Metro Manila: 02-XXXXXXX
            return digits.substring(0, 2) + "-" + digits.substring(2);
        } else {
            String s = digits.substring(0, 3) + "-" +
                    digits.substring(3, 6) + "-" +
                    digits.substring(6);
            if (digits.length() == 9) {
                // Provincial: 032-XXX-XXXX
                return s;
            } else if (digits.length() == 10) {
                // Provincial: 035-XXX-XXXX or with extra digit
                return s;
            }
        }
        return digits;
    }

    public static boolean getYesNo(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scan.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter only Y or N.");
            }
        }
    }

    public static String validateAddress(Scanner scan, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scan.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Address cannot be empty.\n");
                continue;
            }

            // Minimum length check
            if (input.length() < 5) {
                System.out.println("Address is too short. Please provide more details.\n");
                continue;
            }

            // Maximum length check (for database constraints)
            if (input.length() > 200) {
                System.out.println("Address is too long. Maximum 200 characters.\n");
                continue;
            }

            // Check for invalid characters (optional, but prevents some abuse)
            if (input.matches(".*[<>\"';].*")) {
                System.out.println("Address contains invalid characters. Please remove <, >, \", ', ;\n");
                continue;
            }

            return input;
        }
    }

}
