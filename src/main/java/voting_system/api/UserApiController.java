package voting_system.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import voting_system.model.Poll;
import voting_system.model.PollCollection;
import voting_system.model.User;
import voting_system.model.UserCollection;

import java.util.List;

public class UserApiController {

    private PollCollection pollCollection;
    private UserCollection userCollection;
    private ObjectMapper objectMapper;

    @Autowired
    public UserApiController(PollCollection pollCollection, UserCollection userCollection) {
        this.pollCollection = pollCollection;
        this.userCollection = userCollection;
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping(value = "/get_polls", method = RequestMethod.GET)
    public ResponseEntity<String> getPolls(@RequestParam int startIdx,
                                           @RequestParam int size) throws JsonProcessingException {
        List<Poll> polls = this.pollCollection.getPollsSlice(startIdx, size);
        if (polls == null) {
            return new ResponseEntity<>("Incorrect startIdx given.", HttpStatus.BAD_REQUEST);
        }
        String pollsJsonString = this.objectMapper.writeValueAsString(polls);
        return new ResponseEntity<>(pollsJsonString, HttpStatus.OK);
    }

    @RequestMapping(value = "/place_vote", method = RequestMethod.PUT)
    public ResponseEntity<String> placeVote(@RequestParam String pollName,
                                            @RequestParam String firstName,
                                            @RequestParam String lastName) {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }
        Poll currentPoll = this.pollCollection.getPoll(pollName);
        boolean userHasVoted = currentPoll.hasUserVoted(firstName, lastName);
        if (userHasVoted) {
            return new ResponseEntity<>("User has already voted.", HttpStatus.METHOD_NOT_ALLOWED);
        }
        User user = this.userCollection.getUser(firstName, lastName);
        if (user == null) {
            return new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
        }
        currentPoll.addVoter(user);
        return new ResponseEntity<>("Vote placed.", HttpStatus.OK);
    }

}
