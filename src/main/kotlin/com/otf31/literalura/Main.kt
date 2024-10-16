package com.otf31.literalura

import com.otf31.literalura.model.*
import com.otf31.literalura.repository.BookRepository
import com.otf31.literalura.service.ClientAPI
import com.otf31.literalura.service.ConvertData
import java.net.URLEncoder

class Main(private val bookRepository: BookRepository) {
  private val clientAPI = ClientAPI()
  private val converter = ConvertData()
  fun showMenu() {
    println("Welcome to Literalura! \uD83D\uDC4B\uD83D\uDC4B\uD83D\uDC4B")

    var option = -1

    while (option != 0) {
      val menu = """
        1.- Find a book by title 
        2.- Show registered books
        3.- Show registered authors
        4.- List authors that were alive in a specific year
        5.- List books by language
 
        0.- Salir
      """.trimIndent()

      println(menu)

      try {
        print("Enter an option: ")
        option = readln().toInt()
      } catch (e: NumberFormatException) {
        println("\uD83D\uDE25 Invalid option")
        continue
      }

      when (option) {
          1 -> findBookByTitle()
          2 -> showRegisteredBooks()
          3 -> showRegisteredAuthors()
          4 -> listAuthorsAliveInYear()
          5 -> listBooksByLanguage()
        0 -> println("Terminating the application...")
        else -> println("\uD83D\uDE25 Invalid option")
      }
    }
  }

  private fun getBook(): BookData? {
    print("Enter the book title: ")
    val inputTitle = readln()

    val json = clientAPI.getData("/books/?search=${URLEncoder.encode(inputTitle, "UTF-8")}")
    val response = converter.getData(json, ResponseData::class)
    val results = response.results

    when {
      results.isEmpty() -> {
        println("No books found with the title: $inputTitle \n")

        return null
      }
      else -> return results[0]

    }
  }

  private fun findBookByTitle() {
    val data = getBook()

    if (data != null) {
      val book = Book(data)

      data.authors.forEach {
        val author = bookRepository.findPersonByName(it.name) ?: Person(it)

        book.authors.add(author)
      }

      data.translators.forEach {
        val translator = bookRepository.findPersonByName(it.name) ?: Person(it)

        book.translators.add(translator)
      }

      bookRepository.save(book)

      printBook(book)
    }
  }

  private fun showRegisteredBooks() {
    val books = bookRepository.findAll()

    when {
      books.isEmpty() -> {
        println("No books registered yet, try option 1 to register a book")
      }
      else -> books.forEach { printBook(it) }
    }
  }

  private fun showRegisteredAuthors() {
    val authors = bookRepository.findAuthors()
    println(authors)
    when {
      authors.isEmpty() -> {
        println("No authors registered yet, try option 1 to register a book")
      }
      else -> authors
        .forEach { printPerson(it) }
    }

  }

  private fun listAuthorsAliveInYear() {
    try {
      print("Enter the year: ")
      val year = readln().toInt()

      val authors = bookRepository.findAuthors()
        .filter { it.birthYear != null && it.deathYear != null }
        .filter { it.birthYear!! <= year && it.deathYear!! >= year }

      when {
        authors.isEmpty() -> {
          println("No authors were alive in the year $year")
        }
        else -> authors.forEach { printPerson(it) }
      }
    } catch (e: NumberFormatException) {
      println("\uD83D\uDE25 Invalid year")
    }
  }

  private fun listBooksByLanguage() {
    print("Enter the language: ")
    val language = readln()

    val books = bookRepository.findAll()
      .filter { it.languages.contains(language) }

    when {
      books.isEmpty() -> {
        println("No books found in the language $language")
      }
      else -> books.forEach { printBook(it) }
    }
  }

  private fun printBook(book: Book) {
    println("\n******************")
    println("""
      Title: ${book.title}
      Author: ${when {
        book.authors.isEmpty() -> "Unknown"
        else -> book.authors.first().name
      }}
      Languages: ${book.languages.joinToString(", ")}
      Download count: ${book.downloadCount}
    """.trimIndent())
    println("******************\n")
  }

  private fun printPerson(person: Person) {
    println("\n******************")
    println("""
      Name: ${person.name}
      Birth year: ${person.birthYear}
      Death year: ${person.deathYear}
    """.trimIndent())
    println("******************\n")
  }
}