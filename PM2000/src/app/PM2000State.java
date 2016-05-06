import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by madsbjoern on 06/05/16.
 */
public class PM2000State implements Serializable {
    public Set<User> users = new HashSet<>();
    public Set<Project> projects = new HashSet<>();
    public int projectCount = 0;

    public PM2000State(Set<User> users, Set<Project> projects, int projectCount) {
        this.users = users;
        this.projects = projects;
        this.projectCount = projectCount;
    }
}
