package com.example.library.model.dto;

import java.time.LocalDate;
import java.util.List;

public record AuthorDto(
        Integer id,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath,
        String biography,
        List<BookDto> books
) { }
