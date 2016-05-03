import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public class User implements Serializable {
    protected String name;
    protected String mail;
    protected String tel;
    protected Project project;
    protected Set<Activity> activities;
    protected boolean superUser;
    protected String userID;
    protected SimpleCalendar simpleCalendar;

    public User(String name, String mail, String tel) throws NullPointerException {
        if (name == null || name.equals("")) {
            throw new  NullPointerException("No name given");
        }
        if (tel == null || tel.equals("")) {
            throw new NullPointerException("No phone number given");
        }
        if (mail == null || mail.equals("")) {
            throw new NullPointerException("No mail given");
        }
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        simpleCalendar = new SimpleCalendar();
        activities = new HashSet<>();
        userID = PM2000.generateUserIDFromName(name);
        PM2000.addUser(this);
    }

    public void addUsedTime(Activity activity, Date startDate, Date endDate, Date startTime, Date endTime) throws NegativeTimeException, IllegalArgumentException {
        if (activity == null || startDate == null || endDate == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException();
        }
        if (activity.getStartDate() != null && activity.getStartDate().after(startDate)) {
            throw new NegativeTimeException("Aktivitet Ikke Begyndt ved Start Dato");
        } else if (activity.getEndDate() != null && activity.getEndDate().before(endDate)) {
            throw new NegativeTimeException("Aktivitet Afsluttet Før Slut Dato");
        }
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

    public boolean addActivity(Activity activity) {
        if (activities.size() == (superUser ? 20 : 10)) {
            return false;
        }
        activities.add(activity);
        if (!activity.getMembers().contains(this)) {
            activity.addMember(this);
        }
        return true;
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

    @Override
    public String toString() {
        return name + " (" + userID + ")";
    }
}
