import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class User {
    protected String name;
    protected String mail;
    protected String tel;
    protected Project project;
    protected Set<Activity> activities;
    protected boolean superUser;
    protected String userID;
    protected SimpleCalendar simpleCalendar;

    public User(String name, String mail, String tel) throws NullPointerException {
        if (name == null || mail == null || tel == null || name.equals("") || mail.equals("") || tel.equals("")) {
            throw new NullPointerException();
        }
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        simpleCalendar = new SimpleCalendar();
        activities = new HashSet<>();
    }

    public void addUsedTime(Activity activity, Date startDate, Date endDate, Date startTime, Date endTime) throws NegativeTimeException {
        if (!startDate.before(endDate)) {
            throw new NegativeTimeException("You cannot add negative dates");
        } else if (!startTime.before(endTime)) {
            throw new NegativeTimeException("You cannot add negative time");
        }
        simpleCalendar.addUsedTime(activity, startDate, endDate, startTime, endTime);
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public int getUsedTimeOnActivity(Activity activity) {
        return simpleCalendar.getUsedTimeOnActivity(activity);
    }

    public boolean toggleSuperUserStatus() {
        superUser = !superUser;
        if (!superUser && activities.size() > 10) {
            superUser = true;
            return false;
        }
        return true;
    }

    public boolean makeSuperUser() {
        if (!superUser) {
            superUser = true;
            return true;
        }
        return false;
    }

    public boolean makeNormalUser() {
        if (superUser) {
            return toggleSuperUserStatus();
        }
        return false;
    }

    public boolean isSuperUser() {
        return superUser;
    }

    public String getID() {
        return userID;
    }
}
