package voting.storage;

import org.junit.Assert;
import org.junit.Test;

public class CandidateTest {

    /**
     * Tests getName() within Candidate.class
     */
    @Test
    public void getNameTest() {
        /*
        Variable setup
         */
        String name = "Tom";
        Candidate candidate = new Candidate(name);

        /*
        Tests
         */
        String resultName = candidate.getName();
        Assert.assertEquals(resultName, name);
    }

}
