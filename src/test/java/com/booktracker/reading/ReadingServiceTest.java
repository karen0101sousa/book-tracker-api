
package com.booktracker.reading;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.booktracker.book.Book;
import com.booktracker.book.repository.BookRepository;
import com.booktracker.common.exception.BookNotFoundException;
import com.booktracker.common.exception.DuplicateReadingException;
import com.booktracker.reading.Reading;
import com.booktracker.reading.ReadingFormat;
import com.booktracker.reading.repository.ReadingRepository;

import com.booktracker.reading.service.ReadingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ReadingServiceTest {

    @Mock
    private ReadingRepository readingRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReadingService service;

    @Test
    void shouldRejectDuplicateReading() {

        Book book = new Book("Clean Code", "Robert C. Martin");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(readingRepository.existsByBookAndMonthAndYear(book, 1, 2026))
                .thenReturn(true);

        assertThrows(
                DuplicateReadingException.class,
                () -> service.registerReading(
                        1L, 1, 2026, ReadingFormat.PHYSICAL)
        );

        verify(readingRepository, never()).save(any(Reading.class));
    }

    @Test
    void shouldSaveReadingWhenItDoesNotExist() {

        Book book = new Book("Clean Code", "Robert C. Martin");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(readingRepository.existsByBookAndMonthAndYear(book, 1, 2026))
                .thenReturn(false);

        service.registerReading(
                1L, 1, 2026, ReadingFormat.PHYSICAL);

        verify(readingRepository).save(any(Reading.class));
    }

    @Test
    void shouldRejectReadingWhenBookDoesNotExist() {

        when(bookRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> service.registerReading(
                        99L, 1, 2026, ReadingFormat.PHYSICAL)
        );

        verify(readingRepository, never())
                .existsByBookAndMonthAndYear(any(), any(), any());

        verify(readingRepository, never()).save(any(Reading.class));
    }

    @Test
    void shouldReturnAllReadings() {
        Book book = new Book("O Hobbit", "J. R. R. Tolkien");

        Reading reading1 = new Reading(book, 1, 2026, ReadingFormat.PHYSICAL);
        Reading reading2 = new Reading(book, 2, 2026, ReadingFormat.DIGITAL);

        when(readingRepository.findAll()).thenReturn(List.of(reading1, reading2));

        List<Reading> result = service.getAllReadings();

        assertEquals(2, result.size());
        verify(readingRepository).findAll();
    }
}
