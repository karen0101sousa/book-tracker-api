package com.booktracker.book.controller;

import com.booktracker.book.Book;
import com.booktracker.book.dto.BookRequest;
import com.booktracker.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Book> registerBook(
            @Valid @RequestBody BookRequest request) {

        Book book = service.registerBook(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(book);
    }
}