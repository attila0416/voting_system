package voting.storage;

import java.util.Objects;

public class Candidate {

    private final String name;

    /**
     * Candidate within a poll that users can vote for.
     *
     * @param name is the name of the candidate
     */
    public Candidate(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Getter method for the name of the candidate.
     *
     * @return the name of the candidate.
     */
    public String getName() {
        return name;
    }
}
