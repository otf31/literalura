package com.otf31.literalura.model

import com.fasterxml.jackson.annotation.JsonAlias

@JvmRecord
data class BookData(
  val id: Int,
  val title: String,
  val subjects: List<String>,
  val authors: List<PersonData>,
  val translators: List<PersonData>,
  val bookshelves: List<String>,
  val languages: List<String>,
  val copyright: Boolean?,
  @JsonAlias("media_type") val mediaType: String,
  val formats: Map<String, String>,
  @JsonAlias("download_count") val downloadCount: Int
)