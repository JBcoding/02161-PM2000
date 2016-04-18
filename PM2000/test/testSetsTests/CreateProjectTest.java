import org.junit.Test;

/**
 * Created by madsbjoern on 04/04/16.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import static org.junit.Assert.*;

public class CreateProjectTest {

    @Test
    public void TestInputA() {
        try {
            new Project(null);
            fail("Project can be created with no information");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestInputB() {
        try {
            new Project("");
            fail("Project can be created with no name");
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "No name given");
        }
    }

    @Test
    public void TestInputC() {
        Project project = new Project("EksempelProjekt");
        assertEquals(project.getProjectID(), "201601");
    }

    @Test
    public void TestInputD() {
        Project project = new Project("EksempelProjekt");

        try {
            new Project("EksempelProjekt");

            fail("You can create 2 projekts with the same name");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Projekt name already in use");
        }
    }

    // Test E, can not be recreated in code
}
