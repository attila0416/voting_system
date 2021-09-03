package voting.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Poll implements Comparable<Poll> {
    private String pollName;
    private Map<Candidate, Integer> candidates;
    private Set<User> voters;

    /**
     * Poll within the Voting System that users can vote on.
     *
     * @param pollName   is the name of the poll
     * @param candidates are the candidates, where key is an object of Candidate.class and value is the number of votes
     */
    public Poll(String pollName, Map<Candidate, Integer> candidates) {
        this.pollName = pollName;
        this.candidates = candidates;
        this.voters = new HashSet<>();
    }

    @Override
    public int compareTo(Poll o) {
        return this.pollName.compareTo(o.pollName);
    }

    /**
     * Getter method for the name of the poll.
     *
     * @return the name of the poll
     */
    public String getPollName() {
        return this.pollName;
    }

    /**
     * Checks whether the user has voted on this poll.
     *
     * @param user is a user
     * @return whether the user has voted on this poll
     */
    public boolean hasUserVoted(User user) {
        return this.voters.contains(user);
    }

    /**
     * Checks whether a candidate exists in this poll based on the candidate's name.
     *
     * @param candidateName is the name of the candidate
     * @return whether the poll contains a candidate based on its name
     */
    public boolean doesCandidateExist(String candidateName) {
        Candidate candidate = new Candidate(candidateName);
        return this.candidates.containsKey(candidate);
    }

    /**
     * Adds the user to the voters of this poll,
     * and increments the number of votes for the given candidate.
     *
     * @param candidate is the candidate the user has voted for in this poll
     * @param user      is the user who voted on this poll
     */
    public void placeVote(Candidate candidate, User user) {
        this.voters.add(user);
        candidates.put(candidate, candidates.getOrDefault(candidate, 0) + 1);
    }

    /**
     * Converts the candidates Map to a new Map, where the key is the name of the candidate
     * instead of an object of the Candidate.class
     *
     * @return a Map of candidates with the number of votes they received.
     */
    public Map<String, Integer> getCandidatesStandings() {
        Map<String, Integer> convertedCandidates = new HashMap<>();
        for (Map.Entry<Candidate, Integer> entry : this.candidates.entrySet()) {
            Candidate currCandidate = entry.getKey();
            String name = currCandidate.getName();
            Integer votes = entry.getValue();
            convertedCandidates.put(name, convertedCandidates.getOrDefault(name, votes));
        }
        return convertedCandidates;
    }

    /**
     * Analyses the number of votes and returns the name of the winner candidate.
     *
     * @return the name of the winner candidate.
     */
    public String getWinner() {
        Integer highest = 0;
        String name = null;
        for (Map.Entry<Candidate, Integer> entry : this.candidates.entrySet()) {
            Integer votes = entry.getValue();
            if (votes > highest) {
                highest = votes;
                name = entry.getKey().getName();
            }
        }
        return name;
    }

}
