package net.martinprobson.example

import zio.{Task, ZIO, ZLayer}

case class UserRegistrationLive(
    userModel: UserModel,
    userNotifier: UserNotifier
) extends UserRegistration:

  override def register(user: User): Task[(User, String)] = for {
    user <- userModel.insert(user)
    _ <- ZIO.logDebug(s"Insert $user")
    msg <- userNotifier.notify(user, "Welcome!")
    _ <- ZIO.logDebug(s"Sent $msg to $user")
  } yield (user, msg)

object UserRegistrationLive:
  val layer: ZLayer[
    UserModel with UserNotifier,
    Throwable,
    UserRegistration
  ] =
    ZLayer {
      for
        userModel <- ZIO.service[UserModel]
        userNotifier <- ZIO.service[UserNotifier]
      yield UserRegistrationLive(userModel, userNotifier)
    }
