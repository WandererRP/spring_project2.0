package com.rol.project2.repositories;

import com.rol.project2.models.Book;
import com.rol.project2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);
    List<Book> findByBookNameStartingWith(String query);
}
