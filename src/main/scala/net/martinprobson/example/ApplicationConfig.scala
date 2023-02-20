package net.martinprobson.example

import zio.*
import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

final case class ApplicationConfig(
                         threads: Int,
                         driverClassName: String,
                         url: String,
                         user: String,
                         password: String
                       )

object ApplicationConfig:
    val layer: ZLayer[Any, ReadError[String], ApplicationConfig] = ZLayer {
        read {
            descriptor[ApplicationConfig].from(TypesafeConfigSource.fromResourcePath.at(PropertyTreePath.$("ApplicationConfig")))
        }
    }
