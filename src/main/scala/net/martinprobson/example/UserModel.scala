package net.martinprobson.example

import zio.{Task, ZIO}

trait UserModel:
  def insert(user: User): Task[User]

object UserModel:
  def insert(user: User): ZIO[UserModel, Throwable, User] =
    ZIO.serviceWithZIO[UserModel](_.insert(user))
