package dao

import models._

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class UsersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  lazy val users = TableQuery[UsersTable]
  lazy val userTeam = TableQuery[UserTeamTable]
  lazy val userPlayers = TableQuery[UserTeamPlayersTable]

  def getUser(username: String): Future[Option[Users]] =
    db.run(users.filter(_.username === username).result.headOption)

  def signUp(user: Users): Future[_] = db.run(users += user)

  def createTeam(username: String, teamName: String ): Future[_] = {
    db.run(userTeam += UserTeam(username, teamName))
    db.run(users.filter(_.username === username).map(_.hasTeam).update(true))
  }

  def changeTeamName(username: String, teamName: String ): Future[_] = {
    db.run(userTeam.filter(_.username === username).map(_.teamName).update(teamName))
  }

  def getTeam(username: String): Future[Seq[UserTeamPlayers]] = {
    db.run(userPlayers.filter(_.username === username).result)
  }

  def addPlayer(userPlayer: UserTeamPlayers): Future[_] =
    db.run(userPlayers += userPlayer)

  //  def getUserTeam(user: Users): Future[Seq[Players]] =
//    db.run()

}

class PlayersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  lazy val players = TableQuery[PlayersTable]

  def getAllPlayers: Future[Seq[Players]] =
    db.run(players.result)

  def getPlayersByPosition(position: String): Future[Seq[Players]] =
    db.run(players.filter(_.position.toLowerCase === position.toLowerCase).result)

  def getPlayersByClub(club: String): Future[Seq[Players]] =
    db.run(players.filter(_.clubName.toLowerCase === club.toLowerCase).result)

  def getPlayersByClubAndPosition(club: String, position: String): Future[Seq[Players]] =
    db.run(players.filter { r => r.clubName.toLowerCase === club.toLowerCase && r.position.toLowerCase === position.toLowerCase }.result)

}