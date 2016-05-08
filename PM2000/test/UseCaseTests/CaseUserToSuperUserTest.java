import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseUserToSuperUserTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() throws NegativeTimeException {
        User user = new User("Name", "mail@mail.net", "12345678");
        assertTrue(user.makeSuperUser());
        assertTrue(user.isSuperUser());
    }

    @Test
    public void TestAlternativeScenarioA() throws NegativeTimeException {
        User user = new User("Name", "mail@mail.net", "12345678");
        user.makeSuperUser();
        assertFalse(user.makeSuperUser());
    }
}
