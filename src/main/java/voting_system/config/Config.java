package voting_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import voting_system.model.PollCollection;
import voting_system.model.UserCollection;

@Configuration
public class Config {

    @Bean
    public PollCollection createPollCollection() {
        return new PollCollection();
    }

    @Bean
    public UserCollection createUserCollection() {
        return new UserCollection();
    }

}
