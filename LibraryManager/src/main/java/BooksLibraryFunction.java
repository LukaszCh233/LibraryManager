import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BooksLibraryFunction {
    ReaderFunction readerFunction = new ReaderFunction();
    List<Book> bookList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<BorrowedInfo> borrowedInfoList = new ArrayList<>();

    public void addBookToLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        String addBookTitle;
        String addBookAuthor;
        String addBookType;
        int addBookPublicationDate;
        int addIdBook;
        do {
            System.out.println("Add book to booksLibraryFunction:");
            try {
                System.out.println("Id:");
                addIdBook = scanner.nextInt();
                scanner.nextLine();
                for (Book book : bookList) {
                    if (book.getIdBook() == addIdBook) {
                        throw new IllegalArgumentException("Id exist, try again");
                    }
                }
                System.out.println("Title:");
                addBookTitle = scanner.nextLine();
                System.out.println("Author:");
                addBookAuthor = scanner.nextLine();
                System.out.println("Type:");
                addBookType = scanner.nextLine();
                System.out.println("Publication date:");
                addBookPublicationDate = scanner.nextInt();
                while (!scanner.hasNextLine()) {
                    System.out.println("bad");
                }
                scanner.nextLine();
                bookList.add(new Book(addIdBook, addBookTitle, addBookAuthor, addBookType, addBookPublicationDate, BookStatus.AVAILABLE));
                writeBookToDatabase(addIdBook, addBookTitle, addBookAuthor, addBookType, addBookPublicationDate);

            } catch (InputMismatchException e) {
                System.out.println("Bad type. Try again.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println("Bad type try again:");
            }
            System.out.println("Back to menu -> yes/no");
            choice = scanner.nextLine();

        } while (!choice.equalsIgnoreCase("yes"));
    }

    public void bookListView() {

        System.out.println("Books in library:");
        for (Book book : bookList) {
            System.out.println(book.toString());
        }
        System.out.println();
    }

    public void loadBookDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Library.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT * FROM BookList";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int idBook = resultSet.getInt(1);
                String bookName = resultSet.getString(2);
                String bookAuthor = resultSet.getString(3);
                String bookType = resultSet.getString(4);
                int publicationDate = resultSet.getInt(5);
                bookList.add(new Book(idBook, bookName, bookAuthor, bookType, publicationDate, BookStatus.AVAILABLE));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeBookToDatabase(int id, String bookTitle, String bookAuthor, String bookType, int publicationDate) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Library.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "INSERT INTO BookList (id,bookTitle,bookAuthor,bookType,publicationDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, bookTitle);
            preparedStatement.setString(3, bookAuthor);
            preparedStatement.setString(4, bookType);
            preparedStatement.setInt(5, publicationDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBookFromDatabase(int idBook) {

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Library.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "DELETE FROM BookList WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idBook);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Books in library:");
            for (Book book : bookList) {
                System.out.println(book.toString());
            }
            System.out.println("\n If you want to remove book -> yes \n If you want back to menu -> no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                try {
                    System.out.println("Enter the ID of the book you want to remove:");
                    int removeBook = scanner.nextInt();
                    scanner.nextLine();
                    bookList.removeIf(book -> book.getIdBook() == removeBook);
                    removeBookFromDatabase(removeBook);
                } catch (IllegalArgumentException e) {
                    System.out.println("Bad id try again");
                } catch (InputMismatchException e) {
                    System.out.println("Bad  form id try again");
                    scanner.nextLine();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } while (!choice.equalsIgnoreCase("no"));
    }

    public void borrowBookFromLibrary() {

        Scanner scanner = new Scanner(System.in);
        String choice;
        int idBook1;
        int idReader1;
        int idBorrowBook;
        String borrowData;
        String returnData;
        do {
            System.out.println("\n If you want to borrow book -> yes \n If you want back to menu -> no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                bookListView();
                System.out.println("Enter id book which you want borrow:");
                idBook1 = scanner.nextInt();
                System.out.println("Enter new id for borrow book:");
                idBorrowBook = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter borrow data:");
                borrowData = scanner.nextLine();
                System.out.println("Enter return data:");
                returnData = scanner.nextLine();
                readerFunction.readersListView();
                System.out.println("Enter id Reader::");
                idReader1 = scanner.nextInt();
                for (Book book : bookList) {
                    if (book.getIdBook() == idBook1) {
                        book.status = BookStatus.BORROWED;
                    }
                }
                for (User reader : userList) {
                    if (reader.getIdUser() == idReader1) {
                        System.out.println("Exist ");
                    }
                }

                try {
                    Class.forName("org.sqlite.JDBC");
                    String url = "jdbc:sqlite:Library.db";
                    Connection connection = DriverManager.getConnection(url);
                    String sql = "SELECT B.*, U.* FROM BookList B INNER JOIN UserList U ON B.id = U.idUser";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()) {
                        int idBook = resultSet.getInt(1);
                        String bookName = resultSet.getString(2);
                        String bookAuthor = resultSet.getString(3);
                        int id = resultSet.getInt(4);
                        String name = resultSet.getString(5);
                        String lastName = resultSet.getString(6);
                        borrowedInfoList.add(new BorrowedInfo(idBorrowBook, idBook, bookName, bookAuthor, id, name, lastName, borrowData, returnData));
                        writeBorrowBookToDatabase(idBorrowBook, idBook, bookName, bookAuthor, id, name, lastName, borrowData, returnData);

                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("The book has been borrowed\n");
            }
        } while (!choice.equalsIgnoreCase("no"));
    }

    public void writeBorrowBookToDatabase(int idBorrow, int idBook, String title, String author, int idUser, String userName, String lastName, String borrowDate, String returnDate) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Library.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "INSERT INTO borrowedBook (idBorrow,idBook,title,author,idUser,userName,userLastName,borrowData,returnData) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idBorrow);
            preparedStatement.setInt(2, idBook);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, author);
            preparedStatement.setInt(5, idUser);
            preparedStatement.setString(6, userName);
            preparedStatement.setString(7, lastName);
            preparedStatement.setString(8, borrowDate);
            preparedStatement.setString(9, returnDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowedBookListView() {
        System.out.println("Borrowed book:");
        for (BorrowedInfo borrowedInfo : borrowedInfoList) {
            System.out.println(borrowedInfo.toString());
        }
    }

    public void loadBorrowBooksDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Library.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT * FROM borrowedBook";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int idBorrow = resultSet.getInt(1);
                int idBook = resultSet.getInt(2);
                String bookTitle = resultSet.getString(3);
                String bookAuthor = resultSet.getString(4);
                int idReader = resultSet.getInt(5);
                String name = resultSet.getString(6);
                String lastName = resultSet.getString(7);
                String borrowData = resultSet.getString(8);
                String returnData = resultSet.getString(9);
                borrowedInfoList.add(new BorrowedInfo(idBorrow, idBook, bookTitle, bookAuthor, idReader, name, lastName, borrowData, returnData));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}



