package dao

import models._

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class UsersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  lazy val users = TableQuery[UsersTable]

  def getUser(username: String): Future[Option[Users]] =
    db.run(users.filter(_.username === username).result.headOption)

  def signUp(user: Users): Future[_] = db.run(users += user)

}

class PlayersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  lazy val players = TableQuery[PlayersTable]

//  def getPlayers: Future[Seq[Players]]

}