package net.martinprobson.example

import zio.*
import zio.interop.catz.*
import cats.effect.Resource
import doobie.{Transactor, ExecutionContexts}
import doobie.hikari.HikariTransactor
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

case class TransactorLive(config: ApplicationConfig):
  private def log: SelfAwareStructuredLogger[Task] = Slf4jLogger.getLogger[Task]

  private def transactor: ZIO[Scope, Throwable, Transactor[Task]] =
    (for
      _ <- Resource.eval[Task, Unit](log.info("Setting up transactor"))
      _ <- Resource.eval[Task, Unit](
        log.debug(s"DriverClassName = ${config.driverClassName}")
      )
      _ <- Resource.eval[Task, Unit](log.debug(s"url = ${config.url}"))
      _ <- Resource.eval[Task, Unit](log.debug(s"user = ${config.user}"))
      _ <- Resource.eval[Task, Unit](
        log.debug(s"password = ${config.password}")
      )
      ce <- ExecutionContexts.fixedThreadPool[Task](config.threads)
      xa <- HikariTransactor
        .newHikariTransactor[Task](
          config.driverClassName,
          config.url,
          config.user,
          config.password,
          ce
        )
    yield xa)
      .onFinalize(log.info("Finalize of transactor"))
      .toScopedZIO

end TransactorLive

object TransactorLive:
  val layer: ZLayer[ApplicationConfig, Throwable, Transactor[Task]] =
    ZLayer.scoped {
      ZIO
        .service[ApplicationConfig]
        .flatMap(cfg => TransactorLive(cfg).transactor)
    }
end TransactorLive
