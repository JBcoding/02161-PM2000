import java.io.*;
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
        save();
    }

    public static Set<User> getUsers() {
        return users;
    }

    public static void addProject(Project project) {
        projects.add(project);
        save();
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
        save();
    }

    public static String getFolderPath() {
        String workingDirectory;
        //here, we assign the name of the OS, according to Java, to a variable...
        String OS = (System.getProperty("os.name")).toUpperCase();
        //to determine what the workingDirectory is.
        //if it is some version of Windows
        if (OS.contains("WIN")) {
            //it is simply the location of the "AppData" folder
            workingDirectory = System.getenv("AppData");
        }
        //Otherwise, we assume Linux or Mac
        else {
            //in either case, we would start in the user's home directory
            workingDirectory = System.getProperty("user.home");
            //if we are on a Mac, we are not done, we look for "Application Support"
            workingDirectory += "/Library/Application Support";
        }
        //we are now free to set the workingDirectory to the subdirectory that is our
        //folder.
        return workingDirectory;
    }

    public static void save() {
        String workingDirectory = getFolderPath();
        (new File(workingDirectory + "/PM2000")).mkdir();
        File file = new File(workingDirectory + "/PM2000/PM2000.txt");
        try {
            FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new PM2000State(users, projects, projectCount));
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        String workingDirectory = getFolderPath();
        File file = new File(workingDirectory + "/PM2000/PM2000.txt");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file.getAbsoluteFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                PM2000State loaded = (PM2000State)in.readObject();
                projects = loaded.projects;
                users = loaded.users;
                projectCount = loaded.projectCount;
                in.close();
                fileIn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
