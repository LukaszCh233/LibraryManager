package libraryTest;

import books.Book;
import books.BookRepository;
import books.BookStatus;
import books.BooksFunction;
import database.Database;
import library.BorrowBookFunction;
import library.BorrowBookRepository;
import library.BorrowedInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.User;
import users.UserFunction;
import users.UserRepository;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

public class BorrowedBookFunctionTest {
    private static final String DATABASE_NAME = "test.db";
    Database database;
    BookRepository bookRepository;
    UserRepository userRepository;
    BorrowBookRepository borrowBookRepository;
    BorrowBookFunction borrowBookFunction;
    BooksFunction booksFunction;
    UserFunction userFunction;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        bookRepository = new BookRepository(database);
        userRepository = new UserRepository(database);
        borrowBookRepository = new BorrowBookRepository(database);
        booksFunction = new BooksFunction(database);
        userFunction = new UserFunction(database);
        borrowBookFunction = new BorrowBookFunction(database, booksFunction, userFunction);
    }

    @AfterEach
    void deleteDatabase() throws Exception {
        database.close();
        File dbFile = new File(DATABASE_NAME);
        boolean deleted = dbFile.delete();
        if (!deleted) {
            throw new RuntimeException("Failed to delete database file: " + DATABASE_NAME);
        }
    }

    @Test
    void BorrowBookTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        String input = "yes\n1\n1\n2023-03-20\n2023-04-20\nno\n";

        //When
        userRepository.createUser(user);
        bookRepository.saveBook(book);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        borrowBookFunction.borrowBookFormLibrary();

        //Then
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        Assertions.assertEquals(1, borrowedBooks.size());
    }

    @Test
    void returnBookTest() {
        //Given
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Date borrowDate = Date.valueOf("2023-04-16");
        Date returnDate = Date.valueOf("2023-04-30");
        BorrowedInfo borrowedInfo = new BorrowedInfo(1, book.getIdBook(), book.getBookTitle(), book.getBookAuthor(), 1, user.getUserName(), user.getUserLastName(), borrowDate, returnDate);
        String input = "yes\n" + book.getIdBook() + "\nno\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        //When
        bookRepository.saveBook(book);
        userRepository.createUser(user);
        borrowBookRepository.writeBorrowBookToDatabase(book.getIdBook(), user.getIdUser(), borrowDate, returnDate);
        borrowBookFunction.returnBookToLibrary();

        //Then
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        Assertions.assertFalse(borrowedBooks.contains(borrowedInfo));
        Assertions.assertTrue(borrowedBooks.isEmpty());
        Assertions.assertEquals(book.getStatus(), BookStatus.AVAILABLE);
    }

    @Test
    void borrowedBookListViewTest() {
        //Given
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Date borrowDate = Date.valueOf("2023-04-16");
        Date returnDate = Date.valueOf("2023-04-30");
        BorrowedInfo borrowedInfo = new BorrowedInfo(1, book.getIdBook(), book.getBookTitle(), book.getBookAuthor(), 1, user.getUserName(), user.getUserLastName(), borrowDate, returnDate);

        //When
        bookRepository.saveBook(book);
        userRepository.createUser(user);
        borrowBookRepository.writeBorrowBookToDatabase(book.getIdBook(), user.getIdUser(), borrowDate, returnDate);
        borrowBookFunction.borrowedBookListView();

        //Then
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        Assertions.assertEquals(1, borrowedBooks.size());
        Assertions.assertTrue(borrowedBooks.contains(borrowedInfo));
    }
}
