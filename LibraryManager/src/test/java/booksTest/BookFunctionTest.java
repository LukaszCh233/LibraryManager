package booksTest;

import books.Book;
import books.BookRepository;
import books.BookStatus;
import books.BooksFunction;
import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

public class BookFunctionTest {

    private static final String DATABASE_NAME = "test.db";
    Database database;
    BookRepository bookRepository;
    BooksFunction booksFunction;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        bookRepository = new BookRepository(database);
        booksFunction = new BooksFunction(database);
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
    void shouldAddBookTest() {
        //Given
        String input = "TestTitle\nTestAuthor\nTestType\n2000\nyes\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // when
        booksFunction.addBookToLibrary();

        // then
        Collection<Book> books = bookRepository.loadBook();
        System.out.println("Books in library: " + books);
        Assertions.assertEquals(1, books.size());
        Book book = books.iterator().next();
        Assertions.assertEquals("TestTitle", book.getBookTitle());
        Assertions.assertEquals("TestAuthor", book.getBookAuthor());
        Assertions.assertEquals("TestType", book.getBookType());
    }

    @Test
    void shouldDeleteChosenBookTest() {
        //Given
        Book book = new Book(1, "TestTitle", "TestAuthor", "TestType", 2000, BookStatus.AVAILABLE);

        //When
        bookRepository.saveBook(book);

        String input = "yes\n" + book.getIdBook() + "\nno\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        booksFunction.deleteBookFromLibrary();

        //Then
        Collection<Book> books = bookRepository.loadBook();
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    void shouldDisplayBooksFromDatabaseTest() {
        //Given
        Book book = new Book(1, "TestTitle", "TestAuthor", "TestType", 2000, BookStatus.AVAILABLE);

        //When
        bookRepository.saveBook(book);
        booksFunction.bookListView();

        //Then
        Collection<Book> books = bookRepository.loadBook();
        Assertions.assertEquals(1, books.size());
        Assertions.assertTrue(books.contains(book));
    }
}





