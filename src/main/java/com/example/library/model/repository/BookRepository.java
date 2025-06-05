package com.example.library.model.repository;

import com.example.library.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>
{
    Page<Book> findAllByOrderByTitle(Pageable pageable);
    Page<Book> findAllByOrderByTitleDesc(Pageable pageable);
    List<Book> findByAuthorId(Integer authorId);
    List<Book> findTop10ByOrderByIdDesc();

    @Query("SELECT b FROM Book b WHERE b.title ILIKE %:substring%")
    List<Book> findByTitleILike(@Param("substring") String substring);
}
