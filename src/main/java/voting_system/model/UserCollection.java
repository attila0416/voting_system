package voting_system.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserCollection {

    private Set<User> users;

    public UserCollection() {
        this.users = new HashSet<>();
    }

    public User getUser(String firstName, String lastName) {
        User testUser = new User(firstName, lastName);
        boolean userExists =  this.users.contains(testUser);
        if (userExists) {
            for (User currUser : this.users) {
                if (currUser.equals(testUser))
                    return currUser;
            }
        }
        return null;
    }

}
