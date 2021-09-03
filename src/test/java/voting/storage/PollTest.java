package voting.storage;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PollTest {

    /**
     * Tests doesCandidateExist() within Poll.class
     */
    @Test
    public void doesCandidateExistTest() {
        String candidateName = "Tom";
        Candidate candidate = new Candidate(candidateName);
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate, 0);
        String pollName = "School Captain";
        Poll poll = new Poll(pollName, candidates);

        boolean candidateExists = poll.doesCandidateExist(candidateName);
        Assert.assertTrue(candidateExists);

        candidateExists = poll.doesCandidateExist("Bob");
        Assert.assertFalse(candidateExists);
    }

    /**
     * Tests placeVote() using hasUserVoted() and getCandidatesStandings() within Poll.class
     */
    @Test
    public void placeVoteTest() {
        String candidateName = "Tom";
        Candidate candidate = new Candidate(candidateName);
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate, 0);

        String pollName = "School Captain";
        Poll poll = new Poll(pollName, candidates);

        String userName = "Jerry";
        User user = new User(userName);

        poll.placeVote(candidate, user);
        boolean votePlaced = poll.hasUserVoted(user);
        Assert.assertTrue(votePlaced);

        Map<String, Integer> currentStandings = poll.getCandidatesStandings();
        Assert.assertTrue(currentStandings.containsKey(candidateName));

        Integer assumedVotes = currentStandings.get(candidateName);
        Integer actualVotes = 1;
        Assert.assertEquals(assumedVotes, actualVotes);
    }

    @Test
    public void getWinnerTest() {
        Candidate candidate1 = new Candidate("Tom");
        Candidate candidate2 = new Candidate("Bob");
        Candidate candidate3 = new Candidate("Jim");
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate1, 0);
        candidates.put(candidate2, 1);
        candidates.put(candidate3, 2);

        String pollName = "School Captain";
        Poll poll = new Poll(pollName, candidates);

        String winnerCandidate = poll.getWinner();
        Assert.assertEquals(winnerCandidate, "Jim");
    }

}
