import java.util.*;

public  class LibraryManager{
    private final List<Book> books = new LinkedList<>();
    InputValidator input = new InputValidator();


    public void displayAllBooks(){

        System.out.printf("%-10s %-25s %-20s %-15s %-10s\n",
                "ID", "Title", "Author", "Genre", "Price");

        for (Book bookObject : books) {
            System.out.println(bookObject);
        }

        System.out.println("TOTAL BOOKS: " + books.size());
    }

    public void addBook(Scanner scanner){
        boolean validInput = false;
        int amountOfBooksToAdd = 0;

        // Validates user input for adding book
        while(!validInput) {
            System.out.print("How many books to add? : ");
            String inputForAddingBook = scanner.nextLine().trim();

            if(inputForAddingBook.isEmpty()){
                System.out.println("Input cannot be empty.");
                continue;
            }

            try {
                amountOfBooksToAdd = Integer.parseInt(inputForAddingBook);

                if (amountOfBooksToAdd > 100) {  // Max of 100
                    System.out.println("Maximum 100 books at a time. Please enter a smaller number.");
                    continue;  // Ask again
                }

                // Loops if amount is less than 0
                if (amountOfBooksToAdd > 0){
                    validInput = true;
                } else {
                    System.out.println("Must add at least one book.");
                }

            } catch (NumberFormatException exception) {
                System.out.println("Invalid input, must only contain whole numbers.");
            }

        }

        // Prompts user to enter book data
        for (int i = 0; i < amountOfBooksToAdd; i++) {

            System.out.println(" \n Book " + (i + 1));

            String title = input.getValidString(scanner, "\n Enter book title: ");
            String author = input.getValidString(scanner, "\n Enter book author: ");
            String genre = input.getValidString(scanner, "\n Enter book genre: ");
            double bookPrice = input.getValidDouble(scanner, "\n Enter book price: ",1);

            books.add(new Book(title, author, genre, bookPrice));

        }
    }


    public void  findBooksWithinBudgetOf(double price){
        boolean found = false;

        for (Book b : books){
            if (b.getPrice() <= price ) { // no need to check if book is null, because Books is a list
                System.out.println(b);
                found = true;
            }
        }

        if (!found){
            System.out.println("No books found cheaper than " + price);
        }
    }

    public List<Book> getBooksSortedBy(String field) {

        if (field == null || field.trim().isEmpty()) {
            System.out.println("Invalid input.");
            return new ArrayList<>(books);
        }

        List<Book> sortedCopy = new ArrayList<>(books);

        field = field.trim().toLowerCase();

            switch (field) {
                case "title":
                    sortedCopy.sort(Comparator.comparing(Book::getTitle));
                    break;
                case "author":
                    sortedCopy.sort(Comparator.comparing(Book::getAuthor));
                    break;
                case "id":
                    sortedCopy.sort(Comparator.comparing(Book::getId));
                    break;
                case "price":
                    sortedCopy.sort(Comparator.comparing(Book::getPrice));
                    break;
                case "genre":
                    sortedCopy.sort(Comparator.comparing(Book::getGenre));
                    break;
                default:
                    System.out.println("Invalid sort field. Options: title, author, id, price, genre");
                    return sortedCopy;
            }

            System.out.println("Books sorted by " + field);

            return sortedCopy;
    }

    public void displayTotalValue(){
        double total = 0;

        for (Book bookObject : books){
            total += bookObject.getPrice();
        }

        System.out.println("Total Value of Library Collection: " + total);
    }

    public void mostExpensiveBook(){
        if (books.isEmpty()){
            System.out.println("No books available ");
            return;
        }

        Book mostExpensive = books.getFirst();

        for (Book b : books){

            if (b.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = b;
            }

        }

            System.out.println("Most Expensive Book:" + mostExpensive);
    }

    private boolean bookMatches(Book book, String type, String value) {
        // Clean up the inputs
        type = type.trim().toLowerCase();  // "AUTHOR" → "author"
        value = value.trim();               // "  Rowling " → "Rowling"

        // Check different types of criteria
        switch (type) {
            case "author":
                return book.getAuthor().equalsIgnoreCase(value);
            case "title":
                return book.getTitle().equalsIgnoreCase(value);
            case "genre":
                return book.getGenre().equalsIgnoreCase(value);
            case "price":
                try {
                    double priceValue = Double.parseDouble(value);
                    return Math.abs(book.getPrice() - priceValue) < 0.0001;
                } catch (NumberFormatException e) {
                    return false;  // Can't convert to number = no match
                }
            default:
                return false;  // Unknown type = no match
        }
    }

    public void search(String type, String value){

        if(type == null || type.trim().isEmpty()){
            System.out.println("Couldn't find book.");
            return;
        }

        List<String> validTypes = Arrays.asList("author", "title", "genre", "price");
        if (!validTypes.contains(type.trim().toLowerCase())){
            System.out.println("Invalid search type. Use: author, title, genre, or price");
            return;
        }

        List<Book> results = new ArrayList<>();
        for (Book book : books){
            if(bookMatches(book,type,value)){
                results.add(book);
            }
        }

        System.out.println("Found " + results.size() + " book/s");

        for (Book book : results) {
            System.out.println(book);
        }

    }

    public void deleteBook(Scanner scanner){

        ArrayList<Book> toRemove = new  ArrayList<>();

        String type = input.getValidString(scanner, "Delete book by (author/price/title/genre): ");
        String value = input.getValidString(scanner, "Enter " + type + ": ");

        for (Book book : books){
            if (bookMatches(book,type,value)){
                toRemove.add(book);
            }
        }
        if (toRemove.isEmpty()){
            System.out.println("No books to remove");
        } else {
            System.out.println("Books removed:");
            System.out.println(toRemove); // displays the removed objects
            books.removeAll(toRemove); // removes all objects
        }

    }
}


