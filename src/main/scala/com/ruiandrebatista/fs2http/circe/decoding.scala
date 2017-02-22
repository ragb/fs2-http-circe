package com.ruiandrebatista.fs2http.circe

import cats.syntax.either._
import java.nio.charset.{ Charset, StandardCharsets }
import scodec.{ Attempt, Decoder, DecodeResult, Err }
import scodec.bits.BitVector
import io.circe.{ Decoder => CirceDecoder }
import io.circe.parser.{ decode => decodeJson }

import spinoco.fs2.http.body.BodyDecoder

trait DecoderInstances {
  implicit def decoderFromCirceDecoder[T: CirceDecoder](implicit cs: Charset): Decoder[T] = Decoder { bv =>
    Attempt.fromEither[DecodeResult[T]] {
      bv.decodeString(cs).bimap(exc => Err(exc.getMessage), DecodeResult(_, BitVector.empty))
        .flatMap(r => decodeJson[T](r.value).bimap(circeError => Err(circeError.toString), DecodeResult(_, r.remainder)))
    }
  }

  implicit def bodyDecoderFromCirceDecoder[T](implicit d: CirceDecoder[T]) = BodyDecoder.forDecoder { ct =>
    if (ct.mediaType.subType.toLowerCase != "json") {
      Attempt.failure(Err(s"Require a json content type, found $ct"))
    } else {
      val charset = ct.charset.map(_.value).flatMap(name => Option(Charset.forName(name)))
        .getOrElse(StandardCharsets.UTF_8) // assume utf-8 by default
      Attempt.successful(decoderFromCirceDecoder(d, charset))
    }
  }
}
