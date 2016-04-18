import java.util.Date;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Activity {
    public Activity(String name, Project project) {
    }

    public String getName() {
        return null;
    }

    public Project getProject() {
        return null;
    }

    public int getActivityID() {
        return -1;
    }

    public boolean addMember(User user) {
    }

    public Set<User> getMembers() {
        return null;
    }

    public void setStartDate(Date startDate) throws NegativeTimeException {}

    public Date getStartDate() {
        return null;
    }

    public void setEndDate(Date endDate) throws NegativeTimeException {}

    public Date getEndDate() {
        return null;
    }

    public int getUsedTimeOnActivity() {
        return -1;
    }
}
