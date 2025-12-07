import java.util.ArrayList;
import java.util.List;

public  class LibraryManager{
    private Book[] books;

    public LibraryManager(Book[] books){
        this.books = books;
    }

    public void displayAllBooks(){

        System.out.printf("%-20s %-20s %-15s %-10s %-10s\n",
                "ID", "Title", "Author", "Genre", "Price");


        for (Book b : books) {
            if (b != null) {
                System.out.printf("%-20s %-20s %-15s %-10s %-10.2f\n",
                        b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getPrice());
            }
        }

    }

    public void  findBooksByAuthor(String author){

        if (books == null || author == null){
            System.out.println("Invalid Input");
            return;
        }

        List<Book> matchingBooks = new ArrayList<>();

        // searches for books by author and adds to matchingBooks

        author = author.trim().toLowerCase();
        for (Book b : books){
            if (b.getAuthor().trim().toLowerCase().equals(author)){
                matchingBooks.add(b);
            }
        }

        // prints the books by the author (none if no authors found)

        if (matchingBooks.isEmpty()){
            System.out.println("No books found by that author.");
        } else {
            for (Book b : matchingBooks) {
                System.out.println(b);
            }
        }


    }

    public void  findBooksByGenre(String genre){

        if (books == null || genre == null){
            System.out.println("Invalid Input");
            return;
        }

        List<Book> matchingBooks = new ArrayList<>();

        genre = genre.trim().toLowerCase();
        for (Book b : books){
            if (b.getGenre().trim().toLowerCase().equals(genre)){
                matchingBooks.add(b);
            }
        }

        if (matchingBooks.isEmpty()){
            System.out.println("No books found by that genre.");
        } else {
            for (Book b : matchingBooks) {
                System.out.println(b);
            }
        }




    }

    public void  findBooksCheaperThan(double price){
        boolean found = false;

        for (Book b : books){
            if (b != null && b.getPrice() < price ) {
                System.out.println(b);
                found = true;
            }
        }

        if (!found){
            System.out.println("No books found cheaper than " + price);
        }
    }

    public void countAllBooksOfGenre(String genre){

        if (genre == null || genre.trim().isEmpty()) {
            System.out.println("Invalid genre input.");
            return;
        }

        int counter = 0;

        for (Book b : books){
            if (b != null && b.getGenre().equalsIgnoreCase(genre) ) {
                counter++;

            }
        }



        System.out.printf("Found %d book%s of genre %s%n",
                counter, (counter == 1 ? "" : "s"), genre);

    }

    public void sumOfAllBooks(){
        double total = 0;

        for (Book b : books){
            if (b != null) {
                total += b.getPrice();
            }
        }

        System.out.println("Total Value of Library Collection: " + total);
    }

    public void mostExpensiveBook(){
        if (books.length == 0){
            System.out.println("No books available ");
            return;
        }

        Book mostExpensive = null;

        for (Book b : books){
            if (b != null && (mostExpensive == null || b.getPrice() > mostExpensive.getPrice())) {
                mostExpensive = b;
            }

        }

        if (mostExpensive != null){
            System.out.println("Most Expensive Book:" + mostExpensive);
        }


    }
}
