package com.altynnikov.VertagelabTestTask.repositories;

import com.altynnikov.VertagelabTestTask.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findById(long id);
    List<Book> findAll();

    List<Book> findByUserId(long userId);

    @Modifying
    @Query("UPDATE Book book set book.name = :name, book.author = :author where book.id = :id")
    @Transactional
    void updateBookById(@Param(value = "id") long id, @Param(value = "name") String name, @Param(value = "author") String author);

    @Modifying
    @Query("UPDATE Book book set book.userId = 0 where book.id = :id")
    @Transactional
    void returnBookById(@Param(value = "id") long id);

    @Modifying
    @Query("UPDATE Book book set book.userId = :userId where book.id = :id")
    @Transactional
    void takeABook(@Param(value = "id") long id, @Param(value = "userId") long userId);
}
