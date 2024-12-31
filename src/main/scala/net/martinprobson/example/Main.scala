package net.martinprobson.example

import doobie.Transactor
import zio.{ZIO, Task, ZLayer}

import User.*

object Main extends ZIOApplication:

  private def program: ZIO[DB & UserRegistration, Throwable, Unit] = for
    _ <- ZIO.logDebug("About to create table")
    result <- DB.createTable
    _ <- ZIO.logDebug(s"create table result = $result")
    _ <- {
      val users = Range(1, 20000).inclusive.toList
        .map { i => User(UserName(s"User-$i"), Email(s"email-$i")) }
      ZIO.foreachPar(users)(user => UserRegistration.register(user))
    }
    total <- DB.countUsers
    _ <- ZIO.logInfo(s"Complete total of $total registered users")
  yield ()

  override def run: Task[Unit] = Db

  /** ZLayers necessary to run the code with an InMemory (Map based) DB.
    */
  val InMemory: Task[Unit] = program.provide(
    UserNotifierLive.layer,
    InMemoryDBLive.layer,
    UserModelLive.layer,
    UserRegistrationLive.layer,
    EmailServiceLive.layer
    // ZLayer.Debug.tree
  )

  /** ZLayers necessary to run the code with a JDBC database (defined in
    * ApplicationConfig)
    */
  val Db: Task[Unit] = program.provide(
    UserNotifierLive.layer,
    ApplicationConfig.layer,
    TransactorLive.layer,
    DBLive.layer,
    UserModelLive.layer,
    UserRegistrationLive.layer,
    EmailServiceLive.layer
    // ZLayer.Debug.tree
  )

end Main
