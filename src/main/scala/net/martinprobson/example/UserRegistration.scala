package net.martinprobson.example

import zio.{ZIO, Task}
import User.*

trait UserRegistration:
  def register(user: User): Task[(User, String)]

object UserRegistration:
  def register(user: User): ZIO[UserRegistration, Throwable, (User, String)] =
    ZIO.serviceWithZIO[UserRegistration](_.register(user))
