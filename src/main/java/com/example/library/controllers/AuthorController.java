package com.example.library.controllers;

import com.example.library.model.dto.AuthorDto;
import com.example.library.model.entity.Author;
import com.example.library.model.service.IAuthorService;
import com.example.library.model.utils.IDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController
{
    private final IAuthorService authorService;
    private final IDtoMapper dtoMapper;

    public AuthorController(IAuthorService authorService, IDtoMapper dtoMapper)
    {
        this.authorService = authorService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public Page<AuthorDto> getAllAuthors(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    )
    {
        Pageable pageable = PageRequest.of(page, limit);

        return authorService.findAll(pageable).map(dtoMapper::toDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Integer id)
    {
        Author result = authorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find author with id=" + id));

        return ResponseEntity.ok(dtoMapper.toDto(result));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addAuthor(@RequestBody AuthorDto author)
    {
        authorService.save(dtoMapper.toEntity(author));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateAuthor(@RequestBody AuthorDto updatedAuthorDto)
    {
        var updatedAuthor = dtoMapper.toEntity(updatedAuthorDto);

        var id = updatedAuthor.getId();

        authorService.findById(id)
                .map(author -> {
                    author.setFirstName(updatedAuthor.getFirstName());
                    author.setLastName(updatedAuthor.getLastName());
                    author.setPatronymic(updatedAuthor.getPatronymic());
                    author.setDateOfBirth(updatedAuthor.getDateOfBirth());
                    author.setDateOfDeath(updatedAuthor.getDateOfDeath());
                    author.setBiography(updatedAuthor.getBiography());
                    return authorService.save(author);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find author with id=" + id));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Integer id)
    {
        authorService.deleteById(id);
    }
}
