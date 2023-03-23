import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Library {
        List<Book> bookList = new ArrayList<>();
        List<User> userList = new ArrayList<>();

        public void addBookToLibrary()  {
                Scanner scanner = new Scanner(System.in);
                String choice;
                String addBookTitle;
                String addBookAuthor;
                String addBookType;
                int addBookPublicationDate;
                int addIdBook;
                do {
                        System.out.println("Add book to library \n id/title/author/type/publication date: ");
                        try {
                                addIdBook = scanner.nextInt();
                                scanner.nextLine();
                                addBookTitle = scanner.nextLine();
                                addBookAuthor = scanner.nextLine();
                                addBookType = scanner.nextLine();
                                addBookPublicationDate = scanner.nextInt();
                                while (!scanner.hasNextLine()) {
                                        System.out.println("bad");
                                }
                                scanner.nextLine();
                                bookList.add(new Book(addIdBook, addBookTitle, addBookAuthor, addBookType, addBookPublicationDate));
                                writeBookToDatabase(addIdBook, addBookTitle, addBookAuthor, addBookType, addBookPublicationDate);
                        } catch (SQLException e) {
                                System.out.println("An error occurred while writing to the database: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                                System.out.println("Database driver class not found: " + e.getMessage());
                        } catch (Exception e) {
                                System.out.println("Bad type try again:");
                        }

                        System.out.println("Back to menu -> yes/no");
                        choice = scanner.nextLine();
                } while (!choice.equalsIgnoreCase("yes"));
        }

        public void bookListView() {
                Scanner scanner = new Scanner(System.in);
                String choice;

                do {
                        System.out.println("Books in the library: \n");
                        for (Book book : bookList) {
                                System.out.println(book.toString());
                        }
                        System.out.println("\n If you want to remove book -> yes \n If you want back to menu -> no");
                        choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("yes")) {
                                try {
                                        System.out.println("Enter the ID of the book you want to remov:");
                                        int removeBook = scanner.nextInt();
                                        scanner.nextInt();
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
                        }
                        while (!choice.equalsIgnoreCase("no")) ;
                        System.out.println();
        }

        public void loadBookDatabase() throws ClassNotFoundException, SQLException {
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
                        bookList.add(new Book(idBook, bookName, bookAuthor, bookType, publicationDate));
                }
        }
        public void writeBookToDatabase(int id, String bookTitle, String bookAuthor, String bookType, int publicationDate) throws ClassNotFoundException, SQLException {
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
        }
        public void removeBookFromDatabase(int idBook) throws ClassNotFoundException, SQLException {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Library.db";
                Connection connection = DriverManager.getConnection(url);
                String sql = "DELETE FROM BookList WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idBook);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
        }
        public void addReaderToList() {
                Scanner scanner = new Scanner(System.in);
                String choice;
                int idReader;
                String name;
                String lastName;
                String email;
                int number;

                do {
                        System.out.println("Add reader to library \n id/name/last name/email/number: ");
                        try {
                                idReader = scanner.nextInt();
                                scanner.nextLine();
                                name = scanner.nextLine();
                                lastName = scanner.nextLine();
                                email = scanner.nextLine();
                                number = scanner.nextInt();
                                scanner.nextLine();
                                userList.add(new User(idReader, name, lastName, email, number));
                                writeReadersToDatabase(idReader, name, lastName, email, number);
                        } catch (SQLException e) {
                                System.out.println("An error occurred while writing to the database: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                                System.out.println("Database driver class not found: " + e.getMessage());
                        } catch (Exception e) {
                                System.out.println("Bad type try again:");
                        }
                        System.out.println("Back to menu -> yes");
                        choice = scanner.nextLine();
                } while (!choice.equalsIgnoreCase("yes"));
        }
        public void loadReaderDatabase() throws ClassNotFoundException, SQLException {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Library.db";
                Connection connection = DriverManager.getConnection(url);
                String sql = "SELECT * FROM UserList";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        String lastNamer = resultSet.getString(3);
                        String email = resultSet.getString(4);
                        int number = resultSet.getInt(5);
                        userList.add(new User(id, name, lastNamer, email, number));
                }
        }
        public void readersListView() {
                Scanner scanner = new Scanner(System.in);
                String choice;

                do {
                        System.out.println("Readers in the library: \n");
                        for (User reader : userList) {
                                System.out.println(reader.toString());
                        }
                        System.out.println("\n If you want to remove reader -> yes/no");
                        choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("yes")) {
                                try {
                                System.out.println("Enter the ID of the reader you want to remove:");
                                int removeUser = scanner.nextInt();
                                userList.removeIf(reader -> reader.getIdUser() == removeUser);
                                removeReaderFromDatabase(removeUser);
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
                System.out.println();
        }
        public void writeReadersToDatabase(int id,String name,String lastName,String emial, int number) throws ClassNotFoundException, SQLException {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Library.db";
                Connection connection = DriverManager.getConnection(url);
                String sql = "INSERT INTO UserList (idUser,nameUser,lastNameUser,emailUser,numberUser) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, emial);
                preparedStatement.setInt(5, number);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
        }
        public void removeReaderFromDatabase(int idUser) throws ClassNotFoundException, SQLException {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Library.db";
                Connection connection = DriverManager.getConnection(url);
                String sql = "DELETE FROM UserList WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idUser);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
        }
}

