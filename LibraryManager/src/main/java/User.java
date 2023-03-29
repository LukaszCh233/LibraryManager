public class User {
    int idUser;
    String userName;
    String userLastName;
    String userEmail;
    int userNumber;

    public User(int idUser, String userName, String userLastName, String userEmail, int userNumber) {
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserNumber() {
        return userNumber;
    }

    @Override
    public String toString() {
        return "Id: " + idUser + " | " +
                "Name: " + userName + " | " +
                "LastName: " + userLastName + " | " +
                "Email: " + userEmail + " | " +
                "Number: " + userNumber;
    }
}
