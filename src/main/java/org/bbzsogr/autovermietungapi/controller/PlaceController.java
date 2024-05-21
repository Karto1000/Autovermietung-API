package org.bbzsogr.autovermietungapi.controller;

import jakarta.validation.Valid;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.exceptions.RouteException;
import org.bbzsogr.autovermietungapi.model.Place;
import org.bbzsogr.autovermietungapi.repository.PlaceRepository;
import org.bbzsogr.autovermietungapi.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JWTTokenDecoder tokenValidator;

    @PostMapping("")
    public SuccessResponse<Place> create(
            @RequestBody @Valid Place place,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("create:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        try {
            Place savedPlace = placeRepository.save(place);
            return SuccessResponse.ok(savedPlace);
        } catch (Exception e) {
            throw new RouteException("Failed to create place", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<String> delete(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("delete:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        try {
            placeRepository.deleteById(id);
            return SuccessResponse.ok("Place deleted");
        } catch (Exception e) {
            throw new RouteException("Failed to delete place", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public SuccessResponse<Place> put(
            @PathVariable Integer id,
            @RequestBody @Valid Place newPlace,
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("edit:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        if (!placeRepository.existsById(id)) throw new RouteException("Place not found", HttpStatus.NOT_FOUND);

        newPlace.setId(id);
        Place savedPlace = placeRepository.save(newPlace);
        return SuccessResponse.ok(savedPlace);
    }

    @GetMapping("")
    public SuccessResponse<Iterable<Place>> getAll(
            @RequestHeader("Authorization") String bearer
    ) throws RouteException {
        Claims claims = tokenValidator.decode(bearer).orElseThrow(() -> new RouteException("Invalid token", HttpStatus.UNAUTHORIZED));
        if (!claims.hasPermission("read:place")) throw new RouteException("Permission denied", HttpStatus.FORBIDDEN);

        return SuccessResponse.ok(placeRepository.findAll());
    }
}
