import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

public class CreateUserTest {

    @Test
    public void TestInputA() {
        try {
            new User(null, null, null);
            fail("User can be created with no information");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestInputB1() {
        try {
            new User("Mark Tosse Hansen", "Eksempel@mail", "");
            fail("User can be created with no phone number");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No phone number given");
        }
    }

    @Test
    public void TestInputB2() {
        try {
            new User("", "Eksempel@mail", "43345312");
            fail("User can be created with no name");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestInputB3() {
        try {
            new User("Mark Tosse Hansen", "", "43345312");
            fail("User can be created with no mail");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No mail given");
        }
    }

    @Test
    public void TestInputC() {
        User user = new User("Mark Tosse Hansen", "Eksempel@mail", "43345312");
        assertEquals(user.getID(), "MTH");
    }

    @Test
    public void TestInputD() {
        User user1 = new User("Mark Tosse Hansen", "Eksempel@mail", "43345312");

        User user2 = new User("Mikkel Tåstrup Holger, ", "Eksempel@mail", "43345312");
        assertEquals(user2.getID(), "MTHA");
    }

    // Test E, can not be recreated in code
}
