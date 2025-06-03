package com.example.library.model.service;

import com.example.library.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAuthorService
{
    Page<Author> findAll(Pageable pageable);
    Optional<Author> findById(Integer id);
    Author save(Author author);
    void deleteById(Integer id);
}
