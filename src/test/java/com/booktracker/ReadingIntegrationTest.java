
package com.booktracker;

import com.booktracker.book.Book;
import com.booktracker.book.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ReadingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldRegisterAndListReading() throws Exception {

        Book book = bookRepository.save(
                new Book("O Hobbit", "J. R. R. Tolkien")
        );

        String requestBody = """
            {
                "bookId": %d,
                "month": 11,
                "year": 2026,
                "format": "PHYSICAL"
            }
            """.formatted(book.getId());



        mockMvc.perform(
                        post("/readings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.month").value(11))
                .andExpect(jsonPath("$.year").value(2026))
                .andExpect(jsonPath("$.format").value("PHYSICAL"))
                .andExpect(jsonPath("$.book.title").value("O Hobbit"));

        mockMvc.perform(get("/readings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].book.title").value("O Hobbit"))
                .andExpect(jsonPath("$[0].month").value(11))
                .andExpect(jsonPath("$[0].year").value(2026));
    }


    @Test
    void shouldRegisterBook() throws Exception {

        String requestBody = """
        {
            "title": "Dom Casmurro",
            "author": "Machado de Assis"
        }
        """;

        mockMvc.perform(
                        post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Dom Casmurro"))
                .andExpect(jsonPath("$.author").value("Machado de Assis"));
    }

}
