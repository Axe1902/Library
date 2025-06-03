package com.example.library.controllers;

import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Book;
import com.example.library.model.service.IBookService;
import com.example.library.model.utils.IDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController
{
    private final IBookService bookService;
    private final IDtoMapper dtoMapper;

    public BookController(IBookService bookService, IDtoMapper dtoMapper)
    {
        this.bookService = bookService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public Page<BookDto> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    )
    {
        Pageable pageable = PageRequest.of(page, limit);

        return bookService.findAll(pageable).map(dtoMapper::toDto);
    }

    @GetMapping("/last10")
    public List<BookDto> getLastAddedBooks()
    {
        return bookService.findTop10ByOrderByIdDesc().stream().map(dtoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Integer id)
    {
        Book result = bookService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        return ResponseEntity.ok(dtoMapper.toDto(result));
    }

    @GetMapping("/author/{id}")
    public List<BookDto> getBooksByAuthorId(@PathVariable Integer authorId)
    {
        return bookService.findByAuthorId(authorId).stream().map(dtoMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook(@PathVariable Book book)
    {
        bookService.save(book);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Book updatedBook)
    {
        var id = updatedBook.getId();

        bookService.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setDescription(updatedBook.getDescription());
                    book.setAuthor(updatedBook.getAuthor());
                    return bookService.save(book);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id)
    {
        bookService.deleteById(id);
    }
}
