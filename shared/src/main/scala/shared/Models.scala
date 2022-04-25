package models

case class Users(username: String, password: String)
case class Players(playerName: String, clubName: String, position: String)
case class Clubs(id: Int, clubName: String)
case class UserTeam(username: String, teamName: String)
