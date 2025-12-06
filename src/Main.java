
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int amountOfBooks = 0;
        boolean isValidInput = false;
        int choice = -1;

        while (!isValidInput) {


            System.out.println("_____ LIBRARY SYSTEM _____");
            System.out.print("How many books will you add?: ");
            try {
                amountOfBooks = scanner.nextInt();
                scanner.nextLine();


                if (amountOfBooks <= 0) {
                    System.out.println("Must add at least 1 book!");
                } else {
                    isValidInput = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                scanner.nextLine();
            }


        }

        Book booklist[] = new Book[amountOfBooks];

        for (int i = 0; i < amountOfBooks; i++) {

            System.out.print("\n Enter book title: ");
            String title = scanner.nextLine();

            System.out.print("\n Enter book author: ");
            String author = scanner.nextLine();

            System.out.print("\n Enter book genre: ");
            String genre = scanner.nextLine();

            System.out.print("\n Enter book price: ");
            double bookPrice = scanner.nextDouble();
            scanner.nextLine();

            if (bookPrice <= 0) {
                System.out.println("Price cannot be negative!");
                i--;
                continue;
            }

            try {
                booklist[i] = new Book(title, author, genre, bookPrice);
            } catch (IllegalArgumentException e){
                System.out.println("Book creation failed. Try again.");
                i--;
            }

        }


        LibraryManager manager = new LibraryManager(booklist);
        boolean validChoice;

        while (choice != 0) {

            // 1. Print menu
            System.out.println("===== Library Menu =====");
            System.out.println("1 - Display all books");
            System.out.println("2 - Search books by author");
            System.out.println("3 - Search books by genre");
            System.out.println("4 - Find books cheaper than X");
            System.out.println("5 - Show most expensive book");
            System.out.println("6 - Show total value of all books");
            System.out.println("7 - Count books by genre");
            System.out.println("0 - Exit");

            validChoice = false;

            // 2. Get valid choice
            System.out.print("Enter choice: ");
            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    validChoice = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input! Enter a number.");
                    scanner.nextLine();
                }
            }

            // 3. Execute selected option
            switch (choice) {
                case 1:
                    manager.displayAllBooks();
                    break;
                case 2:
                    System.out.print("Enter Author to search: ");
                    String author = scanner.nextLine();

                    manager.findBooksByAuthor(author);

                    break;
                case 3:
                    System.out.print("Enter Genre to search: ");
                    String genreToSearch = scanner.nextLine();

                    manager.findBooksByGenre(genreToSearch);

                    break;
                case 4:
                    double price = -1;
                    boolean validPrice = false;
                    System.out.print("Enter budget price : ");

                    while (!validPrice) {
                        try {
                            price = scanner.nextDouble();
                            scanner.nextLine();
                            validPrice = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input! Please Enter a number.");
                            scanner.nextLine();
                        }
                    }
                    manager.findBooksCheaperThan(price);

                    break;
                case 5:
                    manager.mostExpensiveBook();
                    break;
                case 6:
                    manager.sumOfAllBooks();
                    break;

                case 7:
                    System.out.print("Enter genre to count: ");
                    String genreToCount = scanner.nextLine();

                    manager.countAllBooksOfGenre(genreToCount);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();

    }
}



