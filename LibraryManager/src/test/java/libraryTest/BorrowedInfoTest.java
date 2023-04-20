package libraryTest;

import library.BorrowedInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BorrowedInfoTest {
    @Test
    void name() {
        BorrowedInfo borrowBook = new BorrowedInfo(1,1,null,null,1,"Jan",null,null,null);

        Assertions.assertEquals(1, borrowBook.getIdBorrowBook());
        Assertions.assertEquals("Jan",borrowBook.getUserName());
    }
}
