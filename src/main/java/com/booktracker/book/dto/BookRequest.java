package com.booktracker.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookRequest {

    @Schema(
            description = "Título do livro (máximo de 150 caracteres)",
            example = "O Hobbit"
    )
    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must have at most 150 characters")
    private String title;

    @Schema(
            description = "Nome do autor (máximo de 150 caracteres)",
            example = "J. R. R. Tolkien"
    )
    @NotBlank(message = "Author is required")
    @Size(max = 150, message = "Author must have at most 150 characters")
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
