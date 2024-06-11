package org.bbzsogr.autovermietungapi.controller;

import org.bbzsogr.autovermietungapi.authentication.JWTTokenGenerator;
import org.bbzsogr.autovermietungapi.authentication.LoginRequestDao;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.Role;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // TODO: These are hardcoded users for testing purposes
    private static final List<String> validEmails = Arrays.asList("admin@admin.com", "user@user.com", "firm@firm.com");
    private static final List<String> validPasswords = Arrays.asList("admin", "user", "firm");

    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginRequestDao loginRequestDao) throws RouteException {
        if (!validEmails.contains(loginRequestDao.getEmail()) || !validPasswords.contains(loginRequestDao.getPassword())) {
            throw new RouteException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(loginRequestDao.getEmail()).orElseThrow(() -> new RouteException("Invalid credentials", HttpStatus.UNAUTHORIZED));
        if (user == null) throw new RouteException("Invalid credentials", HttpStatus.UNAUTHORIZED);

        return ResponseEntity.ok(jwtTokenGenerator.getToken(user));
    }
}
