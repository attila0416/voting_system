package voting.storage;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollCollectionTest {

    @Test
    public void addAndGetPollTest() {
        Candidate candidate1 = new Candidate("Tom");
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate1, 0);

        String pollName = "School Captain";
        Poll poll = new Poll(pollName, candidates);
        PollCollection pollCollection = new PollCollection();

        pollCollection.addPoll(pollName, poll);
        Poll returnedPoll = pollCollection.getPoll(pollName);
        Assert.assertEquals(returnedPoll, poll);
    }

    /**
     * Tests checkPollExists() and removePoll() using addPoll() within PollCollection.class
     */
    @Test
    public void existsAndRemovePollTest() {
        Candidate candidate1 = new Candidate("Tom");
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate1, 0);

        String pollName = "School Captain";
        Poll poll = new Poll(pollName, candidates);
        PollCollection pollCollection = new PollCollection();

        pollCollection.addPoll(pollName, poll);
        boolean pollExists = pollCollection.checkPollExists(pollName);
        Assert.assertTrue(pollExists);

        Poll removedPoll = pollCollection.removePoll(pollName);
        Assert.assertEquals(removedPoll, poll);

        pollExists = pollCollection.checkPollExists(pollName);
        Assert.assertFalse(pollExists);
    }

    /**
     * Tests getPollsSlice() using addPoll() within PollCollection.class
     */
    @Test
    public void getPollsSliceTest() {
        Candidate candidate1 = new Candidate("Tom");
        Candidate candidate2 = new Candidate("Bob");
        Candidate candidate3 = new Candidate("Jim");
        Map<Candidate, Integer> candidates = new HashMap<>();
        candidates.put(candidate1, 0);
        candidates.put(candidate2, 0);
        candidates.put(candidate3, 0);

        String pollName1 = "School Captain";
        String pollName2 = "Soccer Team Captain";
        String pollName3 = "Best Performance Award";
        Poll poll1 = new Poll(pollName1, candidates);
        Poll poll2 = new Poll(pollName2, candidates);
        Poll poll3 = new Poll(pollName3, candidates);
        PollCollection pollCollection = new PollCollection();

        pollCollection.addPoll(pollName1, poll2);
        pollCollection.addPoll(pollName2, poll2);
        pollCollection.addPoll(pollName3, poll3);

        List<Poll> polls = pollCollection.getPollsSlice(0, 5);
        Assert.assertEquals(polls.size(), 3);

        polls = pollCollection.getPollsSlice(0, 1);
        Assert.assertEquals(polls.size(), 1);

        Assert.assertEquals(polls.get(0).getPollName(), pollName3);
    }

}
