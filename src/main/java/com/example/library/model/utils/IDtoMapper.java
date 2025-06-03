package com.example.library.model.utils;

import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Author;
import com.example.library.model.entity.Book;

public interface IDtoMapper
{
    BookDto toDto(Book book);
    BookDto toDto(Book book, Integer authorId);
    Book toEntity(BookDto bookDto, Author author);
    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);
}
