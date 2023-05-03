package users;

import lombok.Getter;

import java.util.Objects;
    @Getter
public class User {
    Integer idUser;
    String userName;
    String userLastName;
    String userEmail;
    int userNumber;

    public User(Integer idUser, String userName, String userLastName, String userEmail, int userNumber) {
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return userNumber == user.userNumber && Objects.equals(idUser, user.idUser) && Objects.equals(userName, user.userName) && Objects.equals(userLastName, user.userLastName) && Objects.equals(userEmail, user.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, userName, userLastName, userEmail, userNumber);
    }

    @Override
    public String toString() {
        return  "Id: " + idUser + " | " +
                "Name: " + userName + " | " +
                "LastName: " + userLastName + " | " +
                "Email: " + userEmail + " | " +
                "Number: " + userNumber;
    }
}
