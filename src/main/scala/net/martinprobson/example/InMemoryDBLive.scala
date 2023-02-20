package net.martinprobson.example

import zio.{Ref, Task, ZLayer, ULayer, ZIO}
import scala.collection.immutable.SortedMap
import User.*

case class InMemoryDBLive(
    db: Ref[SortedMap[USER_ID, User]],
    counter: Ref[USER_ID]
) extends DB:

  override def insertUser(user: User): Task[User] = for {
    id <- counter.modify(x => (x + 1, x + 1))
    _ <- ZIO.logInfo(s"About to create User: $user")
    _ <- db.update(users => users.updated(key = id, value = user))
    u <- ZIO.succeed(User(id, user.name, user.email))
    _ <- ZIO.logInfo(s"Created User: $u")
  } yield u

  override def createTable: Task[Int] = ZIO.succeed(0)

  override def countUsers: Task[Long] =
    db.get.flatMap(users => ZIO.succeed(users.size.toLong))

end InMemoryDBLive

object InMemoryDBLive:
  val layer: ULayer[DB] =
    ZLayer {
      for
        counter <- Ref.make(UNASSIGNED_USER_ID)
        db <- Ref.make(SortedMap.empty[USER_ID, User])
      yield InMemoryDBLive(db, counter)
    }
end InMemoryDBLive
