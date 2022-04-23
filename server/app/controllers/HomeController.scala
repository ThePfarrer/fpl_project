package controllers


import javax.inject._
//import play.api._
import play.api.mvc._
import models.TaskListDatabaseModel

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                  val controllerComponents: ControllerComponents,
                                  model: TaskListDatabaseModel
                                )(implicit ec: ExecutionContext)
  extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  //  def load = Action { implicit request =>
  //    val usernameOption = request.session.get("username")
  //    usernameOption.map { username =>
  //      Ok(views.html.version2Main(routes.TaskList2.taskList().toString))
  //    }.getOrElse(Ok(views.html.version2Main(routes.TaskList2.login().toString)))
  //  }

  def login = Action.async { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      model.validateUser(username, password).map { ouserId =>
        ouserId match {
          case Some(_) => Redirect(routes.HomeController.playerList)
          case None => Ok(s"LOL! You aint slick $password")
        }
      }
    }.getOrElse(Future {
      Ok("No user found")
    })
  }

  def playerList = Action.async {implicit request =>
    model.getPlayers.map{ players =>
      Ok(views.html.playerList(players))
    }
  }
  //  def taskList = Action { implicit request =>
  //    val usernameOption = request.session.get("username")
  //    usernameOption.map { username =>
  //      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
  //    }.getOrElse(Ok(views.html.index()))
  //  }

  //  def validate = Action { implicit request =>
  //    val postVals = request.body.asFormUrlEncoded
  //    postVals.map { args =>
  //      val username = args("username").head
  //      val password = args("password").head
  //      if (TaskListInMemoryModel.validateUser(username, password)) {
  //        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
  //          .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
  //      } else {
  //        Ok(views.html.index())
  //      }
  //    }.getOrElse(Ok(views.html.index()))
  //  }

  def createUser = Action.async { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head

      model.createUser(username, password).map { ouserId =>
        ouserId match {
          case Some(_) => Ok(views.html.index())
          case None => Ok(views.html.index())
        }
      }
    }.getOrElse(Future{Ok(views.html.index())})
  }

//  def createUser = Action.async { implicit request =>
//
//    withJsonBody[UserData] { ud => model.createUser(ud.username, ud.password).map { ouserId =>
//      ouserId match {
//        case Some(userid) =>
//          Ok(Json.toJson(true))
//            .withSession("username" -> ud.username, "userid" -> userid.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.map(_.value).getOrElse(""))
//        case None =>
//          Ok(Json.toJson(false))
//      }
//    } }
//  }

//  def delete = Action { implicit request =>
//    val usernameOption = request.session.get("username")
//    usernameOption.map { username =>
//      val postVals = request.body.asFormUrlEncoded
//      postVals.map { args =>
//        val index = args("index").head.toInt
//        TaskListInMemoryModel.removeTask(username, index);
//        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
//      }.getOrElse(Ok(views.html.index()))
//    }.getOrElse(Ok(views.html.index()))
//  }

//  def addTask = Action { implicit request =>
//    val usernameOption = request.session.get("username")
//    usernameOption.map { username =>
//      val postVals = request.body.asFormUrlEncoded
//      postVals.map { args =>
//        val task = args("task").head
//        TaskListInMemoryModel.addTask(username, task);
//        Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
//      }.getOrElse(Ok(views.html.index()))
//    }.getOrElse(Ok(views.html.index()))
//  }

//  def logout = Action {
//    Redirect(routes.TaskList2.load()).withNewSession
//  }
}
