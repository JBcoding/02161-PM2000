import java.io.Serializable;
import java.util.*;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class SimpleCalendar implements Serializable {
    protected HashMap<Integer, Day> days;

    public SimpleCalendar() {
        days = new HashMap<>();
    }

    public void addUsedTime(Activity activity, Date startDate, Date endDate, Date startTime, Date endTime) throws NegativeTimeException {
        if (!startDate.before(endDate)) {
            throw new NegativeTimeException("You cannot add negative dates");
        } else if (!startTime.before(endTime)) {
            throw new NegativeTimeException("You cannot add negative time");
        }
        int startDay = daysBetween(new Date(0, 0, 0, 0, 0, 0), startDate);
        int endDay = daysBetween(new Date(0, 0, 0, 0, 0, 0), endDate);
        for (Integer i = startDay; i < endDay; i ++) {
            Day day = days.get(i);
            if (day == null) {
                day = new Day();
                day.addUsedTime(activity, startTime, endTime);
                days.put(i, day);
            } else {
                day.addUsedTime(activity, startTime, endTime);
            }
        }
    }

    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public int getUsedTimeOnActivity(Activity activity) {
        int timeUsed = 0;
        for (Map.Entry<Integer, Day> e : days.entrySet()) {
            timeUsed += e.getValue().getUsedTimeOnActivity(activity);
        }
        return timeUsed;
    }

    public Quarter getQuarter(Date date, int number) {
        int daysSince = daysBetween(new Date(0, 0, 0, 0, 0, 0), date);
        Day day = days.get(daysSince);
        if (day == null) {
            return null;
        }
        return day.quarters[number];
    }
}
