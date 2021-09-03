package voting.model;

import java.util.Map;

public class GetVotesStatusResponse {

    private final Map<String, Integer> candidates;

    /**
     * Class model for the JSON response that is returned when the admin asks for the current vote status of candidates.
     *
     * @param candidates a collection of candidates, where key is the name of the candidate and value is
     *                   the number of votes
     */
    public GetVotesStatusResponse(Map<String, Integer> candidates) {
        this.candidates = candidates;
    }
}
