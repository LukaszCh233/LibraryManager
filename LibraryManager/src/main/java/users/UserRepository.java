package users;


import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserRepository {
    Database database;

    public UserRepository(Database database) {
        this.database = database;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO UserList (nameUser,lastNameUser,emailUser,numberUser) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.userName);
            preparedStatement.setString(2, user.userLastName);
            preparedStatement.setString(3, user.userEmail);
            preparedStatement.setInt(4, user.userNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<User> loadUser() {
        Collection<User> usersList = new ArrayList<>();
        String sql = "SELECT * FROM UserList";
        try (Statement statement = database.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                usersList.add(mapResultToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    private static User mapResultToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        int number = resultSet.getInt(5);
        return new User(id, name, lastName, email, number);
    }

    public void removeUserFromDatabase(int idUser) {
        String sql = "DELETE FROM UserList WHERE idUser = ?";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Optional<User> findUser(int idUser) {
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT * FROM UserList WHERE idUser = ?")) {
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapResultToUser(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

