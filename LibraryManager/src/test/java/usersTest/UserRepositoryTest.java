package usersTest;

import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.User;
import users.UserRepository;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserRepositoryTest {
    private static final String DATABASE_NAME = "test.db";
    Database database;
    UserRepository userRepository;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        userRepository = new UserRepository(database);
    }

    @AfterEach
    void deleteDatabase() throws Exception {
        database.close();
        File dbFile = new File(DATABASE_NAME);
        boolean deleted = dbFile.delete();
        if (!deleted) {
            throw new RuntimeException("Failed to delete database file: " + DATABASE_NAME);
        }
    }

    @Test
    void shouldCreateUserInDatabaseTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);

        //When
        userRepository.createUser(user);

        //Then
        Collection<User> users = userRepository.loadUser();
        Optional<User> result = userRepository.findUser(user.getIdUser());
        User foundUser = new ArrayList<>(users).get(0);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, foundUser.getIdUser());
        Assertions.assertEquals("Jan", foundUser.getUserName());
        Assertions.assertEquals("Nowak", foundUser.getUserLastName());
        Assertions.assertEquals("qwer", foundUser.getUserEmail());
        Assertions.assertEquals(123456, foundUser.getUserNumber());
    }

    @Test
    void shouldLoadUsersFormDatabaseTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        User user1 = new User(2, "Ewa", "Kowalska", "rewq", 654321);

        //When
        userRepository.createUser(user);
        userRepository.createUser(user1);

        //Then
        Collection<User> users = userRepository.loadUser();
        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.contains(user));
        Assertions.assertTrue(users.contains(user1));
    }

    @Test
    void shouldRemoveChosenUserTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);

        //When
        userRepository.createUser(user);
        userRepository.removeUserFromDatabase(user.getIdUser());

        //Then
        Optional<User> removeUser = userRepository.findUser(user.getIdUser());
        Assertions.assertFalse(removeUser.isPresent());
    }

    @Test
    void shouldFindChosenUserWhenThereAreMoreThanOneTest() {
        //Given
        User user = new User(1, "Jan", "Nowak", "qwer", 123456);
        User user1 = new User(2, "Ewa", "Kowalska", "rewq", 654321);

        //When
        userRepository.createUser(user);
        userRepository.createUser(user1);

        //Then
        Optional<User> foundUser = userRepository.findUser(user.getIdUser());
        Optional<User> foundUser1 = userRepository.findUser(user1.getIdUser());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertEquals(user, foundUser.get());
        Assertions.assertEquals(user1, foundUser1.get());
        Assertions.assertEquals("Jan", user.getUserName());
        Assertions.assertEquals("Ewa", user1.getUserName());
    }
}
