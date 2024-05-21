package org.bbzsogr.autovermietungapi.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class Claims {
    private String[] permissions;

    public boolean hasPermission(String permission) {
        List<String> permissions = Arrays.stream(this.permissions).toList();
        return permissions.contains(permission);
    }
}
