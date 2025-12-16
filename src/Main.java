import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        InputValidator input = new InputValidator();
        Scanner scanner = new Scanner(System.in);
        int amountOfBooks = 0;
        int choiceForActionMenu = 0;
        boolean isValidInput = false;
        boolean validChoice;


        System.out.println("===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("==========================\n");

        while (!isValidInput) {

            System.out.print("How many books will you add?: ");
            String inputForAddingInitialBook = scanner.nextLine().trim();

            if (inputForAddingInitialBook.isEmpty()){
                System.out.println("Input cannot be empty");
                continue; // go back to the start of the loop
            }

            try {

               amountOfBooks = Integer.parseInt(inputForAddingInitialBook);

               if (amountOfBooks > 0){
                    isValidInput = true;
                } else {
                    System.out.println("Must add one or more book/s.");
                }


            } catch (NumberFormatException e) {
                System.out.println("Input must be a whole number.");
            }
        }

         List<Book> booklist = new ArrayList<>(amountOfBooks);


        for (int i = 0; i < amountOfBooks; i++) {

            System.out.println(" \n Book " + (i + 1));

            String title = input.getValidString(scanner, "\n Enter book title: ");

            String author = input.getValidString(scanner, "\n Enter book author: ");

            String genre = input.getValidString(scanner, "\n Enter book genre: ");

            double bookPrice = input.getValidDouble(scanner, "\n Enter book price: ",1);

            booklist.add(i, new Book(title, author, genre, bookPrice));


        }
            // Declare object to call LibraryManager methods
            LibraryManager manager = new LibraryManager(booklist);

       do {

            // 1. Print menu
            System.out.println("===== Library Menu =====");
            System.out.println("1 - Display all books");
            System.out.println("2 - Add Book ");
            System.out.println("3 - Delete Book ");
            System.out.println("4 - Find books cheaper than budget");
            System.out.println("5 - Show most expensive book");
            System.out.println("6 - Show total value of all books");
            System.out.println("7 - Sort books by type ");
            System.out.println("8 - Search books by type");
            System.out.println("0 - Exit");



            validChoice = false;
            int minMenuOption = 0;
            int maxMenuOption = 8;

            // Loop until a valid choice is entered.
            while (!validChoice) {
                System.out.print("Enter menu of choice: ");
                try {
                    choiceForActionMenu = scanner.nextInt();
                    scanner.nextLine();

                    // Checks if choice is in valid range, if so, marks as valid.
                    if (choiceForActionMenu >= minMenuOption &&  choiceForActionMenu <= maxMenuOption){
                        validChoice = true;
                    } else {
                        System.out.print("Please enter a number between 0 and 8.");
                    }

                } catch (InputMismatchException e) {
                    System.out.print("Invalid Input!");
                    scanner.nextLine();
                }
            }


           // Main menu loop: continues until the user chooses exit (0)
           switch (choiceForActionMenu) {
               case 1: // Display all books in the library
                   manager.displayAllBooks();
                   break;

               case 2: // Add books: ask for quantity and repeat until valid input is provided
                       manager.addBook(scanner);
                   break;

               case 3: // Prompts user to delete book/s
                   manager.deleteBook(scanner);
                   break;

               case 4: // Prompt user for budget and display books within that budget
                   double price = input.getValidDouble(scanner, "Enter budget price: ");
                   manager.findBooksWithinBudgetOf(price);
                   break;

               case 5: // Display the most expensive book
                   manager.mostExpensiveBook();
                   break;

               case 6: // Display total value of the book collection
                   manager.displayTotalValue();
                   break;

               case 7: // Prompt user for a field, sort books by it, then display all books
                   String bookToSort = input.getValidString(scanner,"Field to sort by: " );
                   manager.getBooksSortedBy(bookToSort);
                   manager.displayAllBooks();
                   break;

               case 8: // Prompt user for search type and value, then perform search
                   String type = input.getValidString(scanner,"Enter search type (author / genre / title/price): ");
                   String value = input.getValidString(scanner, "Enter " + type + " to search: ");
                   manager.search(type, value);
                   break;
               case 0: // Exit the menu/program
                   System.out.println("Exiting...");
                   break;
           }
       } while (choiceForActionMenu != 0);


    }
}



