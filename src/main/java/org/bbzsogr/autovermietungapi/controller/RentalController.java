package org.bbzsogr.autovermietungapi.controller;

import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.Car;
import org.bbzsogr.autovermietungapi.model.Rental;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.CarRepository;
import org.bbzsogr.autovermietungapi.repository.RentalRepository;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @PutMapping("/{carId}/cancel")
    public ResponseEntity<String> cancel(
            @PathVariable Integer carId,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("delete:rental")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        User user = userRepository
                .findByEmail(claims.getEmail())
                .orElseThrow(() -> new RouteException("User not found", HttpStatus.NOT_FOUND));

        Car car = carRepository
                .findById(carId)
                .orElseThrow(() -> new RouteException("Car not found", HttpStatus.NOT_FOUND));

        Rental rental = rentalRepository
                .findByCarIdAndUserId(car.getId(), user.getId())
                .orElseThrow(() -> new RouteException("Rental not found", HttpStatus.NOT_FOUND));

        try {
            rentalRepository.deleteById(rental.getId());
            return ResponseEntity.ok("Rental deleted");
        } catch (Exception e) {
            throw new RouteException(
                    "Error while deleting rental",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/")
    public void search() {
        // TODO
    }
}
