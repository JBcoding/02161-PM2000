import org.junit.Before;
import org.junit.Test;

/**
 * Created by madsbjoern on 26/04/16.
 */
public class BeforeAndAfterTest {
    @Before
    public void resetPM2000() {
        PM2000.reset();
    }
}
