package com.otf31.literalura.repository

import com.otf31.literalura.model.Book
import com.otf31.literalura.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository: JpaRepository<Book, Int> {
  @Query("SELECT DISTINCT p FROM Person p JOIN p.booksAsAuthor")
  fun findAuthors(): List<Person>

  @Query("SELECT p FROM Person p WHERE p.name = :name")
  fun findPersonByName(name: String): Person?
}