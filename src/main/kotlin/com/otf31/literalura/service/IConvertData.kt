package com.otf31.literalura.service

import kotlin.reflect.KClass

interface IConvertData {
  fun <T: Any> getData(json: String?, clase: KClass<T>): T
}