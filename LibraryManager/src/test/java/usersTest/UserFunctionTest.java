package usersTest;

import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.User;
import users.UserFunction;
import users.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

public class UserFunctionTest {
    private static final String DATABASE_NAME = "test.db";
    Database database;
    UserRepository userRepository;
    UserFunction userFunction;

    @BeforeEach
    void createDatabase() throws SQLException {
        database = new Database(DATABASE_NAME);
        userRepository = new UserRepository(database);
        userFunction = new UserFunction(database);
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
    void shouldAddReaderToLibraryTest() {
        //Given
        String input = "Tom\nNowak\nemailTest\n123456\nyes\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        //When
        userFunction.addReaderToList();

        //Then
        Collection<User> readers = userRepository.loadUser();
        Assertions.assertEquals(1, readers.size());
        User user = readers.iterator().next();
        Assertions.assertEquals("Tom", user.getUserName());
        Assertions.assertEquals("Nowak", user.getUserLastName());
        Assertions.assertEquals("emailTest", user.getUserEmail());
        Assertions.assertEquals(123456, user.getUserNumber());
    }

    @Test
    void shouldDeleteReaderFromLibraryTest() {
        //Given
        User user = new User(1, "Tom", "Nowak", "emailTest", 123456);

        //When
        userRepository.createUser(user);
        String input = "yes\n" + user.getIdUser() + "\nno\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        userFunction.deleteReaderFromLibrary();

        //Then
        Collection<User> readers = userRepository.loadUser();
        Assertions.assertTrue(readers.isEmpty());
    }

    @Test
    void shouldDisplayReadersFromLibrary() {
        //Given
        User user = new User(1, "Tom", "Nowak", "emailTest", 123456);

        //When
        userRepository.createUser(user);
        userFunction.readerListView();

        //Then
        Collection<User> users = userRepository.loadUser();
        Assertions.assertTrue(users.contains(user));
        Assertions.assertEquals(1, users.size());
    }
}
