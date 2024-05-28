package org.bbzsogr.autovermietungapi.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firms")
public class FirmController {
    @DeleteMapping("/{id}")
    public void delete() {
        // TODO
    }

    @GetMapping("/")
    public void search() {
        // TODO
    }
}
