package books;

import java.util.Objects;

public class Book {

    String bookTitle;
    String bookAuthor;
    String bookType;
    int bookPublicationDate;
    Integer idBook;
    BookStatus status;


    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public void setBookPublicationDate(int bookPublicationDate) {
        this.bookPublicationDate = bookPublicationDate;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Book(Integer idBook, String bookTitle, String bookAuthor, String bookType, int bookPublicationDate, BookStatus status) {
        this.idBook = idBook;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookPublicationDate = bookPublicationDate;
        this.status = status;
    }

    public String getBookType() {
        return bookType;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookPublicationDate() {
        return bookPublicationDate;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public BookStatus getStatus() {
        return status;
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
