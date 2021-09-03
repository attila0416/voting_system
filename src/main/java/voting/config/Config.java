package voting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import voting.storage.PollCollection;

@Configuration
public class Config {

    @Bean
    public PollCollection createPollCollection() {
        return new PollCollection();
    }

}
