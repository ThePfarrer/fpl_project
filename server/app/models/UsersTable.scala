package models
// AUTO-GENERATED Slick data model for table Users
trait UsersTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param userName Database column user_name SqlType(varchar)
   *  @param userEmail Database column user_email SqlType(varchar)
   *  @param userPassword Database column user_password SqlType(bpchar), Length(255,false)
   *  @param userTeamName Database column user_team_name SqlType(bpchar), Length(20,false) */
  case class UsersRow(id: Int, userName: String, userEmail: String, userPassword: String, userTeamName: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, userName, userEmail, userPassword, userTeamName) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(userName), Rep.Some(userEmail), Rep.Some(userPassword), Rep.Some(userTeamName))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_name SqlType(varchar) */
    val userName: Rep[String] = column[String]("user_name")
    /** Database column user_email SqlType(varchar) */
    val userEmail: Rep[String] = column[String]("user_email")
    /** Database column user_password SqlType(bpchar), Length(255,false) */
    val userPassword: Rep[String] = column[String]("user_password", O.Length(255,varying=false))
    /** Database column user_team_name SqlType(bpchar), Length(20,false) */
    val userTeamName: Rep[String] = column[String]("user_team_name", O.Length(20,varying=false))

    /** Uniqueness Index over (userEmail) (database name users_user_email_key) */
    val index1 = index("users_user_email_key", userEmail, unique=true)
    /** Uniqueness Index over (userTeamName) (database name users_user_team_name_key) */
    val index2 = index("users_user_team_name_key", userTeamName, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
