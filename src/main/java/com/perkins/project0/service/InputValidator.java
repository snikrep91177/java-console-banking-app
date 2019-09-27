package com.perkins.project0.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputValidator {
	//Responsible for, with very rare exception, prompting the user for
    //input and validating that input so that unexpected values can't corrupt the service of this
    //application or otherwise cause the application to crash.


    /** This method is called when asking for a String value from the console. This method is never
    called directly as it does minimal validation of the input but instead is responsible for
    checking that the string is within the constraints for length, that it does not contain an
    exit clause such as '0' to exit the current menu or '-1' to exit the application. This method
    is never called directly but instead is employed by the public members of this class. */
    private static String enterString(int minLength, int maxLength) {
        String returnString = null;
        while (true) {
            try {
                returnString = getInput();
                if (returnString.length() > maxLength && minLength < returnString.length()) {
                    //System.out.println("INVALID ENTRY: Response must be between " + minLength + "and " + maxLength);
                }
            } catch (IOException e) {
                System.out.println("Invalid Input");
            }
            break;
        }
        return returnString;
    }

    /** Similar to enterString(see above) this is responsible for gathering Integer inputs from the
    user. This utilizes the filterIn method (Below) make sure that inputs are numbers and
    indeed should be able to fetch numbers even from sentences should the need arise. This method
    is never called directly but instead is employed by the public members of this class. */
    private static int enterInt(int minNumber, int maxNumber) {
        int returnInt = 0;
        while (true) {
            try {
                returnInt = Integer.parseInt(getInput());
                if (returnInt > maxNumber && minNumber < returnInt) {
                    //System.out.println("INVALID ENTRY: Response must be between " + minNumber + "and " + maxNumber);
                }
                //The method does its best to filter out any garbage and parse the String as an int
                //but if thats not possible (this should only be the case if the customer in question
                //enters NO numbers in a particular input  requires them. Then it informs the
                //user to try again and re-prompts them.
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid Input");
                continue;
            }
            return returnInt;
        }
    }

    private static double enterDouble(int minNumber, int maxNumber) {
        double returnDouble = 0;
        while (true) {
            try {
                returnDouble = Double.parseDouble(getInput());
                if (returnDouble > maxNumber && minNumber < returnDouble) {
                    //System.out.println("INVALID ENTRY: Response must be between " + minNumber + "and " + maxNumber);
                }
                //The method does its best to filter out any garbage and parse the String as an int
                //but if thats not possible (this should only be the case if the customer in question
                //enters NO numbers in a particular input  requires them. Then it informs the
                //user to try again and re-prompts them.
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid Input");
                continue;
            }
            return returnDouble;
        }
    }



    /**This method is responsible for actually opening the console for input. Creating a BufferedReader
    to receive the input. Then after retrieving the input passing it to either the enterInt or
    enterString methods (above). This was made into a method so that if there is ever an error
    such as when I attempted to implement the close() function of BufferedReader and caused an
    infinite loop in all prompts I am able to manipulate it here and here alone. */

    private static String getInput() throws IOException {
        while (true) {
            Scanner in = new Scanner(new InputStreamReader(System.in));
            String returnVar = in.nextLine();
            if(doesTheStingPassValidation(returnVar, "%", ";"," ")) return returnVar;
            }
        }

        //Useful for area's where an additional layer of unique string validation is required outside of the
        //normal safeguards already in place.

    private static boolean doesTheStingPassValidation(String entry, String... doesNotContain) {
        for (int i = 0; i < doesNotContain.length; i++) {
            if (entry.contains(doesNotContain[i])) {
                System.out.println("Entry cannot contain " + "\"" + doesNotContain[i] + "\"");
                return false;
            }
        }
        return true;
    }

    //These methods provide additional validation and ensure we are getting the input we require from the end user.

    public static String textGet(int minLength, int maxLength, String... prompt) {
        System.out.println("\n--------------------------");
        lineOutput(prompt);
        return enterString(minLength, maxLength);
    }

    public static int integerGet(int minNumber, int maxNumber, String... prompt) {
        System.out.println("\n--------------------------");
        lineOutput(prompt);
        return enterInt(minNumber, maxNumber);
    }

    public static double decimalGet(int minNumber, int maxNumber, String... prompt) {
        System.out.println("\n++++++++++++++++++++++++++");
        System.out.println("\n--------------------------");
        lineOutput(prompt);
        return enterDouble(minNumber, maxNumber);
    }

    //** Responsible for printing the outgoing text to the customer. Useful for quickly creating menu's */

    private static void lineOutput(String[] prompt) {
        for (int i = 0; i < prompt.length; i++) {
            if (i == 0) System.out.println(prompt[i]);
            if (i != 0) System.out.println(i + ". " + prompt[i]);
        }
        System.out.println();
    }

    /** This establishes whether or not the current customer is inputting the correct account credentials
     * necessary to proceed with logging in to the current account. If the customer fails 3 times to enter the correct
     * information they will find themselves kicked back out to the homescreen.*/

    public static boolean enterPassword(String correctPassword) {
        int failedPasswordCounter = 1;
        while (true) {
            String enteredPassword = textGet(1, 30, "Password:");
            if (enteredPassword.equals(correctPassword)) {
                break;
            }
            System.out.println("Invalid entry. Please verify credentials and try again.\n+++++++++++++++++++++++");
            if (failedPasswordCounter >= 3) {
                System.out.println("Too many failed attempts.");
                return false;
            }
            failedPasswordCounter++;
        }
        return true;
    }

    public static String properCasing(String entry) {
                entry = entry.toLowerCase();
                char[] e = entry.toCharArray();
                String upperFirst = String.valueOf(e[0]).toUpperCase();
                e[0] = upperFirst.toCharArray()[0];
                return String.valueOf(e);
        }

}
