package com.booktracker.reading.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.booktracker.reading.ReadingFormat;

public class ReadingRequest {

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be positive")
    private Long bookId;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be between 1900 and 2100")
    @Max(value = 2100, message = "Year must be between 1900 and 2100")
    private Integer year;

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
