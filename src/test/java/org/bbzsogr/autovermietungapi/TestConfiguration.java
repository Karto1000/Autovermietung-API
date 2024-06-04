package org.bbzsogr.autovermietungapi;

import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    public JWTTokenDecoder jwtTokenDecoder() {
        return Mockito.mock(JWTTokenDecoder.class);
    }
}
