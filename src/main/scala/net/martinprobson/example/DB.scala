package net.martinprobson.example

import zio.{ZIO, Task}
import User.*

trait DB:
  def insertUser(user: User): Task[User]

  def createTable: Task[Int]

  def countUsers: Task[Long]

object DB:
  def insertUser(user: User): ZIO[DB, Throwable, User] =
    ZIO.serviceWithZIO[DB](_.insertUser(user))

  def createTable: ZIO[DB, Throwable, Int] =
    ZIO.serviceWithZIO[DB](_.createTable)

  def countUsers: ZIO[DB, Throwable, Long] =
    ZIO.serviceWithZIO[DB](_.countUsers)
