import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.awt.*;
import java.util.Date;
import static org.junit.Assert.*;
/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseCreateUserTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() {
        User newUser = new User("Name", "mail@mail.net", "12345678");
        assertTrue(PM2000.getUsers().contains(newUser));
    }

    @Test
    public void TestAlternativeScenarioA1Of3() {
        try {
            new User("", "mail@mail.net", "12345678");
            fail("No name user can bee created");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestAlternativeScenarioA2Of3() {
        try {
            new User("Name", "", "12345678");
            fail("No mail user can bee created");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No mail given");
        }
    }

    @Test
    public void TestAlternativeScenarioA3Of3() {
        try {
            new User("Name", "mail@mail.net", "");
            fail("No number user can bee created");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No phone number given");
        }
    }

    /*
    @Test
    public void TestAlternativeScenarioB
    Changes was made, so the user class auto generates a id, wish not in use.
     */

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button, because a press on that just makes no user
     */

}
