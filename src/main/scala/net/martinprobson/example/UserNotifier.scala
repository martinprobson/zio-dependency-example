package net.martinprobson.example

import zio.{ZIO, Task}
import User.*

trait UserNotifier:
  def notify(user: User, msg: String): Task[String]

object UserNotifier:
  def notify(user: User, msg: String): ZIO[UserNotifier, Throwable, String] =
    ZIO.serviceWithZIO[UserNotifier](_.notify(user, msg))
