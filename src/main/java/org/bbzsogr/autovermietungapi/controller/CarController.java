package org.bbzsogr.autovermietungapi.controller;

import jakarta.validation.Valid;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.dto.CarDTO;
import org.bbzsogr.autovermietungapi.dto.RentDTO;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.*;
import org.bbzsogr.autovermietungapi.repository.CarRepository;
import org.bbzsogr.autovermietungapi.repository.FirmRepository;
import org.bbzsogr.autovermietungapi.repository.RentalRepository;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenDecoder tokenDecoder;

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Car> create(
            @RequestBody @Valid CarDTO carDTO,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("create:car")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        Car car = carDTO.intoEntity();

        Firm firm = firmRepository.findByEmail(claims.getEmail()).orElseThrow(() -> new RouteException("User not found", HttpStatus.NOT_FOUND));
        car.setFirm(firm);

        try {
            Car savedCar = carRepository.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
        } catch (Exception e) {
            throw new RouteException("Failed to create car", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("delete:car")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        if (!carRepository.existsById(id)) throw new RouteException("Car not found", HttpStatus.NOT_FOUND);

        try {
            carRepository.deleteById(id);
            return ResponseEntity.ok("Car deleted");
        } catch (Exception e) {
            throw new RouteException("Failed to delete car", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Car> put(
            @PathVariable Integer id,
            @RequestBody @Valid CarDTO newCar,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("edit:car")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        if (!carRepository.existsById(id)) throw new RouteException("Car not found", HttpStatus.NOT_FOUND);

        Car car = newCar.intoEntity();
        car.setId(id);

        try {
            carRepository.save(car);
        } catch (Exception e) {
            throw new RouteException("Failed to edit car", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(car);
    }

    @GetMapping("/")
    public void search() {
        // TODO
    }

    @PostMapping(value = "/{id}/rent", consumes = "application/json")
    public ResponseEntity<Rental> rent(
            @PathVariable Integer id,
            @RequestBody @Valid RentDTO rentDTO,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenDecoder
                .decode(bearer)
                .orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));

        if (!claims.hasPermission("rent:car")) throw new RouteException(
                "Permission denied",
                HttpStatus.FORBIDDEN
        );

        if (!carRepository.existsById(id)) throw new RouteException(
                "Car not found",
                HttpStatus.NOT_FOUND
        );

        User user = userRepository
                .findByEmail(claims.getEmail())
                .orElseThrow(() -> new RouteException("User not found", HttpStatus.NOT_FOUND));
        Car car = carRepository
                .findById(id)
                .orElseThrow(() -> new RouteException("Car not found", HttpStatus.NOT_FOUND));

        if (rentalRepository.existsByCarId(car.getId())) throw new RouteException(
                "Car is already rented",
                HttpStatus.BAD_REQUEST
        );

        Rental rental = Rental.builder()
                .start(rentDTO.getStart())
                .end(rentDTO.getEnd())
                .id(RentalId.builder()
                        .car(car)
                        .user(user)
                        .build()
                )
                .build();

        try {
            rentalRepository.save(rental);
            return ResponseEntity.ok(rental);
        } catch (Exception e) {
            throw new RouteException("Failed to rent car", HttpStatus.BAD_REQUEST);
        }
    }
}
