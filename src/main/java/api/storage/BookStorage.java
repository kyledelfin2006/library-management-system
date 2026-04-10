package api.storage;

import api.exceptions.StorageException;
import api.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Is a type of storage
public class BookStorage implements Storage<Book> {
    private final ObjectMapper mapper;
    private final String filename;

    public BookStorage(String filename) {
        this.filename = filename; // User decides this
        this.mapper = new ObjectMapper();

        // Make JSON output pretty (easier to read)
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Should be caught externally
    @Override
    public void save(List<Book> books) {
        try {
            mapper.writeValue(new File(filename), books);
        } catch (IOException e) {
            throw new StorageException("Failed to save books to file: " + filename, e);
        }
    }

    @Override
    public List<Book> load() {
        File filePath = new File(filename);

        if (!filePath.exists()) {
            return new ArrayList<>();
        }

        if (filePath.length() == 0) {
            return new ArrayList<>();  // File is empty → return empty list
        }

        try {
            return mapper.readValue(filePath,
                    mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
        } catch (IOException e) {
            throw new StorageException("Failed to load books from file: " + filename, e);
        }
    }
}