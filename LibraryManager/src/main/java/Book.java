import java.security.PublicKey;

public class Book {

     String bookTitle;
     String bookAuthor;
     String bookType;
     int bookPublicationDate;
     int idBook;
     BookStatus status;




     public Book(int idBook, String bookTitle, String bookAuthor, String bookType, int bookPublicationDate) {
          this.idBook = idBook;
          this.bookTitle = bookTitle;
          this.bookAuthor = bookAuthor;
          this.bookType = bookType;
          this.bookPublicationDate = bookPublicationDate;
     }

     public Book() {

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

     public int getIdBook() {
          return idBook;
     }

     public BookStatus getStatus() {
          return status;
     }

     @Override
     public String toString() {
          return
                  "id: " + idBook +  " | " +
                  "Book: " + bookTitle + " | " +
                  "Author: " + bookAuthor +  " | " +
                  "Type: " + bookType +  " | " +
                  "PublicationDate: " + bookPublicationDate + " | " +
                  "Status: " + status;

     }
}
