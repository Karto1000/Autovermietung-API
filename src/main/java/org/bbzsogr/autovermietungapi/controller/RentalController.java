package org.bbzsogr.autovermietungapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @PutMapping("/{id}/cancel")
    public void cancel() {
        // TODO
    }

    @GetMapping("/")
    public void search() {
        // TODO
    }
}
