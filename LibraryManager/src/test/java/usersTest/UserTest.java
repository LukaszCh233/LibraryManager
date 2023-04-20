package usersTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.User;

public class UserTest {

    @Test
    void name() {
        User user = new User(null, "Jan", "Nowak", null, 12345);

        Assertions.assertEquals("Jan", user.getUserName());
        Assertions.assertEquals("Nowak", user.getUserLastName());
        Assertions.assertEquals(12345, user.getUserNumber());
    }
}
