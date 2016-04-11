import java.util.Date;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class User {
    public User(String name, String mail, String tel) {
    }

    public void addUsedTime(Activity activity, Date startDate, Date endDate, Date startTime, Date endTime) throws NegativeTimeException {
    }

    public String getName() {
        return null;
    }

    public String getMail() {
        return null;
    }

    public String getTel() {
        return null;
    }

    public void setProject(Project project) {
    }

    public Project getProject() {
        return null;
    }

    public void addActivity(Activity activity) {
    }

    public Set<Activity> getActivitys() {
        return null;
    }

    public int getUsedTimeOnActivity(Activity activity) {
        return -1;
    }

    public boolean toggleSuperUserStatus() {
        return false;
    }

    public boolean isSuperUser() {
        return false;
    }
}
