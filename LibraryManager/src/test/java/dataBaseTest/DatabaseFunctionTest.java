package dataBaseTest;

import database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseFunctionTest {
    Database database;
    Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        database = new Database("dataBase");
        connection = database.getConnection();
    }

    @AfterEach
    void closeDatabase() throws Exception {
        database.close();
    }

    @Test
    void connectionTest() {
        Assertions.assertNotNull(connection);
    }

    @Test
    void initializeDatabaseTest() throws SQLException {
        Assertions.assertNotNull(connection.createStatement().executeQuery("SELECT * FROM BookList"));
        Assertions.assertNotNull(connection.createStatement().executeQuery("SELECT * FROM userList"));
        Assertions.assertNotNull(connection.createStatement().executeQuery("SELECT * FROM borrowedBook"));
    }
}
