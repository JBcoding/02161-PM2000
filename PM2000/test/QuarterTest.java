import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by madsbjoern on 04/04/16.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class QuarterTest {
    @Test
    public void TestQuarter_SetActivity_SetsValue() {
        Activity activity = new Activity("", new Project("", ""));

        Quarter quarter = new Quarter();

        quarter.setActivity(activity);
        assertEquals(quarter.getActivity(), activity);

    }

    @Test
    public void TestQuarter_SetActivity_OverridesValue() {
        Activity activity1 = new Activity("", new Project("", ""));
        Activity activity2 = new Activity("", new Project("", ""));

        Quarter quarter = new Quarter();

        quarter.setActivity(activity1);
        quarter.setActivity(activity2);
        assertEquals(quarter.getActivity(), activity2);

    }
}

