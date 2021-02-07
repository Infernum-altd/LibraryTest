package com.altynnikov.VertagelabTestTask.controllers;

import com.altynnikov.VertagelabTestTask.models.Book;
import com.altynnikov.VertagelabTestTask.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        return ResponseEntity.ok(bookRepository.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUserById(@PathVariable long id) {
        bookRepository.deleteById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/add/{name}/{author}")
    public HttpStatus addBook(@PathVariable String name, @PathVariable String author) {
        bookRepository.save(new Book(name, author));
        return HttpStatus.OK;
    }

    @PostMapping("/update")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book) {
        bookRepository.updateBookById(book.getId(), book.getName(), book.getAuthor());
        return ResponseEntity.ok(bookRepository.findById(book.getId()));
    }

    @PostMapping("/return/{id}")
    public HttpStatus returnBook(@PathVariable long id) {
        bookRepository.returnBookById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/take/{bookId}/{userId}")
    public ResponseEntity<Object> takeBookById(@PathVariable long bookId, @PathVariable long userId) {
        Book book = bookRepository.findById(bookId);
        if (book.getUserId() == 0) {
            bookRepository.takeABook(bookId, userId);
            return ResponseEntity.ok(bookRepository.findById(bookId));
        }
        return ResponseEntity.badRequest().body("Book already has been taken");
    }
}
