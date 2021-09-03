package voting.storage;

import java.util.Objects;

public class User {

    private final String name;

    /**
     * User using the Voting System.
     *
     * @param name is the name of the user
     */
    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
