package com.example.library.model.repository;

import com.example.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>
{
    List<Book> findByAuthorId(Integer authorId);
    List<Book> findTop10ByOrderByIdDesc();
}
