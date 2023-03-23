import java.sql.SQLException;
import java.util.Scanner;

public class MenuLibrary {
    Library library = new Library();

    public void useMenu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String choice;
        library.loadBookDatabase();
        library.loadReaderDatabase();
        do {
            System.out.println("Library menu:");
            System.out.println("1: Add book to library");
            System.out.println("2: List the books in the library");
            System.out.println("3: Borrow a book");
            System.out.println("4: Return the book");
            System.out.println("5: View a list of borrowed books");
            System.out.println("6: Add new reader");
            System.out.println("7: View the list of readers");
            System.out.println("0: Good bye :)");
            choice = scanner.nextLine();
            switch (choice) {
                case "1" :
                    library.addBookToLibrary();
                    break;
                case "2" :
                    library.bookListView();
                    break;
                case "3" :
                    break;
                case "4" :
                    break;
                case "5" :
                    break;
                case "6" :
                    library.addReaderToList();
                    break;
                case "7" :
                    library.readersListView();
                    break;
                case "0" :
                    break;
                default:
                    System.out.println("Bad choice, try again");
                    break;

            }
        } while (!choice.equalsIgnoreCase("0"));
    }
}
