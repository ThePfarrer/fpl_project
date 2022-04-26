package controllers

import io.circe.parser._
import io.circe.syntax._
import play.api.mvc._
import play.api.Logging
import models._
import dao.{PlayersDao, UsersDao}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import pdi.jwt.{JwtAlgorithm, JwtJson}
import play.api.libs.circe.Circe

import scala.util.Random


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, userDao: UsersDao, playerDao: PlayersDao) extends AbstractController(cc) with Logging with Circe {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  import io.circe.generic.extras.auto._
  import io.circe.generic.extras.Configuration

  implicit val customConfig: Configuration = Configuration.default.withDefaults


  val recoverError: PartialFunction[Throwable, Result] = {
    case e: Throwable =>
      logger.error("Error while writing in the database", e)
      InternalServerError("Cannot write in the database")
  }

  def index(): Action[AnyContent] = Action { request: Request[AnyContent] =>
    val userOption = request.cookies.get("t")
    userOption match {
      case Some(_) => Ok(views.html.index())
      case None => Redirect(routes.HomeController.login)
    }
  }

  def login: Action[AnyContent] = Action.async { request =>
    lazy val token: String = JwtJson.encode(s"""{"user":"user-${Random.nextInt(100)}"}""", "secretKey", JwtAlgorithm.HS256)

    val postVals = request.body.asJson.get.toString()

    decode[Users](postVals) match {
      case Right(value) =>
        userDao.getUser(value.username).map {
          case None => BadRequest
          case Some(user) if user.password == value.password =>
            Redirect(routes.HomeController.index()).withCookies(Cookie("t", token))
          case Some(_) => Unauthorized
        }

      case Left(_) => Future.successful(BadRequest)
    }

  }

  def signUp: Action[AnyContent] = Action.async { request =>
    lazy val token: String = JwtJson.encode(s"""{"user":"user-${Random.nextInt(100)}"}""", "secretKey", JwtAlgorithm.HS256)
    val postVals = request.body.asJson.get.toString()

    decode[Users](postVals) match {
      case Right(value) =>
        val futureInsert = userDao.signUp(value)
        futureInsert.map(_ => Redirect(routes.HomeController.index()).withCookies(Cookie("t", token))).recover(recoverError)

      case Left(err) => Future.successful(BadRequest)
    }
  }

  def logout: Action[AnyContent] = Action {
    Redirect(routes.HomeController.index()).discardingCookies(DiscardingCookie("t"))
  }

  def createTeam: Action[AnyContent] = Action.async { request =>
    val postVals = request.body.asJson.get.toString()

    decode[UserTeam](postVals) match {
      case Right(value) =>
        val futureInsert = userDao.createTeam(value.username, value.teamName)
        futureInsert.map(_ => Ok)

      case Left(_) => Future.successful(BadRequest)
    }
  }

  def changeTeamName: Action[AnyContent] = Action.async { request =>
    val postVals = request.body.asJson.get.toString()

    decode[UserTeam](postVals) match {
      case Right(value) =>
        val futureInsert = userDao.changeTeamName(value.username, value.teamName)
        futureInsert.map(_ => Ok)

      case Left(_) => Future.successful(BadRequest)
    }
  }

  def addPlayerToTeam: Action[AnyContent] = Action.async { request =>
    val postVals = request.body.asJson.get.toString()

    decode[UserTeamPlayers](postVals) match {
      case Right(value) =>
        val futureInsert = userDao.addPlayer(value)
        futureInsert.map(_ => Ok)

      case Left(_) => Future.successful(BadRequest)
    }
  }

  def getPlayers: Action[AnyContent] = Action.async {
    playerDao.getAllPlayers.map { res => Ok(res.asJson) }
  }

  def getPlayersByPosition(position: String): Action[AnyContent] = Action.async {
    playerDao.getPlayersByPosition(position).map { res => Ok(res.asJson) }
  }

  def getPlayersByClub(clubName: String): Action[AnyContent] = Action.async {
    playerDao.getPlayersByClub(clubName).map { res => Ok(res.asJson) }
  }

  def getPlayersByClubAndPosition(clubName: String, position: String): Action[AnyContent] = Action.async {
    playerDao.getPlayersByClubAndPosition(clubName, position).map { res => Ok(res.asJson) }
  }

  def getUserTeam(userName: String): Action[AnyContent] = Action.async {
    userDao.getTeam(userName).map { res => Ok(res.asJson) }
  }

  def deletePlayerFromTeam(username: String, playerName: String): Action[AnyContent] = Action.async {
    userDao.deletePlayer(username, playerName).map(_ => Ok)
  }
}
