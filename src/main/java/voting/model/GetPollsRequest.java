package voting.model;

public class GetPollsRequest {

    private final int startIdx;
    private final int size;

    /**
     * Class model for turning JSON to an object format when user requests for specific number of polls.
     *
     * @param startIdx is the starting index position for the polls
     * @param size     is the amount of polls to be returned in this request
     */
    public GetPollsRequest(int startIdx, int size) {
        this.startIdx = startIdx;
        this.size = size;
    }

    /**
     * Getter method for the starting index position for the polls that are to be returned in the request.
     *
     * @return starting index position for the polls
     */
    public int getStartIdx() {
        return startIdx;
    }

    /**
     * Getter method for the amount of polls to be returned in this request.
     *
     * @return the amount of polls
     */
    public int getSize() {
        return size;
    }

}
