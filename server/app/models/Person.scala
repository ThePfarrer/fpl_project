package models

import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Tables._

import scala.concurrent.{ExecutionContext, Future}
class TaskListDatabaseModel @Inject() (
                                        protected val dbConfigProvider: DatabaseConfigProvider
                                      )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def createUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Users.filter(userRow => userRow.userName === username).result)
    matches.flatMap { userRows =>
      if (userRows.isEmpty) {
        db.run(Users += UsersRow(-1, username, userEmail = "test1@test.de", password, userTeamName = "Testert1"))
          .flatMap { addCount =>
            if (addCount > 0) db.run(Users.filter(userRow => userRow.userName === username).result)
              .map(_.headOption.map(_.id))
            else Future.successful(None)
          }
      } else Future.successful(None)
    }
  }

  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Users.filter(userRow => userRow.userName === username).result)
    matches.map(userRows => userRows.headOption.flatMap {
      userRow => if (password == userRow.userPassword.trim) Some(userRow.id) else None
    })
  }

  def getPlayers: Future[Seq[PlayersRow]] = {
    db.run(Players.result)
      .map(items =>
        items.map(item =>
          PlayersRow(item.id, item.playerName, item.clubName, item.position)
        )
      )
  }

//  def createTeam(userid: Int, task: String): Future[Int] = {
//    db.run(Items += ItemsRow(-1, userid, task))
//  }
//
//  def createLeague(userid: Int, task: String): Future[Int] = {
//    db.run(Items += ItemsRow(-1, userid, task))
//  }

//  def addPlayer(userid: Int, task: String): Future[Int] = {
//    db.run(Items += ItemsRow(-1, userid, task))
//  }
//
//  def removePlayer(itemId: Int): Future[Boolean] = {
//    db.run( Items.filter(_.itemId === itemId).delete ).map(count => count > 0)
//  }
}