package models
// AUTO-GENERATED Slick data model for table Players
trait PlayersTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Players
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param playerName Database column player_name SqlType(varchar)
   *  @param clubName Database column club_name SqlType(varchar)
   *  @param position Database column position SqlType(varchar) */
  case class PlayersRow(id: Int, playerName: String, clubName: String, position: String)
  /** GetResult implicit for fetching PlayersRow objects using plain SQL queries */
  implicit def GetResultPlayersRow(implicit e0: GR[Int], e1: GR[String]): GR[PlayersRow] = GR{
    prs => import prs._
    PlayersRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table players. Objects of this class serve as prototypes for rows in queries. */
  class Players(_tableTag: Tag) extends profile.api.Table[PlayersRow](_tableTag, "players") {
    def * = (id, playerName, clubName, position) <> (PlayersRow.tupled, PlayersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(playerName), Rep.Some(clubName), Rep.Some(position))).shaped.<>({r=>import r._; _1.map(_=> PlayersRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column player_name SqlType(varchar) */
    val playerName: Rep[String] = column[String]("player_name")
    /** Database column club_name SqlType(varchar) */
    val clubName: Rep[String] = column[String]("club_name")
    /** Database column position SqlType(varchar) */
    val position: Rep[String] = column[String]("position")

    /** Foreign key referencing Clubs (database name players_club_name_fkey) */
    lazy val clubsFk = foreignKey("players_club_name_fkey", clubName, Clubs)(r => r.clubName, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Players */
  lazy val Players = new TableQuery(tag => new Players(tag))
}
