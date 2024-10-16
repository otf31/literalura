package com.otf31.literalura.model

@JvmRecord
data class ResponseData(
  val count: Int,
  val next: String?,
  val previous: String?,
  val results: List<BookData>
)