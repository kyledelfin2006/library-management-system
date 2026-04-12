# Library API

> A REST API built with Java and SparkJava — built as a prerequisite to learning Spring Boot.

![Java](https://img.shields.io/badge/Java-21+-orange)
![SparkJava](https://img.shields.io/badge/SparkJava-2.9.4-red)
![Jackson](https://img.shields.io/badge/Jackson-2.x-blue)
![Status](https://img.shields.io/badge/status-working-brightgreen)

---

## Why I Built This

Before starting Spring Boot, I wanted a solid understanding of what a web framework is actually doing, not just how to use one. 

Spring Boot abstracts a lot: routing, JSON parsing, request/response handling, error management, dependency injection. That's powerful, but if i jump straight into it without understanding the foundations, you end up copying annotations without knowing what problem they solve.

So I built this first, with three specific goals:

**1. Understand HTTP methods and what they mean semantically.**
Not just "GET retrieves data", but why i would use PATCH instead of PUT for partial updates, why DELETE returns 404 when something doesn't exist, and why POST returns 201 instead of 200.

**2. Understand HTTP status codes and when to use them.**
Every response in this API has a deliberately chosen status code. 200 for success, 201 for resource creation, 400 for bad input, 404 for missing resources, 500 for unexpected server errors. These aren't arbitrary, they're part of the HTTP contract.

**3. Understand how a request actually flows through a layered application.**
From the raw HTTP request coming in, to JSON being parsed, to business logic running, to data being persisted, and a structured response going back out. Spring Boot automates this wiring — this project does it manually so you can see every step.

**Did this project accomplish its intended goals for myself?**

Yes, very much so :)


---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 21 | Core language |
| SparkJava 2.9.4 | Lightweight HTTP web framework |
| Jackson | JSON serialization and deserialization |
| Maven | Dependency and build management |
| JUnit 5 | Unit testing |

---

## Project Structure

```
src/
└── main/java/api/
    ├── controller/
    │   └── LibraryAPI.java         # Entry point. Defines all HTTP routes.
    ├── manager/
    │   └── LibraryManager.java     # Business logic. Validates input, orchestrates operations.
    ├── models/
    │   ├── Book.java               # The Book entity. Validates its own fields on construction.
    │   └── BookInput.java          # DTO. Represents what the client sends in a request body.
    ├── repository/
    │   ├── Repository.java         # Generic interface: add, remove, getAll, clear.
    │   └── BookRepository.java     # In-memory list. Implements Repository<Book>.
    ├── storage/
    │   ├── Storage.java            # Generic interface: save, load.
    │   └── BookStorage.java        # JSON file persistence. Implements Storage<Book>.
    ├── responses/
    │   ├── ApiResponse.java        # Wraps successful responses: success, message, data, timestamp.
    │   └── ErrorResponse.java      # Wraps error responses: error, details, statusCode, timestamp.
    ├── exceptions/
    │   └── StorageException.java   # Custom unchecked exception for file I/O failures.
    └── util/
        └── BookIDGenerator.java    # Thread-safe auto-incrementing ID generator using AtomicInteger.
```

---

## Architecture

This project uses a **layered architecture**. Each layer has a single responsibility and only talks to the layer directly below it.

```
┌─────────────────────────────────┐
│         LibraryAPI.java         │  ← HTTP layer. Routes, status codes, JSON in/out.
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│       LibraryManager.java       │  ← Business logic. Validation, search, sort, stats.
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│      BookRepository.java        │  ← In-memory data. Unmodifiable list via Collections.
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│       BookStorage.java          │  ← File persistence. Reads/writes Books.json via Jackson.
└─────────────────────────────────┘
```

**Why this separation matters:** In Spring Boot, this same pattern exists — `@RestController` → `@Service` → `@Repository` — but the framework wires it together automatically. Here, the wiring is explicit and manual, so you can see exactly what each layer is responsible for.

---

## How a Request Flows Through the App

Using `POST /api/books` as a concrete example:

```
1. Client sends:
   POST /api/books
   Content-Type: application/json
   Body: {"title":"1984","author":"Orwell","genre":"Fiction","price":15.99}

2. LibraryAPI.java
   → SparkJava matches the route
   → Jackson calls mapper.readValue(req.body(), BookInput.class)
   → Raw JSON becomes a BookInput object (the DTO)

3. LibraryManager.java
   → validateBookInput(input) checks title, author, genre are not null/empty
   → price must be > 0
   → Throws IllegalArgumentException if anything fails

4. Book.java
   → new Book(title, author, genre, price) is called
   → Book validates its own fields again on construction
   → BookIDGenerator.generateNextID() assigns a zero-padded ID ("0003")

5. BookRepository.java
   → repository.add(newBook) appends the book to the in-memory list
   → getAll() returns Collections.unmodifiableList() — the list cannot be modified externally

6. BookStorage.java
   → storage.save(repository.getAll()) writes the full list to Books.json
   → Throws StorageException (extends RuntimeException) on I/O failure

7. LibraryAPI.java
   → res.status(201) — 201 Created, not 200 OK
   → Returns: {"success":true,"message":"Book Added Successfully","data":{...},"timestamp":...}
```

---

## Key Design Decisions

**`BookInput` vs `Book` (DTO pattern)**
`BookInput` is what the client sends. `Book` is what the system stores. They are kept separate because a client should never be able to set an ID directly — the ID is always generated server-side by `BookIDGenerator`. `BookInput` is also reused for PATCH requests, where all fields are optional.

**`Repository<T>` and `Storage<T>` interfaces**
Both are generic interfaces. `Repository<T>` defines `add`, `remove`, `getAll`, `clear`. `Storage<T>` defines `save` and `load`. Using interfaces means the implementation can be swapped — e.g., replacing `BookStorage` with a database-backed implementation — without changing `LibraryManager`.

**`BookIDGenerator` with `AtomicInteger`**
IDs are generated using `AtomicInteger.getAndIncrement()`, which makes the read-and-increment a single atomic operation. This prevents duplicate IDs under concurrent requests. On startup, `LibraryManager` reads all existing books, finds the highest ID, and sets the counter to `maxId + 1` — so IDs never reset after a restart.

**`Collections.unmodifiableList()` in `BookRepository`**
`getAll()` returns a read-only view of the internal list. External callers cannot accidentally modify repository state by holding a reference to the list.

**`StorageException` as an unchecked exception**
`BookStorage` wraps `IOException` in a custom `StorageException extends RuntimeException`. This means callers don't have to declare `throws` everywhere, but file failures still bubble up with meaningful context rather than being swallowed.

**PATCH only updates provided fields**
In `LibraryManager.patchBook()`, each field is only updated if it is non-null and non-empty. A PATCH request with just `{"price": 12.99}` will update the price and leave title, author, and genre untouched.

**Consistent response shape**
Every successful response is wrapped in `ApiResponse<T>` (success, message, data, timestamp). Every error is wrapped in `ErrorResponse` (error, details, statusCode, timestamp). This consistency means clients always know what shape to expect regardless of which endpoint they hit.

---

## API Endpoints

| Method | Endpoint | Status Codes | Description |
|--------|----------|-------------|-------------|
| `GET` | `/api/health` | 200 | Check if the API is running |
| `GET` | `/api/books` | 200 | Get all books |
| `GET` | `/api/books/:id` | 200, 404 | Get a single book by ID |
| `POST` | `/api/books` | 201, 400 | Add a new book |
| `PATCH` | `/api/books/:id` | 200, 400, 404 | Partially update a book |
| `DELETE` | `/api/books/:id` | 200, 404 | Delete a book by ID |
| `GET` | `/api/books/search?type=&value=` | 200, 400 | Search by author, title, genre, or price |
| `GET` | `/api/books/budget?maxPrice=` | 200, 400 | Get books under a given price |
| `GET` | `/api/books/sorted?by=` | 200 | Sort by title, author, genre, price, or id |
| `GET` | `/api/books/stats` | 200 | Total books, total value, most expensive book |

**Search types:** `author`, `title`, `genre`, `price` — all use partial/contains matching except price (exact match with float tolerance of `0.0001`).

**Sort fields:** `title`, `author`, `genre`, `price`, `id` — defaults to `title` if no field is provided or the field is unrecognized.

---

## Requirements

- Java 21 or higher
- Maven
- Internet connection on first build (Maven downloads dependencies)

---

## How to Run

```bash
# Clone the repository
git clone https://github.com/yourusername/library-api.git
cd library-api

# Build the project
mvn clean compile

# Start the server
mvn exec:java
```

The API starts at `http://localhost:8080`. A `Books.json` file will be created automatically in the project root on the first write.

---

## Testing with cURL

### Health check
```bash
curl http://localhost:8080/api/health
```

### Get all books
```bash
curl http://localhost:8080/api/books
```

### Add a new book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"1984","author":"Orwell","genre":"Fiction","price":15.99}'
```

### Get a book by ID
```bash
curl http://localhost:8080/api/books/0000
```

### Partially update a book
```bash
curl -X PATCH http://localhost:8080/api/books/0000 \
  -H "Content-Type: application/json" \
  -d '{"price":12.99}'
```

### Delete a book
```bash
curl -X DELETE http://localhost:8080/api/books/0000
```

### Search by author (partial match)
```bash
curl "http://localhost:8080/api/books/search?type=author&value=Orwell"
```

### Books within a budget
```bash
curl "http://localhost:8080/api/books/budget?maxPrice=20"
```

### Sorted books
```bash
curl "http://localhost:8080/api/books/sorted?by=price"
```

### Library stats
```bash
curl http://localhost:8080/api/books/stats
```

---

## Running Tests

```bash
mvn test
```

Tests are written with JUnit 5 and cover:

**`BookTest`** — verifies that `Book` validates its own fields on construction: empty title throws `IllegalArgumentException`, null author throws, negative price throws, zero price throws, and that two books created sequentially receive different IDs.

**`BookIDGeneratorTest`** — verifies sequential generation, zero-padding format (`"0000"`, `"0001"`), correct behaviour past 4 digits (`"10000"`), resettable counter via `setNextId()`, and thread safety: 10 threads each generating 100 IDs concurrently with no duplicates, verified using `AtomicInteger`.

---

## Problems Encountered and How They Were Fixed

| Problem | Cause | Fix |
|---------|-------|-----|
| Jackson couldn't deserialize `Book` from JSON | No default (no-arg) constructor | Added `public Book() {}` — Jackson requires it to instantiate the object before setting fields via setters |
| IDs reset to `0000` after every restart | Counter always initialized to 0 | On startup, `LibraryManager` scans all loaded books, finds the max ID, and calls `BookIDGenerator.setNextId(maxId + 1)` |
| Search returning no results | Using exact `.equals()` match | Switched to `.toLowerCase().contains(value)` for partial matching on author, title, and genre |
| Empty `Books.json` caused a crash on load | Jackson failed to parse a zero-byte file | `BookStorage.load()` checks `filePath.length() == 0` and returns an empty list before attempting to read |
| PATCH was overwriting fields with `null` | All fields were applied regardless of whether they were sent | Each field in `patchBook()` is only applied if non-null and non-empty |
| Potential duplicate IDs under concurrent requests | Plain `int` counter with non-atomic read-then-increment | Replaced with `AtomicInteger.getAndIncrement()` — read and increment happen as one indivisible operation |

---

## License

MIT — use it however you want.

*First REST API. Built to understand the foundations before Spring Boot.*
