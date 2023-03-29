public class BorrowedInfo {
    int idBorrowBook;
    int idBook;
    String bookTitle;
    String bookAuthor;
    int idUser;
    String userName;
    String userLastName;
    String borrowBookDate;
    String returnBookDate;

    public BorrowedInfo(int idBorrowBook, int idBook, String bookTitle, String bookAuthor, int idUser, String userName, String userLastName, String borrowBookDate, String returnBookDate) {
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

    public int getIdBook() {
        return idBook;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getBorrowBookDate() {
        return borrowBookDate;
    }

    public String getReturnBookDate() {
        return returnBookDate;
    }

    public int getIdBorrowBook() {
        return idBorrowBook;
    }

    @Override
    public String toString() {
        return "Id borrowed Book: " + idBorrowBook +  "\n" +
                "Id book: " + idBook + " | " +
                "Book title: " + bookTitle +  " | " +
                "Author: " + bookAuthor +  "\n" +
                "Id reader: " + idUser +  " | " +
                "Name: " + userName +  " | " +
                "Last name: " + userLastName +  "\n" +
                "Borrow Book Date: " + borrowBookDate +  " | " +
                "Return Book Date: " + returnBookDate;
    }
}
