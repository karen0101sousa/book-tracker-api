package com.booktracker.reading;

import com.booktracker.book.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadingTest {

    @Test
    void shouldCreateReadingWithCorrectBook() {

        // Arrange: preparar os dados
        Book book = new Book("Clean Code", "Robert C. Martin");

        // Act: executar o comportamento
        Reading reading = new Reading(
                book,
                1,
                2026,
                ReadingFormat.PHYSICAL
        );

        // Assert: verificar o resultado
        assertSame(book, reading.getBook());
        assertEquals("Clean Code", reading.getBook().getTitle());
        assertEquals("Robert C. Martin", reading.getBook().getAuthor());
        assertEquals(1, reading.getMonth());
        assertEquals(2026, reading.getYear());
        assertEquals(ReadingFormat.PHYSICAL, reading.getFormat());
    }

    @Test
    void shouldNotAllowMonthGreaterThan12() {

        Book book = new Book("Clean Code", "Robert C. Martin");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Reading(
                        book,
                        13,
                        2026,
                        ReadingFormat.PHYSICAL
                )
        );
    }

    @Test
    void shouldNotAllowYearGreaterThan2100() {

        Book book = new Book("Clean Code", "Robert C. Martin");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Reading(
                        book,
                        1,
                        2101,
                        ReadingFormat.PHYSICAL
                )
        );
    }
}

