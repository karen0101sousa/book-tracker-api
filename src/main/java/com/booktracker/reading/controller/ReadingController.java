package com.booktracker.reading.controller;

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

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/readings")
public class ReadingController {

    private final ReadingService service;

    public ReadingController(ReadingService service) {
        this.service = service;
    }

    @Operation(
            summary = "Lista todas as leituras",
            description = "Retorna todas as leituras cadastradas, incluindo os dados dos livros associados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de leituras retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                                    schema = @Schema(implementation = Reading.class)
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<Reading>> getAllReadings() {
        return ResponseEntity.ok(service.getAllReadings());
    }

    @Operation(
            summary = "Cadastra uma leitura",
            description = "Registra a leitura de um livro em determinado mês, ano e formato."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados necessários para registrar uma leitura.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReadingRequest.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "bookId": 1,
                                      "month": 11,
                                      "year": 2026,
                                      "format": "PHYSICAL"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Leitura cadastrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Reading.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "status": 400,
                                              "error": "Validation failed",
                                              "messages": {
                                                "month": "Month must be between 1 and 12"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "status": 404,
                                              "error": "Book not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Leitura já cadastrada para esse livro, mês e ano",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "status": 409,
                                              "error": "Reading already registered for this book, month and year"
                                            }
                                            """
                            )
                    )
            )
    })
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
