package org.bbzsogr.autovermietungapi.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTTokenDecoder implements TokenDecoder<Claims> {
    @Override
    public boolean validate(String token) {
        // TODO: This is just for testing, this should be implemented properly
        return true;
    }

    @Override
    public Optional<Claims> decode(String token) {
        String actualToken;
        try {
            actualToken = token.split("Bearer ")[1];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        if (!this.validate(actualToken)) return Optional.empty();

        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(actualToken);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        Claims claims = Claims.builder()
                .permissions(decodedJWT.getClaim("permissions").asArray(String.class))
                .email(decodedJWT.getClaim("email").asString())
                .build();

        return Optional.of(claims);
    }

}
