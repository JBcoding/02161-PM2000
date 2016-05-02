import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 04/04/16.
 */
public final class PM2000 {
    protected static Set<User> users = new HashSet<>();
    protected static Set<Project> projects = new HashSet<>();
    protected static int projectCount = 0;

    public static void addUser(User user) {
        users.add(user);
    }

    public static Set<User> getUsers() {
        return users;
    }

    public static void addProject(Project project) {
        projects.add(project);
    }

    public static Set<Project> getProjects() {
        return projects;
    }

    public static String getNextProjectID() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        projectCount ++;
        String newID = "" + year;
        if (projectCount < 10) {
            newID += "0";
        }
        newID += projectCount;
        return newID;
    }

    public static String generateUserIDFromName(String name) {
        String[] names = name.split(" ");
        String UserID = "";
        for (String namePart : names) {
            if (namePart.length() >= 1) {
                UserID += namePart.charAt(0);
            }
        }
        if (doesUserIdExist(UserID)) {
            char extension = 'A';
            while (doesUserIdExist(UserID + extension)) {
                extension ++;
            }
            UserID += extension;
        }
        return UserID.toUpperCase();
    }

    public static boolean doesUserIdExist(String userID) {
        for (User u : users) {
            if (u.getID().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isProjectNameTaken(String name) {
        for (Project p : projects) {
            if (p.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static void reset() {
        users.clear();
        projects.clear();
        projectCount = 0;
    }
}
