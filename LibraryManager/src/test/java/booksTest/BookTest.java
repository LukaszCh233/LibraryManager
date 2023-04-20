package booksTest;

import books.Book;
import books.BookStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookTest {


    @Test
    void name() {
        Book book = new Book(null, null, null, "Fantasy", 13, BookStatus.AVAILABLE);

        Assertions.assertEquals(13, book.getBookPublicationDate());
        Assertions.assertEquals(book.getStatus(), BookStatus.AVAILABLE);
        Assertions.assertEquals("Fantasy",book.getBookType());


    }
}