package books;
import database.Database;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BooksFunction {
    BookRepository bookRepository;

    public BooksFunction(Database database) {
        this.bookRepository = new BookRepository(database);
    }

    public void addBookToLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Add book to the library");
        do {
            try {
                System.out.println("Title book:");
                String title = scanner.nextLine();
                System.out.println("Author:");
                String author = scanner.nextLine();
                System.out.println("Type:");
                String bookType = scanner.nextLine();
                System.out.println("Publication date:");
                int bookPublicationDate = scanner.nextInt();
                scanner.nextLine();
                Book book = new Book(null, title, author, bookType, bookPublicationDate, BookStatus.AVAILABLE);
                bookRepository.saveBook(book);
            } catch (InputMismatchException e) {
                System.out.println("Bad type");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Back to menu -> yes/no");
            choice = scanner.nextLine();
        } while (!choice.equalsIgnoreCase("yes"));
    }

    public void deleteBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Do you want delete book -> yes/no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                bookListView();
                try {
                    System.out.println("Enter book id to delete:");
                    int idBook = scanner.nextInt();
                    bookRepository.removeBookFromDatabase(idBook);

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

    public void bookListView() {

        Collection<Book> booksList = bookRepository.loadBook();
        System.out.println("Books in library:");
        for (Book book : booksList) {
            System.out.println(book.toString());
        }
    }
}

