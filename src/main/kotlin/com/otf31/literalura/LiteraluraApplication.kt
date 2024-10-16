package com.otf31.literalura

import com.otf31.literalura.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LiteraluraApplication @Autowired constructor(private val bookRepository: BookRepository): CommandLineRunner {
	override fun run(vararg args: String?) {
		val main = Main(bookRepository)

		main.showMenu()
	}
}

fun main(args: Array<String>) {
	runApplication<LiteraluraApplication>(*args)
}
