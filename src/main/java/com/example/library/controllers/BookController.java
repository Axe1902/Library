package com.example.library.controllers;

import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Author;
import com.example.library.model.entity.Book;
import com.example.library.model.service.IAuthorService;
import com.example.library.model.service.IBookService;
import com.example.library.model.utils.IDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final IAuthorService authorService;
    private final IBookService bookService;
    private final IDtoMapper dtoMapper;

    public BookController(IAuthorService authorService, IBookService bookService, IDtoMapper dtoMapper)
    {
        this.authorService = authorService;
        this.bookService = bookService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public Page<BookDto> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    )
    {
        logger.info("Получен запрос на получение списка книг.");
        logger.debug("Страница: {}, лимит: {}", page, limit);

        Pageable pageable = PageRequest.of(page, limit);

        var result = bookService.findAll(pageable).map(dtoMapper::toDto);

        logger.info("Список книг получен.");
        logger.debug(result.stream().toList().toString());

        return result;
    }

    @GetMapping("/last10")
    public List<BookDto> getLastAddedBooks()
    {
        logger.info("Получен запрос на получение последних 10 добавленных книг.");

        var result = bookService.findTop10ByOrderByIdDesc().stream().map(dtoMapper::toDto).toList();

        logger.info("Список книг получен.");
        logger.debug(result.toString());

        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Integer id)
    {
        logger.info("Получен запрос на получение книги с идентификатором {}.", id);

        Book result = bookService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        logger.info("Книга с идентификатором {} получена.", id);

        return ResponseEntity.ok(dtoMapper.toDto(result));
    }

    @GetMapping("/author/{authorId}")
    public List<BookDto> getBooksByAuthorId(@PathVariable Integer authorId)
    {
        logger.info("Получен запрос на получение книги по идентификатору автора: {}.", authorId);

        return bookService.findByAuthorId(authorId).stream().map(dtoMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook(@RequestBody BookDto book)
    {
        logger.info("Получен запрос на добавление новой книги.");

        Author author = authorService.findById(book.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Для добавления книги не удалось найти автора с id " + book.authorId()));

        bookService.save(dtoMapper.toEntity(book, author));

        logger.info("Книга добавлена.");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@RequestBody BookDto updatedBookDto)
    {
        Author author = authorService.findById(updatedBookDto.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("При обновлении информации о книге не удалось найти автора с id " + updatedBookDto.authorId()));

        var updatedBook = dtoMapper.toEntity(updatedBookDto, author);

        var id = updatedBook.getId();

        logger.info("Получен запрос на обновление информации о книге с идентификатором {}.", id);

        bookService.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setDescription(updatedBook.getDescription());
                    book.setAuthor(updatedBook.getAuthor());
                    return bookService.save(book);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        logger.info("Информация о книге с идентификатором {} обновлена.", id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id)
    {
        logger.info("Получен запрос на удаление книги по идентификатору: {}.", id);

        bookService.deleteById(id);

        logger.info("Книга с идентификатором {} удалена.", id);
    }
}
