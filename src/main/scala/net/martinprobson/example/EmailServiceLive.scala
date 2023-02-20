package net.martinprobson.example

import zio.{Task, ULayer, ZIO, ZLayer}

case object EmailServiceLive extends EmailService:
  override def send(email: User.Email, msg: String): Task[Unit] =
    ZIO.logInfo(s"Sending $msg to ${email}")

  val layer: ULayer[EmailService] = ZLayer.succeed(EmailServiceLive)

end EmailServiceLive
