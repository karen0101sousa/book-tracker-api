package com.booktracker.book.controller;

import com.booktracker.book.Book;
import com.booktracker.book.dto.BookRequest;
import com.booktracker.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastra um livro",
            description = "Registra um novo livro com título e autor."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Livro cadastrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "status": 400,
                                              "error": "Validation failed",
                                              "messages": {
                                                "title": "Title is required"
                                              }
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Book> registerBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do livro que será cadastrado",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "title": "O Hobbit",
                                              "author": "J. R. R. Tolkien"
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody BookRequest request) {

        Book book = service.registerBook(
                request.getTitle(),
                request.getAuthor()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }
}