package voting_system.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voting_system.model.Poll;
import voting_system.model.PollCollection;
import voting_system.model.UserCollection;

@RestController()
@RequestMapping(value = "/admin")
public class AdminApiController {

    private PollCollection pollCollection;
    private UserCollection userCollection;
    private ObjectMapper objectMapper;

    @Autowired
    public AdminApiController(PollCollection pollCollection, UserCollection userCollection) {
        this.pollCollection = pollCollection;
        this.userCollection = userCollection;
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("You have reached the Voting System admin API!", HttpStatus.OK);
    }

    @RequestMapping(value = "/create_poll", method = RequestMethod.POST)
    public ResponseEntity<String> createPoll(@RequestBody String jsonString) throws JsonProcessingException {
        Poll poll = this.objectMapper.readValue(jsonString, Poll.class);
        boolean pollExists = this.pollCollection.checkPollExists(poll.getPollName());
        if (pollExists) {
            return new ResponseEntity<>("Poll already exists.", HttpStatus.CONFLICT);
        }
        this.pollCollection.addPoll(poll);
        return new ResponseEntity<>("Poll created.", HttpStatus.OK);
    }

    @RequestMapping(value = "/close_poll", method = RequestMethod.DELETE)
    public ResponseEntity<String> closePoll(@RequestParam String pollName) throws JsonProcessingException {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }
        Poll removedPoll = this.pollCollection.removePoll(pollName);
        removedPoll.checkWinner();
        removedPoll.closePoll();
        String pollJsonString = this.objectMapper.writeValueAsString(removedPoll);
        return new ResponseEntity<>(pollJsonString, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_votes_status", method = RequestMethod.GET)
    public ResponseEntity<String> getVotesStatus(@RequestParam String pollName) throws JsonProcessingException {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }
        Poll currentPoll = this.pollCollection.getPoll(pollName);
        String pollJsonString = this.objectMapper.writeValueAsString(currentPoll);
        return new ResponseEntity<>(pollJsonString, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_if_user_voted", method = RequestMethod.GET)
    public ResponseEntity<String> getIfUserVoted(@RequestParam String pollName,
                                                 @RequestParam String firstName,
                                                 @RequestParam String lastName) throws JsonProcessingException {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }
        Poll currentPoll = this.pollCollection.getPoll(pollName);
        boolean userHasVoted = currentPoll.hasUserVoted(firstName, lastName);
        String userHasVotedJsonString = this.objectMapper.writeValueAsString(userHasVoted);
        return new ResponseEntity<>(userHasVotedJsonString, HttpStatus.OK);
    }

}
