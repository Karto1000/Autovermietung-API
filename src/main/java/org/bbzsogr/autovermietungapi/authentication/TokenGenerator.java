package org.bbzsogr.autovermietungapi.authentication;

import org.bbzsogr.autovermietungapi.model.User;

public interface TokenGenerator {
    String getToken(User user);
}
