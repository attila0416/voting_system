package voting.model;

public class GetIfUserVotedRequest {

    private final String pollName;
    private final String userName;

    /**
     * Class model for turning the JSON request into an object when the admin checks if a user has voted on a poll.
     *
     * @param pollName is the name of the poll
     * @param userName is the name of the user
     */
    public GetIfUserVotedRequest(String pollName, String userName) {
        this.pollName = pollName;
        this.userName = userName;
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
     * Getter method for the name of the user.
     *
     * @return name of the user
     */
    public String getUserName() {
        return userName;
    }
}
