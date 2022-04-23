package models
// AUTO-GENERATED Slick data model for table Clubs
trait ClubsTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Clubs
   *  @param id Database column id SqlType(serial), AutoInc
   *  @param clubName Database column club_name SqlType(varchar), PrimaryKey */
  case class ClubsRow(id: Int, clubName: String)
  /** GetResult implicit for fetching ClubsRow objects using plain SQL queries */
  implicit def GetResultClubsRow(implicit e0: GR[Int], e1: GR[String]): GR[ClubsRow] = GR{
    prs => import prs._
    ClubsRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table clubs. Objects of this class serve as prototypes for rows in queries. */
  class Clubs(_tableTag: Tag) extends profile.api.Table[ClubsRow](_tableTag, "clubs") {
    def * = (id, clubName) <> (ClubsRow.tupled, ClubsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(clubName))).shaped.<>({r=>import r._; _1.map(_=> ClubsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column club_name SqlType(varchar), PrimaryKey */
    val clubName: Rep[String] = column[String]("club_name", O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Clubs */
  lazy val Clubs = new TableQuery(tag => new Clubs(tag))
}
