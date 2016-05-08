import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by madsbjoern on 08/05/16.
 */
public class CaseCreateProjectTest extends BeforeAndAfterTest {

    @Test
    public void TestMainScenario() {
        Project newProject = new Project("Name");
        assertTrue(PM2000.getProjects().contains(newProject));
    }

    @Test
    public void TestAlternativeScenarioA() {
        try {
            new Project("");
            fail("No name project can bee created");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestAlternativeScenarioB() {
        new Project("Name");
        try {
            new Project("Name");
            fail("Two projects with same can exist");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Projekt name already in use");
        }
    }

    /*
    @Test
    public void TestAlternativeScenarioC
    We cannot test a the cancel button, because a press on that just makes no project
     */
}
