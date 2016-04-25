import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Activity {
    protected static int IDCount;
    protected String name;
    protected Project project;
    protected int ActivityID;
    protected Set<User> members;
    protected Date startDate;
    protected Date endDate;

    public Activity(String name, Project project) throws NullPointerException {
        if (name == null || project == null) {
            throw new NullPointerException();
        }
        IDCount ++;
        ActivityID = IDCount;
        this.name = name;
        this.project = project;
        this.members = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Project getProject() {
        return project;
    }

    public int getActivityID() {
        return ActivityID;
    }

    public boolean addMember(User user) {
        return members.add(user);
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setStartDate(Date startDate) throws NegativeTimeException {
        if (endDate == null) {
            this.startDate = startDate;
            return;
        }
        if (startDate.before(endDate)) {
            this.startDate = startDate;
            return;
        }
        throw new NegativeTimeException("Start set after end date");
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) throws NegativeTimeException {
        if (startDate == null) {
            this.endDate = endDate;
            return;
        }
        if (endDate.after(startDate)) {
            this.endDate = endDate;
            return;
        }
        throw new NegativeTimeException("End set after start date");
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getUsedTimeOnActivity() {
        int timeUsed = 0;
        for (User u : members) {
            timeUsed += u.getUsedTimeOnActivity(this);
        }
        return timeUsed;
    }
}
