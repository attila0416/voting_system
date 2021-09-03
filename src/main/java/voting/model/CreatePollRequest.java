package voting.model;

import voting.storage.Candidate;

import java.util.Map;

public class CreatePollRequest {

    private String pollName;
    private Map<Candidate, Integer> candidates;

    /**
     * Class model for turning JSON request into an object when the admin creates a poll.
     *
     * @param pollName   is the name of the poll
     * @param candidates are the candidates where the key is object of Candidate.class and value is the number of votes
     */
    public CreatePollRequest(String pollName, Map<Candidate, Integer> candidates) {
        this.pollName = pollName;
        this.candidates = candidates;
    }

    /**
     * Getter method for the name of the poll.
     *
     * @return name of the poll
     */
    public String getPollName() {
        return pollName;
    }

    /**
     * Getter method for the candidates collection.
     *
     * @return candidates collection
     */
    public Map<Candidate, Integer> getCandidates() {
        return candidates;
    }

}
