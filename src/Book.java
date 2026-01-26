public class Book {

    // What a book should contain
    private final String title;
    private final String author;
    private final String genre;
    private final double price;
    private final String id;
    private static int counter = 0;

    Book(String title, String author, String genre, double price) {
    // Validates user input (title, author, genre, price) before creating the Book object
        validate(title, "Title");
        validate(author, "Author");
        validate(genre, "Genre");
        validate(price, "Price");

    // Assigns when user input is valid
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;

        counter++;
        this.id = String.format("BOOK-%04d", counter);
    }

    // Validates that a String value for a Book field is not null or empty.
    private void validate(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty or null");
        }
    }

    // Validates that a numeric value (double) for a Book field is greater than 0.
    private void validate(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException("Error! " + fieldName + " must be greater than 0!");
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

 // Overrides when you print a book object
 @Override
 public String toString() {
     return String.format(
             "%-10s %-25s %-20s %-15s %10.2f",
             id, title, author, genre, price
     );
 }


}