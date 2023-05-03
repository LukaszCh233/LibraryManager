package library;

import lombok.Getter;

import java.sql.Date;
import java.util.Objects;

@Getter
public class BorrowedInfo {
    int idBorrowBook;
    int idBook;
    String bookTitle;
    String bookAuthor;
    int idUser;
    String userName;
    String userLastName;
    Date borrowBookDate;
    Date returnBookDate;

    public BorrowedInfo(int idBorrowBook, int idBook, String bookTitle, String bookAuthor, int idUser, String userName, String userLastName, Date borrowBookDate, Date returnBookDate) {
        this.idBorrowBook = idBorrowBook;
        this.idBook = idBook;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.borrowBookDate = borrowBookDate;
        this.returnBookDate = returnBookDate;
    }

    @Override
    public String toString() {
        return "Id borrowed books: " + idBorrowBook +  "\n" +
                "Id book: " + idBook + " | " +
                "books title: " + bookTitle +  " | " +
                "Author: " + bookAuthor +  "\n" +
                "Id reader: " + idUser +  " | " +
                "Name: " + userName +  " | " +
                "Last name: " + userLastName +  "\n" +
                "Borrow books: " + borrowBookDate +  " | " +
                "Return books: " + returnBookDate + "\n **************************************************************";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowedInfo that)) return false;
        return idBorrowBook == that.idBorrowBook && idBook == that.idBook && idUser == that.idUser && Objects.equals(bookTitle, that.bookTitle) && Objects.equals(bookAuthor, that.bookAuthor) && Objects.equals(userName, that.userName) && Objects.equals(userLastName, that.userLastName) && Objects.equals(borrowBookDate, that.borrowBookDate) && Objects.equals(returnBookDate, that.returnBookDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBorrowBook, idBook, bookTitle, bookAuthor, idUser, userName, userLastName, borrowBookDate, returnBookDate);
    }
}
