package api.repository;

import api.models.Book;
import api.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookRepository implements Repository<Book> {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {}

    @Override
    public void add(Book book){
        books.add(book);
    }

    @Override
    public void remove(Book book){
        books.remove(book);
    }

    @Override
    public List<Book> getAll() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public void clear(){
        books.clear();
    }

    public void addAll(List<Book> Books) {
        books.addAll(Books);
    }

}
