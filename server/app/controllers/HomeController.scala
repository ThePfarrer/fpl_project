package controllers

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import play.api.mvc._
import play.api.Logging
import models._
import dao.UsersDao

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import pdi.jwt.{JwtAlgorithm, JwtJson}

import scala.util.Random


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, userDao: UsersDao) extends AbstractController(cc) with Logging {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  lazy val key = "secretKey"
  lazy val algo: JwtAlgorithm = JwtAlgorithm.HS256
  lazy val claim = s"""{"user":"user-${Random.nextInt(100)}"}"""
  lazy val token: String = JwtJson.encode(claim, key, algo)

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
    val postVals = request.body.asJson.get.toString()

    decode[Users](postVals) match {
      case Right(value) =>
        val futureInsert = userDao.signUp(value)
        futureInsert.map(_ => Redirect(routes.HomeController.index()).withCookies(Cookie("t", token))).recover(recoverError)

      case Left(_) => Future.successful(BadRequest)
    }
  }
  
  def logout: Action[AnyContent] = Action {
    Redirect(routes.HomeController.index()).discardingCookies(DiscardingCookie("t"))
  }

//  def getPlayers: Action[AnyContent] = Action { request =>
//    userDao
//  }
}
