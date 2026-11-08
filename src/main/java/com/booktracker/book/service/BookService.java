package com.booktracker.book.service;

import com.booktracker.book.Book;
import com.booktracker.book.dto.BookRequest;
import com.booktracker.book.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book registerBook(BookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor()
        );

        return repository.save(book);
    }
}