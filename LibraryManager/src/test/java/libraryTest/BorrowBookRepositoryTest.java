package libraryTest;

import books.Book;
import books.BookRepository;
import books.BookStatus;
import database.Database;
import library.BorrowBookRepository;
import library.BorrowedInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.User;
import users.UserRepository;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BorrowBookRepositoryTest {
    private static final String DATABASE_NAME = "test.db";
    Database database;
    BookRepository bookRepository;
    UserRepository userRepository;
    BorrowBookRepository borrowBookRepository;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        bookRepository = new BookRepository(database);
        userRepository = new UserRepository(database);
        borrowBookRepository = new BorrowBookRepository(database);
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
    void writeBorrowBookTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        Date borrowDate = Date.valueOf("2023-04-16");
        Date returnDate = Date.valueOf("2023-04-30");

        //When
        bookRepository.saveBook(book);
        userRepository.createUser(user);
        borrowBookRepository.writeBorrowBookToDatabase(book.getIdBook(), user.getIdUser(), borrowDate, returnDate);

        //Then
        Optional<Book> foundBook = bookRepository.findBook(book.getIdBook());
        Optional<User> foundUser = userRepository.findUser(user.getIdUser());
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        BorrowedInfo foundBorrowBook = new ArrayList<>(borrowedBooks).get(0);

        Assertions.assertEquals(1, borrowedBooks.size());
        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(book.getIdBook(), foundBorrowBook.getIdBorrowBook());
        Assertions.assertEquals(user.getIdUser(), foundBorrowBook.getIdUser());
        Assertions.assertEquals(borrowDate, foundBorrowBook.getBorrowBookDate());
        Assertions.assertEquals(returnDate, foundBorrowBook.getReturnBookDate());

    }

    @Test
    void loadBorrowedBooksTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        Book book1 = new Book(2, "Titus Andronicus1", "William Shakespeare1", null, 0, BookStatus.AVAILABLE);
        Date borrowDate = Date.valueOf("2023-04-16");
        Date returnDate = Date.valueOf("2023-04-30");
        BorrowedInfo borrowedInfo = new BorrowedInfo(1, book.getIdBook(), book.getBookTitle(), book.getBookAuthor(), 1, user.getUserName(), user.getUserLastName(), borrowDate, returnDate);
        BorrowedInfo borrowedInfo1 = new BorrowedInfo(2, book1.getIdBook(), book1.getBookTitle(), book1.getBookAuthor(), 1, user.getUserName(), user.getUserLastName(), borrowDate, returnDate);

        //When
        bookRepository.saveBook(book);
        bookRepository.saveBook(book1);
        userRepository.createUser(user);
        borrowBookRepository.writeBorrowBookToDatabase(borrowedInfo.getIdBook(), borrowedInfo.getIdUser(), borrowedInfo.getBorrowBookDate(), borrowedInfo.getReturnBookDate());
        borrowBookRepository.writeBorrowBookToDatabase(borrowedInfo1.getIdBook(), borrowedInfo1.getIdUser(), borrowedInfo1.getBorrowBookDate(), borrowedInfo1.getReturnBookDate());

        //Then
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        Assertions.assertEquals(2, borrowedBooks.size());
        Assertions.assertTrue(borrowedBooks.contains(borrowedInfo));
        Assertions.assertTrue(borrowedBooks.contains(borrowedInfo1));
    }

    @Test
    void returnBookTest() {
        //Given
        Book book = new Book(1, "Titus Andronicus", "William Shakespeare", null, 0, BookStatus.AVAILABLE);
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        Date borrowDate = Date.valueOf("2023-04-16");
        Date returnDate = Date.valueOf("2023-04-30");
        BorrowedInfo borrowedInfo = new BorrowedInfo(1, book.getIdBook(), book.getBookTitle(), book.getBookAuthor(), 1, user.getUserName(), user.getUserLastName(), borrowDate, returnDate);

        //When
        bookRepository.saveBook(book);
        userRepository.createUser(user);
        borrowBookRepository.writeBorrowBookToDatabase(borrowedInfo.getIdBook(), borrowedInfo.getIdUser(), borrowedInfo.getBorrowBookDate(), borrowedInfo.getReturnBookDate());
        borrowBookRepository.returnBook(book.getIdBook());

        //Then
        Collection<BorrowedInfo> borrowedBooks = borrowBookRepository.loadBorrowBooksDatabase();
        Assertions.assertFalse(borrowedBooks.contains(borrowedInfo));
        Assertions.assertEquals(book.getStatus(), BookStatus.AVAILABLE);

    }
}
