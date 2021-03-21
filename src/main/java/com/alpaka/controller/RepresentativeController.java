package com.alpaka.controller;

import com.alpaka.model.representative.Representative;
import com.alpaka.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class RepresentativeController {

    @Autowired
    private RepresentativeService representativeService;

    @GetMapping("/representatives")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Representative>> getRepresentativeList() {
        return representativeService.getRepresentativeList();
    }

    @GetMapping("/representatives/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Representative getRepresentativeById(@PathVariable("id") String isbn) {
        return representativeService.getRepresentative(isbn);
    }

    @PostMapping("/representatives")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createRepresentative(@Valid @RequestBody Representative representative) {
        return representativeService.createRepresentative(representative);

    }

    @DeleteMapping("/representative/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HttpStatus> deleteRepresentative(@PathVariable("id") String id) {
        return representativeService.deleteRepresentative(Long.valueOf(id));
    }
}

