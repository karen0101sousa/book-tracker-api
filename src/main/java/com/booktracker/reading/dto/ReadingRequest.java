package com.booktracker.reading.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.booktracker.reading.ReadingFormat;
import io.swagger.v3.oas.annotations.media.Schema;

public class ReadingRequest {

    @Schema(description = "Identificador do livro", example = "1")
    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be positive")
    private Long bookId;

    @Schema(description = "Mês da leitura (1 a 12)", example = "11")
    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @Schema(description = "Ano da leitura (1900 a 2100)", example = "2026")
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be between 1900 and 2100")
    @Max(value = 2100, message = "Year must be between 1900 and 2100")
    private Integer year;

    @Schema(
            description = "Formato da leitura",
            example = "PHYSICAL",
            allowableValues = {"PHYSICAL", "DIGITAL"}
    )
    @NotNull(message = "Format is required")
    private ReadingFormat format;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ReadingFormat getFormat() {
        return format;
    }

    public void setFormat(ReadingFormat format) {
        this.format = format;
    }
}
