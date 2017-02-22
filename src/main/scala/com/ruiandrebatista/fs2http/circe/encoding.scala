package com.ruiandrebatista.fs2http.circe

import scodec.Encoder
import io.circe.{ Encoder => CirceEncoder }

import io.circe.syntax._
import spinoco.fs2.http.body.BodyEncoder

private[circe] trait EncoderInstances {
  import CirceSupport.jsonContentType

  implicit def encoderFromCirceEncoder[T: CirceEncoder]: Encoder[T] = Encoder[String].contramap(_.asJson.noSpaces)

  implicit def bodyEncoderFromCirceEncoder[T: CirceEncoder]: BodyEncoder[T] = BodyEncoder.forEncoder(jsonContentType)(encoderFromCirceEncoder[T])
}

