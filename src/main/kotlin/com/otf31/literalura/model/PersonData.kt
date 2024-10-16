package com.otf31.literalura.model

import com.fasterxml.jackson.annotation.JsonAlias

@JvmRecord
data class PersonData(
  @JsonAlias("birth_year") val birthYear: Int?,
  @JsonAlias("death_year") val deathYear: Int?,
  val name: String
)