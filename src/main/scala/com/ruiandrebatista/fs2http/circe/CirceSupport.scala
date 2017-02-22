package com.ruiandrebatista.fs2http.circe

import spinoco.protocol.http.header.value.{ ContentType, MediaType, HttpCharset }

private[circe] object CirceSupport {
  val jsonContentType = ContentType(MediaType.`application/json`, Some(HttpCharset.`UTF-8`), None)
}
