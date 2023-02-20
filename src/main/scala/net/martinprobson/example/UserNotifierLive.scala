package net.martinprobson.example

import zio.{Task, ULayer, ZIO, ZLayer}

case class UserNotifierLive(emailService: EmailService) extends UserNotifier:
  override def notify(user: User, msg: String): Task[String] =
    val res = s"Sending $msg to ${user.email}"
    emailService.send(user.email, msg) *>
      ZIO.logInfo(res) *> ZIO.succeed(res)

end UserNotifierLive

object UserNotifierLive:
  val layer: ZLayer[
    EmailService,
    Throwable,
    UserNotifierLive
  ] =
    ZLayer {
      for {
        emailService <- ZIO.service[EmailService]
      } yield UserNotifierLive(emailService)
    }
end UserNotifierLive
