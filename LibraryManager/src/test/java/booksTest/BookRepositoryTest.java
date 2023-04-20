package booksTest;

import books.Book;
import books.BookRepository;
import books.BookStatus;
import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BookRepositoryTest {

    private static final String DATABASE_NAME = "test.db";
    Database database;
    BookRepository bookRepository;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        bookRepository = new BookRepository(database);
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

    // exampleTest
    @Test
    void shouldCreateBookGivenSampleBook() {
        // given
        Book book = new Book(null,
                "Titus Andronicus",
                "William Shakespeare",
                null,
                0,
                BookStatus.AVAILABLE);

        // when
        bookRepository.saveBook(book);

        // then
        Collection<Book> allBooks = bookRepository.loadBook();

        Assertions.assertEquals(1, allBooks.size());
        // Not the same as original!
        Book foundBook = new ArrayList<>(allBooks).get(0);
        Assertions.assertEquals(1, foundBook.getIdBook());
        Assertions.assertEquals("Titus Andronicus", foundBook.getBookTitle());
        Assertions.assertEquals("William Shakespeare", foundBook.getBookAuthor());
        Assertions.assertEquals(BookStatus.AVAILABLE, foundBook.getStatus());
    }

    @Test
    void shouldSaveBookToDatabaseTest() {
        Book book = new Book(1,
                "Titus Andronicus",
                "William Shakespeare",
                null,
                0,
                BookStatus.AVAILABLE);

        // when
        bookRepository.saveBook(book);

        // then

        Optional<Book> result = bookRepository.findBook(book.getIdBook());
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void shouldRemoveChosenBookFromDatabaseTest() {
        // given
        Book book = new Book(1,
                "Titus Andronicus",
                "William Shakespeare",
                null,
                0,
                BookStatus.AVAILABLE);
        //When
        bookRepository.saveBook(book);
        bookRepository.removeBookFromDatabase(book.getIdBook());

        //Then
        Optional<Book> removeBook = bookRepository.findBook(book.getIdBook());
        Assertions.assertFalse(removeBook.isPresent());
    }

    @Test
    void shouldLoadBooksFromDatabase() {
        //Given
        Book book = new Book(1, "a", "a1", "a3", 2000, BookStatus.AVAILABLE);
        Book book1 = new Book(2, "b", "b1", "b3", 2000, BookStatus.AVAILABLE);

        //When
        bookRepository.saveBook(book);
        bookRepository.saveBook(book1);

        //Then
        Collection<Book> books = bookRepository.loadBook();
        Assertions.assertEquals(2, books.size());
        Assertions.assertTrue(books.contains(book));
        Assertions.assertTrue(books.contains(book1));
    }

    @Test
    void shouldFindChosenBookWhenThereAreMoreThanOne() {
        //Given
        Book book = new Book(1, "a", "a1", "a3", 2000, BookStatus.AVAILABLE);
        Book book1 = new Book(2, "b", "b1", "b3", 2000, BookStatus.AVAILABLE);

        //When
        bookRepository.saveBook(book);
        bookRepository.saveBook(book1);

        //Then
        Optional<Book> foundBook = bookRepository.findBook(book.getIdBook());
        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertEquals(book, foundBook.get());
    }

    @Test
    void shouldUpdateBookStatusTest() {
        //Given
        Book book = new Book(1, "a", "a1", "a3", 2000, BookStatus.AVAILABLE);

        //When
        bookRepository.saveBook(book);

        //Then
        Assertions.assertTrue(bookRepository.updateBookStatus(book.getIdBook(), BookStatus.BORROWED));
        Optional<Book> foundBook = bookRepository.findBook(book.getIdBook());
        Assertions.assertTrue(foundBook.isPresent());
        Assertions.assertEquals(BookStatus.BORROWED, foundBook.get().getStatus());
    }
}


