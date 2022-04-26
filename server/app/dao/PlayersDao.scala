package dao

import models._

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}


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