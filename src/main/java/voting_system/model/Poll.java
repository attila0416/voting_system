package voting_system.model;

import java.util.List;
import java.util.Set;

public class Poll implements Comparable<Poll> {

    private final String[] pollStatuses = {"OPEN", "CLOSED"};
    private String pollName;
    private List<Candidate> candidates;
    private Set<User> voters;
    private Candidate winner;
    private String pollStatus;

    public Poll(String pollName, List<Candidate> candidates, Set<User> voters) {
        this.pollName = pollName;
        this.candidates = candidates;
        this.voters = voters;
        this.pollStatus = pollStatuses[0];
        this.winner = null;
    }

    @Override
    public int compareTo(Poll o) {
        return this.pollName.compareTo(o.pollName);
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        this.candidates.remove(candidate);
    }

    public void checkWinner() {
        int mostVotes = 0;
        Candidate candidateWithMostVotes = null;
        for (Candidate candidate : this.candidates) {
            if (candidate.getVotes() > mostVotes) {
                candidateWithMostVotes = candidate;
            }
        }
        this.winner = candidateWithMostVotes;
    }

    public void closePoll() {
        this.pollStatus = pollStatuses[1];
    }

    public boolean hasUserVoted(String firstName, String lastName) {
        User testUser = new User(firstName, lastName);
        return this.voters.contains(testUser);
    }

    public void addVoter(User user) {
        this.voters.add(user);
    }
}
