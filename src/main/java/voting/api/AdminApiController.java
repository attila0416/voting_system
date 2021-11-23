package voting.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voting.model.*;
import voting.storage.Candidate;
import voting.storage.Poll;
import voting.storage.PollCollection;
import voting.storage.User;

import java.util.Map;

@RestController()
@RequestMapping(value = "/admin")
public class AdminApiController {

    private final PollCollection pollCollection;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    /**
     * Class that controls the API communication between the admin and the server.
     *
     * @param pollCollection is the collection of polls in one place
     */
    @Autowired
    public AdminApiController(PollCollection pollCollection) {
        this.pollCollection = pollCollection;
        this.objectMapper = new ObjectMapper();
        this.logger = LoggerFactory.getLogger(AdminApiController.class);
    }

    /**
     * Return a default response when the default path is hit by the admin
     *
     * @return a response message
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("You have reached the Voting System admin API!", HttpStatus.OK);
    }

    /**
     * Creates a poll and the candidates in it.
     *
     * @param jsonString is a JSON in the format of an object of CreatePollRequest.class
     * @return a response message
     */
    @RequestMapping(value = "/create_poll", method = RequestMethod.POST)
    public ResponseEntity<String> createPoll(@RequestBody String jsonString) {
        CreatePollRequest request;
        try {
            request = this.objectMapper.readValue(jsonString, CreatePollRequest.class);
        } catch (JsonProcessingException e) {
            this.logger.error("Invalid input format.", e);
            return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
        }

        String pollName = request.getPollName();
        Map<Candidate, Integer> candidates = request.getCandidates();
        Poll poll = new Poll(pollName, candidates);

        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (pollExists) {
            return new ResponseEntity<>("Poll already exists.", HttpStatus.CONFLICT);
        }

        this.pollCollection.addPoll(pollName, poll);

        return new ResponseEntity<>("Poll created.", HttpStatus.OK);
    }

    /**
     * Closes a poll and returns the information about the closed poll to the admin.
     *
     * @param pollName is the name of the poll
     * @return JSON information about the closed poll
     */
    @RequestMapping(value = "/close_poll/{pollName}", method = RequestMethod.DELETE)
    public ResponseEntity<String> closePoll(@PathVariable String pollName) {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }

        Poll removedPoll = this.pollCollection.removePoll(pollName);
        Map<String, Integer> candidates = removedPoll.getCandidatesStandings();
        String winnerCandidate = removedPoll.getWinner();

        ClosePollResponse response = new ClosePollResponse(pollName, candidates, winnerCandidate);
        String JsonStringResponse;
        try {
            JsonStringResponse = this.objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            this.logger.error("Unable to form the JSON response.", e);
            return new ResponseEntity<>("Unable to form the JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonStringResponse, HttpStatus.OK);
    }

    /**
     * Gets the current vote standings of a poll.
     *
     * @param pollName is the name of the poll
     * @return JSON information about the current vote standings of a poll.
     */
    @RequestMapping(value = "/get_votes_status/{pollName}", method = RequestMethod.GET)
    public ResponseEntity<String> getVotesStatus(@PathVariable String pollName) {
        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }

        Poll currentPoll = this.pollCollection.getPoll(pollName);
        Map<String, Integer> candidates = currentPoll.getCandidatesStandings();
        GetVotesStatusResponse response = new GetVotesStatusResponse(candidates);

        String JsonStringResponse;
        try {
            JsonStringResponse = this.objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            this.logger.error("Unable to form the JSON response.", e);
            return new ResponseEntity<>("Unable to form the JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonStringResponse, HttpStatus.OK);
    }

    /**
     * Checks whether a user has voted on a certain poll.
     *
     * @param jsonString is a JSON in the format of an object of GetIfUserVotedRequest.class
     * @return JSON information whether a user has voted the specified poll.
     */
    @RequestMapping(value = "/get_if_user_voted", method = RequestMethod.PUT)
    public ResponseEntity<String> getIfUserVoted(@RequestParam String jsonString) {
        GetIfUserVotedRequest request;
        try {
            request = this.objectMapper.readValue(jsonString, GetIfUserVotedRequest.class);
        } catch (JsonProcessingException e) {
            this.logger.error("Invalid input format.", e);
            return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
        }

        String pollName = request.getPollName();
        String userName = request.getUserName();

        boolean pollExists = this.pollCollection.checkPollExists(pollName);
        if (!pollExists) {
            return new ResponseEntity<>("Poll does not exist.", HttpStatus.NOT_FOUND);
        }

        Poll currentPoll = this.pollCollection.getPoll(pollName);
        User user = new User(userName);
        boolean userHasVoted = currentPoll.hasUserVoted(user);
        GetIfUserVotedResponse response = new GetIfUserVotedResponse(userHasVoted);

        String JsonStringResponse;
        try {
            JsonStringResponse = this.objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            this.logger.error("Unable to form the JSON response.", e);
            return new ResponseEntity<>("Unable to form the JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonStringResponse, HttpStatus.OK);
    }

}
