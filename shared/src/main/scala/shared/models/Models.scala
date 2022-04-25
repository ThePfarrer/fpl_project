package models

case class Users(username: String, password: String, hasTeam: Boolean = false)
case class Players(playerName: String, clubName: String, position: String)
case class Clubs(id: Int, clubName: String)
case class UserTeam(username: String, teamName: String)
case class UserTeamPlayers(username: String, teamName: String, playerName:String)
