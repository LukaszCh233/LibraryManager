import books.BooksFunction;
import database.Database;
import library.BorrowBookFunction;
import users.UserFunction;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuLibrary implements AutoCloseable {
    BooksFunction booksFunction;
    UserFunction userFunction;
    BorrowBookFunction borrowBookFunction;
    Database database;

    public MenuLibrary() throws SQLException {
        database = new Database();
        booksFunction = new BooksFunction(database);
        userFunction = new UserFunction(database);
        borrowBookFunction = new BorrowBookFunction(database, booksFunction, userFunction);
    }

    public void useMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Library menu:");
            System.out.println("1: Add book to library");
            System.out.println("2: List the books in the library");
            System.out.println("3: Delete book form library");
            System.out.println("4: Borrow a book");
            System.out.println("5: Return the book");
            System.out.println("6: View a list of borrowed books");
            System.out.println("7: Add new reader");
            System.out.println("8: Delete reader from library");
            System.out.println("9: View the list of readers");
            System.out.println("0: Good bye :)");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    booksFunction.addBookToLibrary();
                    break;
                case "2":
                    booksFunction.bookListView();
                    break;
                case "3":
                    booksFunction.deleteBookFromLibrary();
                    break;
                case "4":
                    borrowBookFunction.borrowBookFormLibrary();
                    break;
                case "5":
                    borrowBookFunction.returnBookToLibrary();
                    break;
                case "6":
                    borrowBookFunction.borrowedBookListView();
                    break;
                case "7":
                    userFunction.addReaderToList();
                    break;
                case "8":
                    userFunction.deleteReaderFromLibrary();
                    break;
                case "9":
                    userFunction.readerListView();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Bad choice, try again");
                    break;

            }
        } while (!choice.equalsIgnoreCase("0"));
    }

    @Override
    public void close() throws Exception {
        database.close();

    }
}
