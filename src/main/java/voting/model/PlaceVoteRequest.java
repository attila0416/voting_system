package voting.model;

public class PlaceVoteRequest {

    private final String pollName;
    private final String candidateName;
    private final String userName;

    /**
     * Class model for turning JSON to an object format when user places vote on a specific candidate in a poll.
     *
     * @param pollName is the name of the poll that the user voted for
     * @param candidateName is the name of the candidate who the user voted for
     * @param userName is the name of the user who placed the vote
     */
    public PlaceVoteRequest(String pollName, String candidateName, String userName) {
        this.pollName = pollName;
        this.candidateName = candidateName;
        this.userName = userName;
    }

    /**
     * Getter method for the name of the poll.
     *
     * @return name of the poll that the user voted for
     */
    public String getPollName() {
        return pollName;
    }

    /**
     * Getter method for the name of the candidate.
     *
     * @return name of the candidate who the user voted for
     */
    public String getCandidateName() {
        return candidateName;
    }

    /**
     * Getter method for the name of the user.
     *
     * @return name of the user who placed the vote
     */
    public String getUserName() {
        return userName;
    }

}
