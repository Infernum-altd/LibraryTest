package com.altynnikov.VertagelabTestTask.controllers;

import com.altynnikov.VertagelabTestTask.models.Book;
import com.altynnikov.VertagelabTestTask.models.User;
import com.altynnikov.VertagelabTestTask.repositories.BookRepository;
import com.altynnikov.VertagelabTestTask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public UserController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok((userRepository.findById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUserById(@PathVariable long id) {
        userRepository.deleteById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/create/{name}")
    public HttpStatus createUser(@PathVariable String name) {
        userRepository.save(new User(name));
        return HttpStatus.OK;
    }

    @PostMapping("/change/{id}/{newName}")
    public HttpStatus changeUserNameById(@PathVariable long id, @PathVariable String newName) {
        userRepository.updateNameById(newName, id);
        return HttpStatus.OK;
    }

    @GetMapping("/own/{userId}")
    public ResponseEntity<User> getUserWithBooks(@PathVariable long userId) {
        User user = userRepository.findById(userId);
        List<Book> books = bookRepository.findByUserId(userId);
        user.setBooks(books);
        return ResponseEntity.ok(user);
    }

}
