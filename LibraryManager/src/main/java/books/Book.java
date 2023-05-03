package books;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Book {

    String bookTitle;
    String bookAuthor;
    String bookType;
    int bookPublicationDate;
    Integer idBook;
    BookStatus status;


    public Book(Integer idBook, String bookTitle, String bookAuthor, String bookType, int bookPublicationDate, BookStatus status) {
        this.idBook = idBook;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookPublicationDate = bookPublicationDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "id: " + idBook + " | " +
                        "books.Book: " + bookTitle + " | " +
                        "Author: " + bookAuthor + " | " +
                        "Type: " + bookType + " | " +
                        "PublicationDate: " + bookPublicationDate + " | " +
                        "Status: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookPublicationDate == book.bookPublicationDate && Objects.equals(bookTitle, book.bookTitle) && Objects.equals(bookAuthor, book.bookAuthor) && Objects.equals(bookType, book.bookType) && Objects.equals(idBook, book.idBook) && status == book.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookTitle, bookAuthor, bookType, bookPublicationDate, idBook, status);
    }

    public boolean isBorrowed() {
        return getStatus().equals(BookStatus.BORROWED);
    }
}
