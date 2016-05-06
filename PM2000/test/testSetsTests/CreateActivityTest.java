import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Fr on 18-04-2016.
 */
public class CreateActivityTest extends BeforeAndAfterTest {

    @Test
    public void TestInputA() {
        try {
            Activity test = new Activity(null, 1, null);
            fail("Can make null Activitys");
        } catch (NullPointerException e) {}
    }

    @Test
    public void TestInputB() {
        Project projekt = new Project("name");
        String name = "name";
        Activity test = new Activity(name, 1, projekt);
        assertTrue(test.getName() == name);
    }

    @Test
    public void TestInputC() {
        Project projekt = new Project("name");
        String name = "name";
        Activity test = new Activity(name, 1, projekt);
        try {
            Activity test2 = new Activity(name, 1, projekt);
            fail("Shouldn't be able to create 2 of the same Activity");
        } catch (IllegalArgumentException e) {}
    }
}
