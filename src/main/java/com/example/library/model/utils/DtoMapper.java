package com.example.library.model.utils;

import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Author;
import com.example.library.model.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper implements IDtoMapper
{
    public BookDto toDto(Book book)
    {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthor().getId());
    }

    public BookDto toDto(Book book, Integer authorId)
    {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                authorId);
    }

    public Book toEntity(BookDto bookDto, Author author)
    {
        if (!Objects.equals(author.getId(), bookDto.authorId()))
        {
            throw new RuntimeException("");
        }

        Book book = new Book();

        book.setId(bookDto.id());
        book.setTitle(bookDto.title());
        book.setDescription(bookDto.description());
        book.setAuthor(author);

        return book;
    }

    public AuthorDto toDto(Author author)
    {
        if (author == null)
            return null;

        Integer authorId = author.getId();

        List<BookDto> books = author.getBooks()
                .stream().map(book -> toDto(book, authorId)
                ).toList();

        return new AuthorDto(
                authorId,
                author.getFirstName(),
                author.getLastName(),
                author.getPatronymic(),
                author.getDateOfBirth(),
                author.getDateOfDeath(),
                author.getBiography(),
                books
        );
    }

    public Author toEntity(AuthorDto authorDto)
    {
        Author author = new Author();

        author.setId(authorDto.id());
        author.setFirstName(authorDto.firstName());
        author.setLastName(authorDto.lastName());
        author.setPatronymic(authorDto.patronymic());
        author.setDateOfBirth(authorDto.dateOfBirth());
        author.setDateOfDeath(authorDto.dateOfDeath());
        author.setBiography(authorDto.biography());

        Set<Book> books = authorDto.books()
                .stream().map(bookDto -> toEntity(bookDto, author)
                ).collect(Collectors.toSet());

        author.setBooks(books);

        return author;
    }
}
