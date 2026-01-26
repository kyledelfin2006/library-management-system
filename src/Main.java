
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        InputValidator input = new InputValidator();
        Scanner scanner = new Scanner(System.in);
        LibraryManager manager = new LibraryManager();

        int choiceForActionMenu = 0;
        boolean validChoice;


       do {

            // 1. Print menu
           System.out.println("\n");
           System.out.println("===== LIBRARY MANAGEMENT SYSTEM =====");
           System.out.println("=====================================");
           System.out.println("=========== by: Kyle D. ============= \n");


           System.out.println("1 - Add Book");
           System.out.println("2 - View All Books");
           System.out.println("3 - Remove Book");
           System.out.println("4 - Find Books Within Budget");
           System.out.println("5 - View Most Expensive Book");
           System.out.println("6 - View Total Book Value");
           System.out.println("7 - Sort Books ");
           System.out.println("8 - Search Books ");
           System.out.println("0 - Exit");





            validChoice = false;
            int minMenuOption = 0;
            int maxMenuOption = 8;

            // Loop until a valid choice is entered.
            while (!validChoice) {
                System.out.print("\n Enter action of choice: ");
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
                    System.out.print("Invalid Input! ");
                    scanner.nextLine();
                }
            }


           // Main menu loop: continues until the user chooses exit (0)
           switch (choiceForActionMenu) {
               case 1: // Add books: ask for quantity and repeat until valid input is provided
                   manager.addBook(scanner);
                   break;

               case 2: // Display all books in the library
                   manager.displayAllBooks();
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
                   List<Book> sortedBooks = manager.getBooksSortedBy(bookToSort);
                   if (!sortedBooks.isEmpty()) {
                       // Display sorted books
                       System.out.printf("%-10s %-25s %-20s %-15s %-10s\n",
                               "ID", "Title", "Author", "Genre", "Price");
                       for (Book book : sortedBooks) {
                           System.out.println(book);
                       }
                       System.out.println("TOTAL BOOKS: " + sortedBooks.size());
                   }

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


       scanner.close();

    }
}



