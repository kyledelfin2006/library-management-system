import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {


    // Wrapper method that calls the 3 parameter-list method and defaults min to 0;
    public  double getValidDouble(Scanner scanner, String prompt){
        return getValidDouble( scanner,  prompt, 0);

    }


    public double getValidDouble(Scanner scanner, String prompt, double min){
        double input;
        while(true){
            System.out.print(prompt);
            try{
                input = scanner.nextDouble();
                scanner.nextLine();
                if (input >= min){
                    return input;
                } else {
                    System.out.println("Value must be greater than " + min);
                }

            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }

        }
    }

    public String getValidString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error! Input cannot be empty!, Try again.");
        }

    }
    
}
