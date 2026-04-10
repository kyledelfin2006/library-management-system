package testAPI;

import api.models.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testCreateValidBook() {
        Book book = new Book("1984", "Orwell", "Fiction", 15.99);

        assertEquals("1984", book.getTitle());
        assertEquals("Orwell", book.getAuthor());
        assertEquals("Fiction", book.getGenre());
        assertEquals(15.99, book.getPrice());
        assertNotNull(book.getId());
        assertTrue(book.getId().matches("\\d{4}")); // "0000" format
    }

    @Test
    void testEmptyTitleThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Orwell", "Fiction", 15.99);
        });
        assertTrue(exception.getMessage().contains("Title cannot be empty"));
    }

    @Test
    void testNullAuthorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("1984", null, "Fiction", 15.99);
        });
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("1984", "Orwell", "Fiction", -5.00);
        });
    }

    @Test
    void testZeroPriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("1984", "Orwell", "Fiction", 0);
        });
    }

    @Test
    void testIdIsGenerated() {
        Book book1 = new Book("Book1", "Author1", "Genre1", 10.00);
        Book book2 = new Book("Book2", "Author2", "Genre2", 20.00);

        assertNotEquals(book1.getId(), book2.getId());
    }
}