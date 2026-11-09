package com.booktracker.book.service;

import com.booktracker.book.Book;
import com.booktracker.book.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book registerBook(String title, String author) {
        Book book = new Book(title, author);

        return repository.save(book);
    }
}