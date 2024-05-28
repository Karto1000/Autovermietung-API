package org.bbzsogr.autovermietungapi.controller;

import jakarta.validation.Valid;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.dto.PlaceDTO;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.Place;
import org.bbzsogr.autovermietungapi.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JWTTokenDecoder tokenValidator;

    @PostMapping("")
    public ResponseEntity<Place> create(
            @RequestBody @Valid PlaceDTO place,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("create:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        try {
            Place savedPlace = placeRepository.save(place.intoEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlace);
        } catch (Exception e) {
            throw new RouteException("Failed to create place", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("delete:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        if (!placeRepository.existsById(id)) throw new RouteException("Place not found", HttpStatus.NOT_FOUND);

        try {
            placeRepository.deleteById(id);
            return ResponseEntity.ok("Place deleted");
        } catch (Exception e) {
            throw new RouteException("Failed to delete place", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> put(
            @PathVariable Integer id,
            @RequestBody @Valid PlaceDTO newPlace,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("edit:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        if (!placeRepository.existsById(id)) throw new RouteException("Place not found", HttpStatus.NOT_FOUND);

        Place place = newPlace.intoEntity();
        place.setId(id);
        Place savedPlace = placeRepository.save(place);
        return ResponseEntity.ok(savedPlace);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Place>> getAll(
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("read:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(placeRepository.findAll());
    }
}
