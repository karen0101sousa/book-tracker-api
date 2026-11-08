package com.booktracker;

import com.booktracker.book.Book;
import com.booktracker.reading.Reading;
import com.booktracker.reading.ReadingFormat;

public class DomainTest {

    public static void main(String[] args) {

        // Criando um livro
        Book book = new Book("Clean Code", "Robert C. Martin");

        // Criando um registro de leitura associado ao livro
        Reading reading = new Reading(
                book,
                1,
                2026,
                ReadingFormat.PHYSICAL
        );

        // Exibindo os dados da leitura
        System.out.println("DADOS DA LEITURA");
        System.out.println("--------------------");
        System.out.println("Livro: " + reading.getBook().getTitle());
        System.out.println("Autor: " + reading.getBook().getAuthor());
        System.out.println("Mês: " + reading.getMonth());
        System.out.println("Ano: " + reading.getYear());
        System.out.println("Formato: " + reading.getFormat());
    }
}