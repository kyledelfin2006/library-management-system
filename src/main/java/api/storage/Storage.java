package api.storage;

import java.io.IOException;
import java.util.List;

public interface Storage<T> {
    void save(List<T> items);
    List<T> load();
}