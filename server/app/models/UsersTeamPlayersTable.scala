package models
// AUTO-GENERATED Slick data model for table UsersTeamPlayers
trait UsersTeamPlayersTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table UsersTeamPlayers
   *  @param userTeamName Database column user_team_name SqlType(varchar)
   *  @param playerId Database column player_id SqlType(int4) */
  case class UsersTeamPlayersRow(userTeamName: String, playerId: Int)
  /** GetResult implicit for fetching UsersTeamPlayersRow objects using plain SQL queries */
  implicit def GetResultUsersTeamPlayersRow(implicit e0: GR[String], e1: GR[Int]): GR[UsersTeamPlayersRow] = GR{
    prs => import prs._
    UsersTeamPlayersRow.tupled((<<[String], <<[Int]))
  }
  /** Table description of table users_team_players. Objects of this class serve as prototypes for rows in queries. */
  class UsersTeamPlayers(_tableTag: Tag) extends profile.api.Table[UsersTeamPlayersRow](_tableTag, "users_team_players") {
    def * = (userTeamName, playerId) <> (UsersTeamPlayersRow.tupled, UsersTeamPlayersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userTeamName), Rep.Some(playerId))).shaped.<>({r=>import r._; _1.map(_=> UsersTeamPlayersRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_team_name SqlType(varchar) */
    val userTeamName: Rep[String] = column[String]("user_team_name")
    /** Database column player_id SqlType(int4) */
    val playerId: Rep[Int] = column[Int]("player_id")

    /** Primary key of UsersTeamPlayers (database name user_team_player) */
    val pk = primaryKey("user_team_player", (userTeamName, playerId))

    /** Foreign key referencing Players (database name users_team_players_player_id_fkey) */
    lazy val playersFk = foreignKey("users_team_players_player_id_fkey", playerId, Players)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name users_team_players_user_team_name_fkey) */
    lazy val usersFk = foreignKey("users_team_players_user_team_name_fkey", userTeamName, Users)(r => r.userTeamName, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table UsersTeamPlayers */
  lazy val UsersTeamPlayers = new TableQuery(tag => new UsersTeamPlayers(tag))
}
