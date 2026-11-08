package com.booktracker.reading.repository;

import com.booktracker.book.Book;
import com.booktracker.reading.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    boolean existsByBookAndMonthAndYear(
            Book book,
            Integer month,
            Integer year
    );

}