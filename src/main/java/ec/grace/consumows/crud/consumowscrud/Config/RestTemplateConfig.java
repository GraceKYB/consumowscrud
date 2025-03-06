package ec.grace.consumows.crud.consumowscrud.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @Qualifier("restTemplateCustomer")
    public RestTemplate restTemplateCustomer() {
        return new RestTemplate();
    }
}
