package com.example.library.model.service;

import com.example.library.model.entity.Book;
import com.example.library.model.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService
{
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> findTop10ByOrderByIdDesc()
    {
        return bookRepository.findTop10ByOrderByIdDesc();
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findByAuthorId(Integer authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }
}
