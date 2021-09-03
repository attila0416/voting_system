package voting.storage;

import java.util.*;

public class PollCollection {

    private Map<String, Poll> polls;

    /**
     * PollCollection holds all the polls in one place within a Map, where the key is the name of the poll and
     * the value is the poll itself.
     */
    public PollCollection() {
        this.polls = new TreeMap<>();
    }

    /**
     * Getter method for a poll based on its name.
     *
     * @param pollName is the name of the poll
     * @return a poll based on its name
     */
    public Poll getPoll(String pollName) {
        return polls.get(pollName);
    }

    /**
     * Method for checking whether a poll exists based on its name.
     *
     * @param pollName is the name of the poll
     * @return whether a poll based on its name exists
     */
    public boolean checkPollExists(String pollName) {
        return this.polls.containsKey(pollName);
    }

    /**
     * Method that returns a list of polls that is a sublist of all the polls based on the request size and location.
     *
     * @param startIdx is the starting index position for the polls to be returned
     * @param size     is the amount of polls to be returned in this request
     * @return a list of polls
     */
    public List<Poll> getPollsSlice(int startIdx, int size) {
        Collection<Poll> values = this.polls.values();
        List<Poll> pollsList = new ArrayList<>(values);
        if (startIdx >= pollsList.size()) {
            return null;
        }
        int endIdx = pollsList.size() - startIdx;
        if (endIdx > startIdx + size) {
            endIdx = startIdx + size;
        }
        return pollsList.subList(startIdx, endIdx);
    }

    /**
     * Method that adds a poll to the rest of the polls.
     *
     * @param pollName is the name of the poll
     * @param poll     is the poll object that contains all the information about the poll
     */
    public void addPoll(String pollName, Poll poll) {
        polls.put(pollName, poll);
    }

    /**
     * Method that removes a poll from the poll collection based on its name.
     *
     * @param pollName is the name of the poll
     * @return the removed poll
     */
    public Poll removePoll(String pollName) {
        return this.polls.remove(pollName);
    }


}
