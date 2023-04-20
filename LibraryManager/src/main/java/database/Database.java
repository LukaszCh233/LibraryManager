package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class Database implements AutoCloseable {
    private static final String JDBC_SQLITE_LIBRARY_DB = "jdbc:sqlite:";
    private final Connection connection;

    public Database(String path) throws SQLException {
        connection = DriverManager.getConnection(JDBC_SQLITE_LIBRARY_DB + path);
        initializeDatabase();
    }

    public Database() throws SQLException {
        this("Library.db");
    }

    private void initializeDatabase() {
        Collection<String> sqlTable = new ArrayList<>();
        sqlTable.add("CREATE TABLE IF NOT EXISTS BookList ("+
                                "id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "bookTitle       TEXT," +
                                "bookAuthor      TEXT," +
                                "bookType        TEXT," +
                                "publicationDate INTEGER," +
                                "status          TEXT" +
                        ");");
        sqlTable.add("CREATE TABLE IF NOT EXISTS UserList (" +
                                "idUser       INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "nameUser     TEXT," +
                                "lastNameUser TEXT," +
                                "emailUser    TEXT," +
                                "numberUser   INTEGER" +
                        ");");

        sqlTable.add("CREATE TABLE IF NOT EXISTS borrowedBook (" +
                                "idBorrow     INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "idBook       INTEGER," +
                                "idUser       INTEGER," +
                                "borrowData   DATE," +
                                "returnData   DATE," +
                                "FOREIGN KEY(idBook) REFERENCES BookList(id)," +
                                "FOREIGN KEY(idUser) REFERENCES UserList(idUser)" +
                        ");");
        for (String sql : sqlTable) {
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
    @Override
    public void close() throws Exception {
        System.out.println("Database connection closed.");
        connection.close();
    }
}
