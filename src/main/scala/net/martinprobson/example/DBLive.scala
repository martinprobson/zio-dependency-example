package net.martinprobson.example

import zio.{Task, ULayer, ZIO, ZLayer}
import zio.interop.catz.*
import doobie.*
import doobie.free.connection.ConnectionOp
import doobie.implicits.*

case class DBLive(xa: Transactor[Task]) extends DB:

  override def insertUser(user: User): Task[User] = (for {
    _ <-
      sql"INSERT INTO user (name, email) VALUES (${user.name.toString},${user.email.toString})".update.run
    id <- sql"SELECT last_insert_id()".query[Long].unique
    user <- doobie.free.connection.pure(
      User(id, user.name, user.email)
    )
  } yield user).transact(xa)

  override def createTable: Task[Int] = (for {
    result <- sql"""
             |create table if not exists user
             |(
             |    id   int auto_increment
             |        primary key,
             |    name varchar(100) null,
             |    email varchar(100) null
             |);
             |""".stripMargin.update.run
  } yield result).transact(xa)

  override def countUsers: Task[Long] =
    sql"SELECT COUNT(*) FROM user".query[Long].unique.transact(xa)

end DBLive

object DBLive:
  val layer: ZLayer[Transactor[Task], Throwable, DB] =
    ZLayer {
      for xa <- ZIO.service[Transactor[Task]]
      yield DBLive(xa)
    }
