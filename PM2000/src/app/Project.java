import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class Project {
    protected String name;
    protected String projectID;
    protected Date startDate;
    protected Date endDate;
    protected Set<User> members;
    protected Set<Activity> activities;
    protected User projectLead;

    public Project(String name) throws NullPointerException, IllegalStateException {
        if (name == null || name.equals("")) {
            throw new NullPointerException("No name given");
        }
        if (PM2000.isProjectNameTaken(name)) {
            throw new IllegalStateException("Projekt name already in use");
        }
        this.name = name;
        activities = new HashSet<>();
        members = new HashSet<>();
        projectID = PM2000.getNextProjectID();
        PM2000.addProject(this);
    }

    public String getName() {
        return name;
    }

    public String getProjectID() {
        return projectID;
    }

    public boolean setStartDate(Date startDate) {
        if (endDate == null) {
            this.startDate = startDate;
            return true;
        }
        if (startDate.before(endDate)) {
            this.startDate = startDate;
            return true;
        }
        return false;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean setEndDate(Date endDate) {
        if (startDate == null) {
            this.endDate = endDate;
            return true;
        }
        if (endDate.after(startDate)) {
            this.endDate = endDate;
            return true;
        }
        return false;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setProjectLead(User projectLead) {
        this.projectLead = projectLead;
    }

    public User getProjectLead() {
        return projectLead;
    }

    public int getUsedTimeOnProject() {
        int timeUsed = 0;
        for (Activity a : activities) {
            timeUsed += a.getUsedTimeOnActivity();
        }
        return timeUsed;
    }

    public Activity getActivityWithName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }
}
