import java.io.Serializable;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Quarter implements Serializable {
    protected Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
