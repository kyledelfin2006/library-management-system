public class Book {

    private final String title;
    private final String author;
    private final String genre;
    private final double price;
    private final String id;
    private static int counter = 0;

    Book(String title, String author, String genre, double price) {
        validate(title, "Title");
        validate(author, "Author");
        validate(genre, "Genre");
        validate(price, "Price");

        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;

        counter++;
        this.id = String.format("BOOK-%04d", counter);
    }

    private void validate(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            System.out.printf("\nError! %s needed! ", fieldName);
            throw new IllegalArgumentException(fieldName + " cannot be empty or null");
        }
    }

    private void validate(double value, String fieldName) {
        if (value <= 0) {
            System.out.println("Error! " + fieldName + " must be greater than 0!");
            throw new IllegalArgumentException(fieldName + " cannot be empty or null");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }


    public String toString() {
        return String.format("%-10s %-20s %-15s %-10s %-10.2f",
                id, title, author, genre, price);
    }
}