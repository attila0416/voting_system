package voting_system.model;

import java.util.*;

public class PollCollection {

    private SortedMap<String, Poll> polls;

    public PollCollection() {
        this.polls = new TreeMap<>();
    }

    public Poll getPoll(String pollName) {
        return this.polls.get(pollName);
    }

    public void addPoll(Poll poll) {
        this.polls.put(poll.getPollName(), poll);
    }

    public Poll removePoll(String pollName) {
        return this.polls.remove(pollName);
    }

    public boolean checkPollExists(String pollName) {
        return this.polls.containsKey(pollName);
    }

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

}
