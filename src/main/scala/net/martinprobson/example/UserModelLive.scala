package net.martinprobson.example

import zio.{Task, ZIO, ZLayer}

case class UserModelLive(db: DB) extends UserModel:

  override def insert(user: User): Task[User] = db.insertUser(user)

object UserModelLive:
  val layer: ZLayer[DB, Throwable, UserModel] =
    ZLayer {
      for {
        db <- ZIO.service[DB]
      } yield UserModelLive(db)
    }
