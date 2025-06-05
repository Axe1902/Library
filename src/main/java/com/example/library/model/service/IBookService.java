package com.example.library.model.service;

import com.example.library.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookService
{
    Page<Book> findAllOrderByTitle(Pageable pageable);
    Page<Book> findAllOrderByTitleDesc(Pageable pageable);
    Optional<Book> findById(Integer id);
    List<Book> findByAuthorId(Integer authorId);
    List<Book> findTop10ByOrderByIdDesc();
    List<Book> findByTitle(String title);
    Book save(Book book);
    void deleteById(Integer id);
}
