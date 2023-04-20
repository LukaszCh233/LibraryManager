package books;
import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BookRepository {
    Database database;

    public BookRepository(Database database) {
        this.database = database;

    }

    private static Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        int idBook = resultSet.getInt(1);
        String bookName = resultSet.getString(2);
        String bookAuthor = resultSet.getString(3);
        String bookType = resultSet.getString(4);
        int publicationDate = resultSet.getInt(5);
        String statusString = resultSet.getString(6);
        BookStatus status;
        if (statusString != null) {
            status = BookStatus.valueOf(resultSet.getString(6));
        } else {
            status = BookStatus.AVAILABLE;
        }
        return new Book(idBook, bookName, bookAuthor, bookType, publicationDate, status);
    }

    public void saveBook(Book book) {
        String sql = "INSERT INTO BookList (bookTitle,bookAuthor,bookType,publicationDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, book.bookTitle);
            preparedStatement.setString(2, book.bookAuthor);
            preparedStatement.setString(3, book.bookType);
            preparedStatement.setInt(4, book.bookPublicationDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Book> loadBook() {
        Collection<Book> bookList = new ArrayList<>();

        String sql = "SELECT * FROM BookList";
        try (Statement statement = database.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                bookList.add(mapResultSetToBook(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void removeBookFromDatabase(int idBook) {
        String sql = "DELETE FROM BookList WHERE id = ?";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idBook);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Book> findBook(int idBook) {
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT * FROM BookList WHERE id = ?")) {
            preparedStatement.setInt(1, idBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapResultSetToBook(resultSet));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateBookStatus(int id, BookStatus borrowed) {
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement("UPDATE BookList SET status = ? WHERE id = ?")) {
            preparedStatement.setString(1, borrowed.name());
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


