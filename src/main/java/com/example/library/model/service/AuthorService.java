package com.example.library.model.service;

import com.example.library.model.entity.Author;
import com.example.library.model.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService
{
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Author> findAllOrderByFirstName(Pageable pageable) {
        return authorRepository.findAllByOrderByFirstName(pageable);
    }

    @Override
    public Page<Author> findAllOrderByFirstNameDesc(Pageable pageable) {
        return authorRepository.findAllByOrderByFirstNameDesc(pageable);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findByFirstName(String firstName) {
        return authorRepository.findByFirstNameILike(firstName);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }
}
