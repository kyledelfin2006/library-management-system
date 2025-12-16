# library-management-system

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)
![Status](https://img.shields.io/badge/status-active-success)
![License](https://img.shields.io/badge/license-MIT-blue)

A console-based CRUD application for library management implementing core OOP principles.
## ✨ Features

### Core Functionality
- ✅ Add books with automatic ID generation
- ✅ Display all books in formatted table
- ✅ Search by author, genre, or price range
- ✅ Find most expensive book
- ✅ Calculate total library value
- ✅ Count books by genre

### Technical Highlights
- **Immutable Design**: Books are immutable after creation
- **Multi-layer Validation**: Input validated at UI and data layers
- **Exception Handling**: Comprehensive error handling with retry logic
- **Layered Design**: Clear separation of concerns (UI, Logic, Data Model)

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- No external dependencies required

### Installation
```bash
# Clone the repository
git clone https://github.com/kyledelfin2006/library-management-system.git
cd library-management-system

# Compile
javac src/*.java

# Run
java -cp src Main
```

## 📖 Usage

### Adding Books
```
How many books will you add?: 2

Enter book title: 1984
Enter book author: George Orwell
Enter book genre: Fiction
Enter book price: 15.99

Enter book title: The Hobbit
Enter book author: J.R.R. Tolkien
Enter book genre: Fantasy
Enter book price: 12.50
```

### Menu Options
1. Display all books
2. Search books by author
3. Search books by genre
4. Find books cheaper than X
5. Show most expensive book
6. Show total value of all books
7. Count books by genre
0. Exit

## 📸 Sample Output

### Book Display
```
ID                   Title                Author          Genre      Price     
BOOK-0001           1984                 George Orwell   Fiction    15.99     
BOOK-0002           The Hobbit           J.R.R. Tolkien  Fantasy    12.50     
```

### Menu Interface
```
===== Library Menu =====
1 - Display all books
2 - Search books by author
3 - Search books by genre
4 - Find books cheaper than X
5 - Show most expensive book
6 - Show total value of all books
7 - Count books by genre
0 - Exit
```

## 🏗️ Architecture

```
┌─────────────┐
│   Main.java │  ← User Interface Layer
└──────┬──────┘
       │
┌──────▼──────────────┐
│ LibraryManager.java │  ← Business Logic Layer
└──────┬──────────────┘
       │
┌──────▼──────┐
│  Book.java  │  ← Data Model Layer
└─────────────┘
```

### Design Patterns Used
- **Manager Pattern**: LibraryManager handles all business logic
- **Immutability Pattern**: Books cannot be modified after creation
- **Validation Pattern**: Multi-layer input validation

## 🔍 Code Quality

- ✅ Robust error handling
- ✅ Thoroughly tested functionality
- ✅ Comprehensive input validation at multiple layers
- ✅ Clean code principles
- ✅ Professional naming conventions
- ✅ Proper encapsulation

## 🛠️ Technical Details

### Book Class
- Immutable fields with `final` keyword
- Constructor validation
- Unique auto-generated IDs (BOOK-0001, BOOK-0002, ...)
- Formatted toString() for display

### LibraryManager Class
- Case-insensitive search
- Null-safe operations
- Efficient algorithms
- User-friendly error messages

### Main Class
- Exception handling for all user input
- Retry logic for invalid data
- Clean menu interface
- Proper resource management

## 💡 What I Learned

This project demonstrates:
- Object-Oriented Programming principles
- Defensive programming techniques
- Clean code architecture
- Exception handling best practices
- User experience design
- Input validation strategies

## 🚧 Future Enhancements

Potential features to add:
- [ ] Save/load library to file
- [ ] Sort books by different criteria
- [ ] Book borrowing system
- [ ] Database integration (SQLite)
- [ ] GUI interface (JavaFX)
- [ ] Export to CSV/JSON

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

This is a learning project, but feedback is welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📬 Contact

Kyle Delfin - [@kyledelfin2006](https://github.com/kyledelfin2006)

Project Link: [https://github.com/kyledelfin2006/library-management-system](https://github.com/kyledelfin2006/library-management-system)
