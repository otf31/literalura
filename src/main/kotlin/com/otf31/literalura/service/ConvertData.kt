package com.otf31.literalura.service

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass

class ConvertData: IConvertData {
  private val objectMapper = ObjectMapper()

  override fun <T : Any> getData(json: String?, clase: KClass<T>): T {
    return objectMapper.readValue(json, clase.java)
  }
}