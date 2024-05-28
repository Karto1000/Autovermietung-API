package org.bbzsogr.autovermietungapi.controller;


import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firms")
public class FirmController {
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder
                .decode(bearer)
                .orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));

        if (!claims.hasPermission("delete:firm")) throw new RouteException(
                "Permission denied",
                HttpStatus.FORBIDDEN
        );

        User user = userRepository
                .findByEmail(claims.getEmail())
                .orElseThrow(() -> new RouteException("User not found", HttpStatus.NOT_FOUND));

        switch (user.getRole().getName()) {
            case "admin":
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            case "firm":
                if (!user.getId().equals(id)) throw new RouteException(
                        "Permission denied",
                        HttpStatus.FORBIDDEN
                );

                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            default:
                throw new RouteException(
                        "Permission denied",
                        HttpStatus.FORBIDDEN
                );
        }
    }

    @GetMapping("/")
    public void search() {
        // TODO
    }
}
