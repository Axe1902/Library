package com.example.library.model.dto;

public record BookDto (
        Integer id,
        String title,
        String description,
        Integer authorId
){}
