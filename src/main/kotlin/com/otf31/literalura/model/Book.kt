package com.otf31.literalura.model

import jakarta.persistence.*

@Entity
@Table(name = "books")
class Book(bookData: BookData) {
  @Id
  var id: Int = 0
  var title: String = ""

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "book_subjects", joinColumns = [JoinColumn(name = "book_id")])
  var subjects = listOf<String>()

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "book_bookshelves", joinColumns = [JoinColumn(name = "book_id")])
  var bookshelves = listOf<String>()

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "book_languages", joinColumns = [JoinColumn(name = "book_id")])
  var languages = listOf<String>()

  var copyright: Boolean? = false
  var mediaType: String = ""

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "book_formats", joinColumns = [JoinColumn(name = "book_id")])
  var formats = mapOf<String, String>()

  var downloadCount: Int = 0

  @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinTable(name = "book_authors", joinColumns = [JoinColumn(name = "book_id")], inverseJoinColumns = [JoinColumn(name = "author_id")])
  var authors = mutableSetOf<Person>()
    set(value) {
      value.forEach { it.booksAsAuthor.add(this) }
      field = value
    }

  @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinTable(name = "book_translators", joinColumns = [JoinColumn(name = "book_id")], inverseJoinColumns = [JoinColumn(name = "translator_id")])
  var translators = mutableSetOf<Person>()
    set(value) {
      value.forEach { it.booksAsTranslator.add(this) }
      field = value
    }

  init {
    id = bookData.id
    title = bookData.title
    subjects = bookData.subjects
    bookshelves = bookData.bookshelves
    languages = bookData.languages
    copyright = bookData.copyright
    mediaType = bookData.mediaType
    formats = bookData.formats
    downloadCount = bookData.downloadCount
  }

  override fun toString(): String {
    return "id=$id, title='$title', languages=$languages, copyright=$copyright, mediaType='$mediaType', downloadCount=$downloadCount, authors=$authors, translators=$translators"
  }
}