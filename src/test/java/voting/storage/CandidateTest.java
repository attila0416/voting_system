package voting.storage;

import org.junit.Assert;
import org.junit.Test;

public class CandidateTest {

    @Test
    public void getNameTest() {
        String name = "Tom";
        Candidate candidate = new Candidate(name);

        String resultName = candidate.getName();
        Assert.assertEquals(resultName, name);
    }

}
