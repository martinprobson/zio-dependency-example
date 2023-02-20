package net.martinprobson.example

import zio.{ZIO, Task}
import User.*

trait EmailService:
  def send(email: User.Email, msg: String): Task[Unit]

object EmailService:
  def send(email: User.Email, msg: String): ZIO[EmailService, Throwable, Unit] =
    ZIO.serviceWithZIO[EmailService](_.send(email, msg))
