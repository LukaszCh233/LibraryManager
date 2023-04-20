package library;

import books.Book;
import books.BookRepository;
import books.BookStatus;
import books.BooksFunction;
import database.Database;
import users.User;
import users.UserRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BorrowBookRepository {
    Database database;
    BookRepository bookRepository;
    UserRepository userRepository;
    BooksFunction booksFunction;

    public BorrowBookRepository(Database database) {
        this.database = database;
        this.bookRepository = new BookRepository(database);
        this.userRepository = new UserRepository(database);
        this.booksFunction = new BooksFunction(database);
    }

    public void writeBorrowBookToDatabase(int idBook, int idUser, Date borrowData, Date returnData) {
        Optional<Book> book = this.bookRepository.findBook(idBook);
        Optional<User> user = this.userRepository.findUser(idUser);

        if (book.isEmpty()) {
            throw new RuntimeException("there is no such id" + idBook);
        }
        if (user.isEmpty()) {
            throw new RuntimeException("there is no such id" + idUser);
        }
        if (book.get().isBorrowed()) {

            throw new RuntimeException("This book is borrowed");

        }
        boolean isUpdated = bookRepository.updateBookStatus(idBook, BookStatus.BORROWED);
        if (!isUpdated) {
            throw new RuntimeException("There has been an error when updating status");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String sql = "INSERT INTO borrowedBook (idBook, idUser,borrowData,returnData) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idBook);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setDate(3, Date.valueOf((dateFormat.format(borrowData))));
            preparedStatement.setDate(4, Date.valueOf((dateFormat.format(returnData))));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Collection<BorrowedInfo> loadBorrowBooksDatabase() {

        ArrayList<BorrowedInfo> borrowedInfo = new ArrayList<>();
        String sql = "SELECT " +
                "book_user.idBorrow as id, " +
                "book_user.idUser as userId, " +
                "book_user.idBook as bookId, " +
                "book_user.borrowData as borrowDate, " +
                "book_user.returnData as returnDate, " +
                "user.nameUser as userName, " +
                "user.lastNameUser as userLastName, " +
                "book.bookTitle as bookTitle, " +
                "book.bookAuthor as bookAuthor " +
                "FROM borrowedBook book_user " +
                "INNER JOIN UserList user ON book_user.idUser = user.idUser " +
                "INNER JOIN BookList book ON book_user.idBook = book.id";
        try (Statement statement = database.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Integer idBorrowBook = resultSet.getInt("id");
                Integer bookId = resultSet.getInt("bookId");
                Integer userId = resultSet.getInt("userId");
                Date borrowDate = resultSet.getDate("borrowDate");
                Date returnDate = resultSet.getDate("returnDate");
                String bookTitle = resultSet.getString("bookTitle");
                String bookAuthor = resultSet.getString("bookAuthor");
                String userName = resultSet.getString("userName");
                String userLastName = resultSet.getString("userLastName");

                borrowedInfo.add(new BorrowedInfo(idBorrowBook,
                        bookId,
                        bookTitle,
                        bookAuthor,
                        userId,
                        userName,
                        userLastName,
                        borrowDate,
                        returnDate
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedInfo;
    }

    public void returnBook(int idBook) {
        String sql = "DELETE FROM borrowedBook WHERE idBook = ?";
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idBook);
            preparedStatement.executeUpdate();
            bookRepository.updateBookStatus(idBook, BookStatus.AVAILABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


