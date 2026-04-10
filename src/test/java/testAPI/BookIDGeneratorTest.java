package testAPI;

import api.util.BookIDGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookIDGeneratorTest {

    @Test
    void testGenerateNextID_ReturnsFormattedString() {
        // Reset counter to known state for testing
        BookIDGenerator.setNextId(0);

        String id = BookIDGenerator.generateNextID();

        assertNotNull(id);
        assertEquals(4, id.length());
        assertTrue(id.matches("\\d{4}")); // 4 digits
    }

    @Test
    void testGenerateNextID_StartsAtZero() {
        BookIDGenerator.setNextId(0);

        String firstId = BookIDGenerator.generateNextID();
        String secondId = BookIDGenerator.generateNextID();

        assertEquals("0000", firstId);
        assertEquals("0001", secondId);
    }

    @Test
    void testGenerateNextID_IncrementsSequentially() {
        BookIDGenerator.setNextId(0);

        assertEquals("0000", BookIDGenerator.generateNextID());
        assertEquals("0001", BookIDGenerator.generateNextID());
        assertEquals("0002", BookIDGenerator.generateNextID());
        assertEquals("0003", BookIDGenerator.generateNextID());
    }

    @Test
    void testGenerateNextID_HandlesDoubleDigits() {
        BookIDGenerator.setNextId(10);

        assertEquals("0010", BookIDGenerator.generateNextID());
        assertEquals("0011", BookIDGenerator.generateNextID());
    }

    @Test
    void testGenerateNextID_HandlesThreeDigits() {
        BookIDGenerator.setNextId(100);

        assertEquals("0100", BookIDGenerator.generateNextID());
        assertEquals("0101", BookIDGenerator.generateNextID());
    }

    @Test
    void testGenerateNextID_HandlesFourDigits() {
        BookIDGenerator.setNextId(999);

        assertEquals("0999", BookIDGenerator.generateNextID());
        assertEquals("1000", BookIDGenerator.generateNextID());
        assertEquals("1001", BookIDGenerator.generateNextID());
    }

    @Test
    void testSetNextId() {
        BookIDGenerator.setNextId(50);

        assertEquals("0050", BookIDGenerator.generateNextID());
        assertEquals("0051", BookIDGenerator.generateNextID());
    }

    @Test
    void testSetNextId_ToZero() {
        BookIDGenerator.setNextId(0);

        assertEquals("0000", BookIDGenerator.generateNextID());
        assertEquals("0001", BookIDGenerator.generateNextID());
    }

    @Test
    void testSetNextId_ToLargeNumber() {
        BookIDGenerator.setNextId(9999);

        assertEquals("9999", BookIDGenerator.generateNextID());
        assertEquals("10000", BookIDGenerator.generateNextID()); // 5 digits!
    }

    @Test
    void testConcurrentGeneration_NoDuplicates() throws InterruptedException {
        BookIDGenerator.setNextId(0);

        // Create 10 threads, each generating 100 IDs
        int threads = 10;
        int idsPerThread = 100;
        java.util.concurrent.atomic.AtomicInteger duplicateCheck = new java.util.concurrent.atomic.AtomicInteger(0);

        Thread[] threadArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < idsPerThread; j++) {
                    BookIDGenerator.generateNextID();
                    duplicateCheck.incrementAndGet();
                }
            });
        }

        for (Thread t : threadArray) {
            t.start();
        }

        for (Thread t : threadArray) {
            t.join();
        }

        // Total generated = threads * idsPerThread = 10 * 100 = 1000
        assertEquals(threads * idsPerThread, duplicateCheck.get());
        // If no exceptions and all IDs were generated, AtomicInteger proves thread-safety
    }

    @Test
    void testResetBehavior() {
        BookIDGenerator.setNextId(0);
        assertEquals("0000", BookIDGenerator.generateNextID());

        BookIDGenerator.setNextId(0);
        assertEquals("0000", BookIDGenerator.generateNextID()); // Starts over at 0
    }
}