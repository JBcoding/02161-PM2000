import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by madsbjoern on 04/04/16.
 */

public class SuperUserToNormalUserTest {

    @Test
    public void TestInputA() {
        User user = null;
        try {
            user.makeNormalUser();
            fail("Can make null user to normal user");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputB() {

    }

    @Test
    public void TestInputC() {
    }

    // Test D, can not be recreated in code
}
