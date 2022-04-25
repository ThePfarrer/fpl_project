package models

import slick.jdbc.PostgresProfile.api._
import slick.model.ForeignKeyAction


/** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
class UsersTable(tag: Tag) extends Table[Users](tag, "users") {
  def * = (username, password) <> (Users.tupled, Users.unapply)

  /** Database column username SqlType(varchar), PrimaryKey */
  val username: Rep[String] = column[String]("username", O.PrimaryKey)

  /** Database column password SqlType(varchar) */
  val password: Rep[String] = column[String]("password")
}

/** Table description of table players. Objects of this class serve as prototypes for rows in queries. */
class PlayersTable(tag: Tag) extends Table[Players](tag, "players") {
  lazy val clubs = TableQuery[ClubsTable]

  def * = (playerName, clubName, position) <> (Players.tupled, Players.unapply)

  /** Database column player_name SqlType(varchar) */
  val playerName: Rep[String] = column[String]("player_name")
  /** Database column club_name SqlType(varchar) */
  val clubName: Rep[String] = column[String]("club_name")
  /** Database column position SqlType(varchar) */
  val position: Rep[String] = column[String]("position")

  /** Foreign key referencing Clubs (database name players_club_name_fkey) */
  lazy val clubsFk = foreignKey("players_club_name_fkey", clubName, clubs)(r => r.clubName, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
}

/** Table description of table clubs. Objects of this class serve as prototypes for rows in queries. */
class ClubsTable(tag: Tag) extends Table[Clubs](tag, "clubs") {
  def * = (id, clubName) <> (Clubs.tupled, Clubs.unapply)

  /** Database column id SqlType(serial), AutoInc */
  val id: Rep[Int] = column[Int]("id", O.AutoInc)
  /** Database column club_name SqlType(varchar), PrimaryKey */
  val clubName: Rep[String] = column[String]("club_name", O.PrimaryKey)

}

/** Table description of table user_team. Objects of this class serve as prototypes for rows in queries. */
class UserTeamTable(tag: Tag) extends Table[UserTeam](tag, "user_team") {
  lazy val users = TableQuery[UsersTable]

  def * = (username, teamName) <> (UserTeam.tupled, UserTeam.unapply)

  /** Database column username SqlType(varchar) */
  val username: Rep[String] = column[String]("username")
  /** Database column team_name SqlType(varchar) */
  val teamName: Rep[String] = column[String]("team_name")

  /** Foreign key referencing Users (database name user_team_username_fkey) */
  lazy val usersFk = foreignKey("user_team_username_fkey", username, users)(r => r.username, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

  /** Uniqueness Index over (username,teamName) (database name user_team_username_team_name_key) */
  val index1 = index("user_team_username_team_name_key", (username, teamName), unique=true)
}

