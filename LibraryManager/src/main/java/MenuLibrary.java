import java.util.Scanner;

public class MenuLibrary {
    BooksLibraryFunction booksLibraryFunction = new BooksLibraryFunction();
    ReaderFunction readerFunction = new ReaderFunction();

    public void useMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        booksLibraryFunction.loadBookDatabase();
        readerFunction.loadReaderDatabase();
        booksLibraryFunction.loadBorrowBooksDatabase();
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
                    booksLibraryFunction.addBookToLibrary();
                    break;
                case "2":
                    booksLibraryFunction.bookListView();
                    break;
                case "3":
                    booksLibraryFunction.deleteBookFromLibrary();
                    break;
                case "4":
                    booksLibraryFunction.borrowBookFromLibrary();
                    break;
                case "5":
                    break;
                case "6":
                    booksLibraryFunction.borrowedBookListView();
                    break;
                case "7":
                    readerFunction.addReaderToList();
                    break;
                case "8":
                    readerFunction.deleteReaderFormLibrary();
                    break;
                case "9":
                    readerFunction.readersListView();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Bad choice, try again");
                    break;

            }
        } while (!choice.equalsIgnoreCase("0"));
    }
}
