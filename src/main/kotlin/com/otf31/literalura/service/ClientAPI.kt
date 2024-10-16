package com.otf31.literalura.service

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

class ClientAPI {
  private val client = RestClient.builder().baseUrl("https://gutendex.com").build()

  fun getData(url: String): String? {
    try {
      val bodyResponse = client.get().uri(url).retrieve().body<String>()

      return bodyResponse

    } catch (e: HttpClientErrorException) {
      println("Something went wrong")
      throw RuntimeException(e)
    }
  }
}

// {
//  "count": <number>,
//  "next": <string or null>,
//  "previous": <string or null>,
//  "results": <array of Books>
//}