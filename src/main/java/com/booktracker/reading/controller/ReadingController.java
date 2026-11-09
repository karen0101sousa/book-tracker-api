package com.booktracker.reading.controller;

import java.util.List;

import com.booktracker.reading.Reading;
import com.booktracker.reading.dto.ReadingRequest;
import com.booktracker.reading.service.ReadingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readings")
public class ReadingController {

    @GetMapping
    public ResponseEntity<List<Reading>> getAllReadings() {

        List<Reading> readings = service.getAllReadings();

        return ResponseEntity.ok(readings);
    }

    private final ReadingService service;

    public ReadingController(ReadingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reading> registerReading(
            @Valid @RequestBody ReadingRequest request) {

        Reading reading = service.registerReading(
                request.getBookId(),
                request.getMonth(),
                request.getYear(),
                request.getFormat()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reading);
    }
}