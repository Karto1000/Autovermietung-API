package org.bbzsogr.autovermietungapi.controller;

import org.bbzsogr.autovermietungapi.authentication.JWTTokenGenerator;
import org.bbzsogr.autovermietungapi.authentication.LoginRequestDao;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.Role;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.bbzsogr.autovermietungapi.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public SuccessResponse<String> login(@RequestBody LoginRequestDao loginRequestDao) throws RouteException {
        jwtTokenGenerator.getToken(User.builder().id(1).email("admin@admin.com").role(Role.builder().id(1).name("test").build()).build());

        // TODO: This is a hardcoded admin user for testing purposes
        if (!loginRequestDao.getEmail().equals("admin@admin.com") || !loginRequestDao.getPassword().equals("admin")) {
            throw new RouteException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(loginRequestDao.getEmail());
        if (user == null) throw new RouteException("Invalid credentials", HttpStatus.UNAUTHORIZED);

        return SuccessResponse.ok(jwtTokenGenerator.getToken(user));
    }
}
