package org.bbzsogr.autovermietungapi.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bbzsogr.autovermietungapi.model.Permission;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JWTTokenGenerator implements TokenGenerator {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String getToken(User user) {
        // TODO: This is just for testing, can be replaced with actual implementation
        Algorithm algorithm = Algorithm.none();

        List<Permission> permissions = roleRepository.getPermissionsByRoleId(user.getRole().getId());

        return JWT.create()
                .withIssuer("autovermietung-api")
                .withClaim("email", user.getEmail())
                .withClaim("jamie", "Do you want Toys?")
                .withArrayClaim("permissions", permissions.stream().map(Permission::getName).toArray(String[]::new))
                .sign(algorithm);
    }
}
