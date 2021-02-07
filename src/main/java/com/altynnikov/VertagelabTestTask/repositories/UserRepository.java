package com.altynnikov.VertagelabTestTask.repositories;

import com.altynnikov.VertagelabTestTask.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);

    List<User> findAll();

    @Modifying
    @Query("update User usr set usr.name = :name where usr.id = :id")
    @Transactional
    void updateNameById(@Param(value = "name") String name, @Param(value = "id") long id);

}
