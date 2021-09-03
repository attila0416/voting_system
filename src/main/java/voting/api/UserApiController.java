package voting.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voting.model.GetPollsRequest;
import voting.model.PlaceVoteRequest;
import voting.storage.Candidate;
import voting.storage.Poll;
import voting.storage.PollCollection;
import voting.storage.User;

import java.util.List;

@RestController()
public class UserApiController {

    private final PollCollection pollCollection;
    private final ObjectMapper objectMapper;

    /**
     * Class that controls the API communication between the user and the server.
     *
     * @param pollCollection is the collection of polls in one place
     */
    @Autowired
    public UserApiController(PollCollection pollCollection) {
        this.pollCollection = pollCollection;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Method the polls are returned depending on how many the user asks for.
     *
     * @param jsonString is a JSON in the format of an object of GetPollsRequest.class
     * @return a JSON response message in the format of an object of GetPollsResponse.class
     */
    @RequestMapping(value = "/get_polls", method = RequestMethod.PUT)
    public ResponseEntity<String> getPolls(@RequestBody String jsonString) {
        GetPollsRequest request;
        try {
            request = this.objectMapper.readValue(jsonString, GetPollsRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
        }

        int startIdx = request.getStartIdx();
        int size = request.getSize();

        List<Poll> polls = this.pollCollection.getPollsSlice(startIdx, size);
        if (polls == null) {
            return new ResponseEntity<>("Incorrect startIdx given.", HttpStatus.BAD_REQUEST);
        }

        String pollsJsonString;
        try {
            pollsJsonString = this.objectMapper.writeValueAsString(polls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to form the JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(pollsJsonString, HttpStatus.OK);
    }

    /**
     * Method where the user places a vote on the given candidate in the given poll.
     *
     * @param jsonString in the format of an object of PlaceVoteRequest.class
     * @return a response message
     */
    @RequestMapping(value = "/place_vote", method = RequestMethod.PUT)
    public ResponseEntity<String> placeVote(@RequestBody String jsonString) {
        PlaceVoteRequest vote;
        try {
            vote = this.objectMapper.readValue(jsonString, PlaceVoteRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
        }

        String pollName = vote.getPollName();
        String candidateName = vote.getCandidateName();
        String userName = vote.getUserName();

        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }

        Poll currentPoll = this.pollCollection.getPoll(pollName);
        boolean candidateExists = currentPoll.doesCandidateExist(candidateName);
        if (!candidateExists) {
            return new ResponseEntity<>("Candidate does not exist.", HttpStatus.NOT_FOUND);
        }

        User user = new User(userName);
        boolean userHasVoted = currentPoll.hasUserVoted(user);
        if (userHasVoted) {
            return new ResponseEntity<>("User has already voted.", HttpStatus.METHOD_NOT_ALLOWED);
        }

        Candidate candidate = new Candidate(candidateName);
        currentPoll.placeVote(candidate, user);

        return new ResponseEntity<>("Vote placed.", HttpStatus.OK);
    }

}
