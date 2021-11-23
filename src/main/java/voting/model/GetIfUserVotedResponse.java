package voting.model;

public class GetIfUserVotedResponse {

    private final boolean userHasVoted;

    /**
     * Class model for the JSON response that is returned when the admin checks whether a user has voted on a poll.
     *
     * @param userHasVoted is whether a user has voted on a poll
     */
    public GetIfUserVotedResponse(boolean userHasVoted) {
        this.userHasVoted = userHasVoted;
    }
}
