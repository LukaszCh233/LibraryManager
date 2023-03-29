import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReaderFunction {
    List<User> userList = new ArrayList<>();

    public void addReaderToList() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        int idReader;
        String name;
        String lastName;
        String email;
        int number;

        do {
            System.out.println("Add reader to booksLibraryFunction \n id/name/last name/email/number: ");
            try {
                System.out.println("Id:");
                idReader = scanner.nextInt();
                scanner.nextLine();
                for (User user : userList) {
                    if (user.getIdUser() == idReader) {
                        throw new IllegalArgumentException("Id exist try again");
                    }
                }
                System.out.println("Name:");
                name = scanner.nextLine();
                System.out.println("Last name:");
                lastName = scanner.nextLine();
                System.out.println("Email:");
                email = scanner.nextLine();
                System.out.println("Number:");
                number = scanner.nextInt();
                scanner.nextLine();
                userList.add(new User(idReader, name, lastName, email, number));
                writeReadersToDatabase(idReader, name, lastName, email, number);
            } catch (SQLException e) {
                System.out.println("An error occurred while writing to the database: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Database driver class not found: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Bad type try again:");
            }
            System.out.println("Back to menu -> yes/no");
            choice = scanner.nextLine();
        } while (!choice.equalsIgnoreCase("yes"));
    }

    public void loadReaderDatabase() {
        try {
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void readersListView() {

        System.out.println("Readers in the Library: ");
        for (User reader : userList) {
            System.out.println(reader.toString());
        }
        System.out.println();
    }

    public void writeReadersToDatabase(int id, String name, String lastName, String emial, int number) throws ClassNotFoundException, SQLException {
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
        String sql = "DELETE FROM UserList WHERE idUser = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idUser);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public void deleteReaderFormLibrary() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Readers in the Library:");
            for (User reader : userList) {
                System.out.println(reader.toString());
            }
            System.out.println("\n If you want to remove reader -> yes/no");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                try {
                    System.out.println("Enter the ID of the reader you want to remove:");
                    int removeUser = scanner.nextInt();
                    scanner.nextLine();
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
    }
}
