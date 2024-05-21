package org.bbzsogr.autovermietungapi.authentication;
import java.util.Optional;


public interface TokenDecoder<T> {
    boolean validate(String token);

    Optional<T> decode(String token);
}
