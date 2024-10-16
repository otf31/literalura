package com.otf31.literalura.model

import jakarta.persistence.*

@Entity
@Table(name = "persons")
class Person(person: PersonData) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L

  var birthYear: Int? = null
  var deathYear: Int? = null
  var name: String = ""

  @ManyToMany(mappedBy = "authors", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  var booksAsAuthor =  mutableSetOf<Book>()

  @ManyToMany(mappedBy = "translators", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  var booksAsTranslator = mutableSetOf<Book>()

  init {
    this.birthYear = person.birthYear
    deathYear = person.deathYear
    name = person.name
  }
}