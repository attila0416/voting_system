package voting.model;

import java.util.Map;

public class ClosePollResponse {

    private final String pollName;
    private final Map<String, Integer> candidates;
    private final String winnerCandidate;

    /**
     * Class model for the JSON response that is returned when the admin closes a poll.
     *
     * @param pollName        is the name of poll
     * @param candidates      are the collection of candidates, where key is candidate's name and value is number of votes
     * @param winnerCandidate is the name of the winner candidate
     */
    public ClosePollResponse(String pollName, Map<String, Integer> candidates, String winnerCandidate) {
        this.pollName = pollName;
        this.candidates = candidates;
        this.winnerCandidate = winnerCandidate;
    }
}
