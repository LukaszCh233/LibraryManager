package library;


import books.BookRepository;
import books.BooksFunction;
import database.Database;
import users.UserFunction;
import java.sql.Date;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BorrowBookFunction {
    BorrowBookRepository borrowBookRepository;
    BooksFunction booksFunction;
    UserFunction userFunction;
    BookRepository bookRepository;

    public BorrowBookFunction(Database database, BooksFunction booksFunction, UserFunction userFunction) {
        this.borrowBookRepository = new BorrowBookRepository(database);
        this.booksFunction = booksFunction;
        this.userFunction = userFunction;
        this.bookRepository = new BookRepository(database);
    }

    public void borrowBookFormLibrary() {

        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Borrow book -> yes/no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                try {
                    booksFunction.bookListView();
                    System.out.println("Id book:");
                    int idBook = scanner.nextInt();
                    userFunction.readerListView();
                    System.out.println("Id reader:");
                    int idUser = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Borrow book data yyyy-mm-dd:");
                    Date borrowData = Date.valueOf(scanner.nextLine());
                    System.out.println("Return book data yyyy-mm-dd:");
                    Date returnData = Date.valueOf(scanner.nextLine());
                    borrowBookRepository.writeBorrowBookToDatabase(idBook, idUser, borrowData, returnData);

                } catch (InputMismatchException e) {
                    System.out.println("Bad type");
                } catch (IllegalArgumentException e) {
                    System.out.println("Bad form try yyyy-mm-dd ");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        while (!choice.equalsIgnoreCase("no"));
    }

    public void borrowedBookListView() {
        System.out.println("Borrowed book:");
        Collection<BorrowedInfo> borrowedInfo = borrowBookRepository.loadBorrowBooksDatabase();
        for (BorrowedInfo borrowedInfo1 : borrowedInfo) {
            System.out.println(borrowedInfo1.toString());
        }
    }

    public void returnBookToLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Do you want return book -> yes/no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                borrowedBookListView();
                try {
                    System.out.println("Enter id book to return:");
                    int idBorrow = scanner.nextInt();
                    borrowBookRepository.returnBook(idBorrow);

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Bad type id");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } while (!choice.equalsIgnoreCase("no"));
    }
}
