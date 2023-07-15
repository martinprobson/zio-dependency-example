package net.martinprobson.example

import zio.*
import zio.config.*
import zio.config.magnolia.deriveConfig
import zio.config.typesafe.TypesafeConfigProvider

final case class ApplicationConfig(
                         threads: Int,
                         driverClassName: String,
                         url: String,
                         user: String,
                         password: String
                       )


object ApplicationConfig:
      val layer: ZLayer[Any, Config.Error, ApplicationConfig] = ZLayer {
        TypesafeConfigProvider.fromResourcePath.load(deriveConfig[ApplicationConfig].nested("ApplicationConfig"))
      }
