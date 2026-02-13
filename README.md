
# Library Management System

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)
![Status](https://img.shields.io/badge/status-educational-blue)
![License](https://img.shields.io/badge/license-MIT-green)

A console-based library management system built in Java, demonstrating object-oriented programming principles and clean code architecture.

> **Educational Project**: This system was developed as a learning exercise to practice OOP concepts, input validation, separation of concerns, and defensive programming techniques.

## Overview

This Library Management System provides a command-line interface for managing a collection of books. Users can add books, view the collection, search and filter books, and perform various operations like finding the most expensive book or calculating the total library value.

## Features

### Core Functionality
- **Add Books**: Add single or multiple books (up to 100 at a time) with automatic ID generation
- **Display All Books**: View entire collection in a formatted table
- **Remove Books**: Delete books by author, title, genre, or price
- **Search Books**: Find books by author, title, genre, or exact price
- **Budget Filter**: Find all books within a specified price range
- **Sort Books**: Sort collection by title, author, genre, price, or ID
- **Most Expensive Book**: Instantly identify the highest-priced book
- **Total Value**: Calculate the combined value of all books in the library

### Technical Highlights
- **Immutable Book Objects**: Books cannot be modified after creation, ensuring data integrity
- **Multi-Layer Validation**: Input validated at both UI and data model layers
- **Comprehensive Error Handling**: Graceful handling of invalid inputs with user-friendly retry logic
- **Separation of Concerns**: Clear architectural layers (UI, Business Logic, Data Model, Validation)
- **Defensive Programming**: Null-safe operations and boundary checking throughout

## Architecture

```
┌─────────────────────┐
│     Main.java       │  ← User Interface Layer
│  (Menu & Display)   │     Handles user interaction
└──────────┬──────────┘
           │
┌──────────▼──────────────┐
│ InputValidator.java     │  ← Input Validation Layer
│  (Input Sanitization)   │     Validates and sanitizes user input
└──────────┬──────────────┘
           │
┌──────────▼──────────────┐
│  LibraryManager.java    │  ← Business Logic Layer
│  (CRUD Operations)      │     Manages library operations
└──────────┬──────────────┘
           │
┌──────────▼──────────────┐
│      Book.java          │  ← Data Model Layer
│  (Immutable Entity)     │     Represents book entities
└─────────────────────────┘
```

### Design Patterns & Principles

**Object-Oriented Principles**:
- **Encapsulation**: Private fields with public getters, no setters for immutability
- **Single Responsibility**: Each class has one clear purpose
- **Information Hiding**: Implementation details hidden from users

**Design Patterns**:
- **Manager Pattern**: `LibraryManager` centralizes business logic
- **Immutability Pattern**: Books use `final` fields and have no mutators
- **Validator Pattern**: Separate `InputValidator` class for reusable validation logic

## Getting Started

### Prerequisites
- Java 17 or higher
- No external dependencies required

### Installation & Running

```bash
# Clone the repository
git clone https://github.com/kyledelfin2006/library-management-system.git
cd library-management-system

# Compile all Java files
javac *.java

# Run the application
java Main
```

## Usage Guide

### Main Menu
```
===== LIBRARY MANAGEMENT SYSTEM =====
=====================================
=========== by: Kyle D. =============

1 - Add Book
2 - View All Books
3 - Remove Book
4 - Find Books Within Budget
5 - View Most Expensive Book
6 - View Total Book Value
7 - Sort Books
8 - Search Books
0 - Exit
```

### Example Workflow

**Adding Books:**
```
How many books to add?: 2

Book 1
Enter book title: 1984
Enter book author: George Orwell
Enter book genre: Dystopian
Enter book price: 15.99

Book 2
Enter book title: The Hobbit
Enter book author: J.R.R. Tolkien
Enter book genre: Fantasy
Enter book price: 12.50
```

**Viewing All Books:**
```
ID         Title                     Author               Genre           Price
BOOK-0001  1984                      George Orwell        Dystopian       15.99
BOOK-0002  The Hobbit                J.R.R. Tolkien       Fantasy         12.50
TOTAL BOOKS: 2
```

**Searching Books:**
```
Enter search type (author / genre / title/price): author
Enter author to search: George Orwell

Found 1 book/s
BOOK-0001  1984                      George Orwell        Dystopian       15.99
```

**Sorting Books:**
```
Field to sort by: price
Books sorted by price
ID         Title                     Author               Genre           Price
BOOK-0002  The Hobbit                J.R.R. Tolkien       Fantasy         12.50
BOOK-0001  1984                      George Orwell        Dystopian       15.99
```

##  Code Quality Features

### Input Validation
- **Multi-layer validation**: Both at UI level and within Book constructor
- **Type checking**: Ensures numeric inputs are valid numbers
- **Boundary validation**: Prevents negative prices, empty strings, and excessive quantities
- **Retry logic**: Prompts users again on invalid input rather than crashing

### Error Handling
- Catches `InputMismatchException` for invalid data types
- Catches `NumberFormatException` for parsing errors
- Validates null and empty strings before processing
- User-friendly error messages guide users to correct input

### Code Organization
- **Clear naming conventions**: Methods and variables have descriptive names
- **Comments**: Strategic comments explain complex logic
- **Consistent formatting**: Proper indentation and spacing throughout
- **Modular design**: Each method has a single, well-defined purpose

##  Learning Objectives Demonstrated

This project showcases understanding of:

1. **Object-Oriented Programming**
   - Classes and objects
   - Encapsulation and data hiding
   - Immutability and final fields
   - Constructor validation

2. **Data Structures**
   - LinkedList for dynamic book storage
   - ArrayList for temporary collections
   - List interface for flexibility

3. **Algorithms**
   - Linear search for finding books
   - Comparator-based sorting
   - Sequential validation

4. **Software Design**
   - Separation of concerns
   - Layered architecture
   - Single Responsibility Principle
   - Defensive programming

5. **User Experience**
   - Clear menu navigation
   - Formatted output tables
   - Helpful error messages
   - Input retry mechanisms

##  Technical Implementation Details

### Book Class (`Book.java`)
- **Immutable design**: All fields are `final`
- **Auto-generated IDs**: Format `BOOK-0001`, `BOOK-0002`, etc.
- **Constructor validation**: Rejects null/empty strings and non-positive prices
- **Formatted output**: `toString()` produces aligned table rows

### LibraryManager Class (`LibraryManager.java`)
- **LinkedList storage**: Efficient for adding/removing books
- **Case-insensitive search**: Uses `equalsIgnoreCase()` for user-friendly matching
- **Flexible sorting**: Supports sorting by any book attribute
- **Safe deletion**: Uses temporary list to avoid ConcurrentModificationException

### InputValidator Class (`InputValidator.java`)
- **Reusable validation**: Can be used across different parts of the application
- **Method overloading**: `getValidDouble()` with and without minimum value
- **Loop-based validation**: Continues prompting until valid input received
- **Scanner management**: Proper handling of scanner buffer

### Main Class (`Main.java`)
- **Menu-driven interface**: Clear numbered options
- **Input validation**: Ensures menu choice is within valid range
- **Switch statement**: Clean routing to appropriate manager methods
- **Resource cleanup**: Closes scanner on exit

## Potential Enhancements

Future features to explore:
- [ ] Persistent storage (save/load from files or database)
- [ ] Book borrowing/return system with due dates
- [ ] ISBN validation and duplicate detection
- [ ] Advanced filtering (multiple criteria simultaneously)
- [ ] Export functionality (CSV, JSON, XML)
- [ ] GUI version using JavaFX or Swing
- [ ] Unit tests with JUnit
- [ ] Multi-user support with authentication

##  License

This project is licensed under the MIT License - free to use for educational purposes.

##  Contributing

This is primarily an educational project, but suggestions and feedback are always welcome! Feel free to:
- Open issues for bugs or improvements
- Fork and experiment with your own features
- Share your learning experience

## Contacts

- [@kyledelfin2006](https://github.com/kyledelfin2006)

**Project Link**: [https://github.com/kyledelfin2006/library-management-system](https://github.com/kyledelfin2006/library-management-system)

---

*Built with intent as a learning project to master Java OOP and clean code principles*
