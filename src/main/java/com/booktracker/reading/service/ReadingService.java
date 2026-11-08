package com.booktracker.reading.service;

import com.booktracker.book.Book;
import com.booktracker.book.repository.BookRepository;
import com.booktracker.common.exception.BookNotFoundException;
import com.booktracker.common.exception.DuplicateReadingException;
import com.booktracker.reading.Reading;
import com.booktracker.reading.ReadingFormat;
import com.booktracker.reading.repository.ReadingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service


public class ReadingService {

    public List<Reading> getAllReadings() {
        return readingRepository.findAll();
    }

    private final ReadingRepository readingRepository;
    private final BookRepository bookRepository;

    public ReadingService(
            ReadingRepository readingRepository,
            BookRepository bookRepository) {
        this.readingRepository = readingRepository;
        this.bookRepository = bookRepository;
    }

    public Reading registerReading(
            Long bookId,
            Integer month,
            Integer year,
            ReadingFormat format) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found"));

        boolean alreadyExists =
                readingRepository.existsByBookAndMonthAndYear(
                        book, month, year);

        if (alreadyExists) {
            throw new DuplicateReadingException(
                    "Reading already registered for this book, month and year");
        }

        Reading reading = new Reading(book, month, year, format);

        return readingRepository.save(reading);
    }
}