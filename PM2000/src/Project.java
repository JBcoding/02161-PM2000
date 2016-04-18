import java.util.Date;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Project {
    public Project(String name) {
    }

    public String getName() {
        return null;
    }

    public String getProjectID() {
        return null;
    }

    public boolean setStartDate(Date startDate) {
        return false;
    }

    public Date getStartDate() {
        return null;
    }

    public boolean setEndDate(Date endDate) {
        return false;
    }

    public Date getEndDate() {
        return null;
    }

    public void addMember(User user) {
    }

    public Set<User> getMembers() {
        return null;
    }

    public void addActivity(Activity activity) {
    }

    public Set<Activity> getActivitys() {
        return null;
    }

    public void setProjectLead(User projectLead) {

    }

    public User getProjectLead() {
        return null;
    }

    public int getUsedTimeOnProject() {
        return -1;
    }
}
