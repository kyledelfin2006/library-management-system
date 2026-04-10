# Library API

> **A REST API built with Java + SparkJava — designed as a hands-on prerequisite before learning Spring Boot.**

![Java](https://img.shields.io/badge/Java-21+-orange)
![SparkJava](https://img.shields.io/badge/SparkJava-2.9.4-red)
![Status](https://img.shields.io/badge/status-working-brightgreen)

---

## Why This Project Exists

Before jumping into Spring Boot, you need to understand what a web framework is actually doing for you. This project builds a fully working REST API using **SparkJava** — a minimal framework that makes the fundamentals explicit:

- How HTTP routes are defined manually
- How JSON is parsed and returned by hand
- How request/response cycles work without magic annotations
- How data is read, stored, and persisted yourself

Once you understand these concepts here, Spring Boot's annotations (`@RestController`, `@GetMapping`, `@RequestBody`, etc.) will make complete sense — because you'll already know what they're abstracting away.

**Do not skip this.** If you go straight to Spring Boot without understanding the foundations, you'll be memorizing annotations without knowing why they exist.

---

## What This API Does

A simple book management REST API. It lets you add, view, search, update, and delete books through HTTP requests. Books are stored in a local `Books.json` file for persistence.

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 21 | Core language |
| SparkJava 2.9.4 | Lightweight HTTP web framework |
| Jackson | JSON serialization and deserialization |
| Maven | Dependency and build management |

---

## Project Structure

```
src/
└── main/java/api/
    ├── controller/
    │   └── LibraryAPI.java         # Route definitions (HTTP endpoints live here)
    ├── manager/
    │   └── LibraryManager.java     # Business logic (validation, orchestration)
    ├── models/
    │   ├── Book.java               # The Book entity/object
    │   └── BookInput.java          # DTO — what the client sends in a request body
    ├── repository/
    │   └── BookRepository.java     # In-memory list of books (the data layer)
    ├── storage/
    │   └── BookStorage.java        # Reads and writes Books.json to disk
    ├── responses/
    │   ├── ApiResponse.java        # Standard success response wrapper
    │   └── ErrorResponse.java      # Standard error response wrapper
    └── util/
        └── BookIDGenerator.java    # Auto-incrementing ID generator
```

---

## How a Request Flows Through the App

Using `POST /api/books` as an example:

```
Client sends POST request with JSON body
        ↓
LibraryAPI.java        → Receives the HTTP request, reads the body
        ↓
BookInput.java         → Maps raw JSON into a Java object (deserialization)
        ↓
LibraryManager.java    → Runs business logic (e.g., validates required fields)
        ↓
BookRepository.java    → Adds the new book to the in-memory list
        ↓
BookStorage.java       → Writes the updated list to Books.json
        ↓
Returns HTTP 201 with a JSON response body
```

Every layer has a single responsibility. This is the foundation of how Spring Boot organizes code too — just with annotations doing the wiring automatically.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/health` | Check if the API is running |
| `GET` | `/api/books` | Get all books |
| `GET` | `/api/books/:id` | Get a single book by ID |
| `POST` | `/api/books` | Add a new book |
| `PATCH` | `/api/books/:id` | Partially update a book |
| `DELETE` | `/api/books/:id` | Delete a book by ID |
| `GET` | `/api/books/search?type=&value=` | Search by author, title, genre, or price |
| `GET` | `/api/books/budget?maxPrice=` | Get books under a given price |
| `GET` | `/api/books/sorted?by=` | Sort books by title, author, price, etc. |
| `GET` | `/api/books/stats` | Get total book count, total value, and most expensive book |

---

## Requirements

- Java 21 or higher
- Maven
- Internet connection (for Maven to download dependencies on first build)

---

## How to Run

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/library-api.git
cd library-api

# 2. Build the project
mvn clean compile

# 3. Start the server
mvn exec:java
```

The API will be available at `http://localhost:8080`.

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

### Update a book (partial update)
```bash
curl -X PATCH http://localhost:8080/api/books/0000 \
  -H "Content-Type: application/json" \
  -d '{"price":12.99}'
```

### Delete a book
```bash
curl -X DELETE http://localhost:8080/api/books/0000
```

### Search books
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

You can also test all of these in **Postman** by setting `http://localhost:8080` as your base URL and creating requests for each endpoint.

---

## Problems Encountered and How They Were Solved

| Problem | Cause | Fix |
|---------|-------|-----|
| Jackson couldn't deserialize the `Book` class | No default (no-arg) constructor | Added a default constructor to `Book.java` |
| IDs were resetting to 0 after every restart | Counter wasn't reading existing data on startup | Load existing books on startup and set the counter to `max ID + 1` |
| Search wasn't finding partial matches | Using exact string match (`equals`) | Switched to `.contains()` for flexible matching |
| Empty `Books.json` caused a crash on load | Jackson failed to parse an empty file | Added a file length check before attempting to read |
| PATCH was overwriting fields with `null` | All fields were being applied regardless of input | Added null checks — only update fields that are actually present in the request |

---

## What You'll Understand After This

After completing and studying this project, you will understand:

- How HTTP methods map to operations (GET = read, POST = create, PATCH = update, DELETE = remove)
- How status codes are chosen and returned (200, 201, 400, 404, 500)
- How request bodies are parsed from raw JSON into Java objects
- How path parameters (`:id`) and query parameters (`?type=`) work
- How to separate routing, logic, data, and storage into distinct layers
- How to handle errors globally instead of catching them everywhere
- How file-based persistence works without a database

All of this maps directly to Spring Boot. The difference is that Spring Boot automates the wiring — you'll understand exactly what it's automating.

---

## License

MIT — use it however you want.

*Built as a learning project. First REST API. Not the last.*
