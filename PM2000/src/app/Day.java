import java.io.Serializable;
import java.util.Date;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Day implements Serializable {
    protected Quarter[] quarters = new Quarter[96];

    public Day() {
        for (int i = 0; i < 96; i ++) {
            quarters[i] = new Quarter();
        }
    }

    public void addUsedTime(Activity activity, Date startTime, Date endTime) throws NegativeTimeException {
        int startQuater = startTime.getHours() * 4 + (int)Math.floor(startTime.getMinutes() / 15);
        int endQuater = endTime.getHours() * 4 + (int)Math.ceil(endTime.getMinutes() / 15);
        if (startQuater > endQuater) {
            throw new NegativeTimeException("You cannot add negative time");
        }
        for (int i = startQuater; i < endQuater; i ++) {
            quarters[i].setActivity(activity);
        }
    }

    public int getUsedTimeOnActivity(Activity activity) {
        int timeUsed = 0;
        for (Quarter q : quarters) {
            if (q.getActivity() == null) {
                continue;
            }
            if (q.getActivity().getActivityID() == activity.getActivityID()) {
                timeUsed ++;
            }
        }
        return timeUsed;
    }
}
