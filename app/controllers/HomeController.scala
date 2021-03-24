package controllers

import akka.util.ByteString

import javax.inject._
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._
import play.libs.Json

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def explore(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }
  
  def tutorial(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }

  def test(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
    )
//    Status(http.Status.FORBIDDEN)
//    Forbidden("Access Denied")
//    println("request: " + request.headers.toMap.get("Accept"))
//    Ok("{\"name\": \"Anil\"}")
  }

  def user(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    var user = new User(id)
    user.name = "Anil"
    user.email = "anil@gmail.com"
    user.setAge(35)
    println("name: " + user.name)
    println("setName: " + user.setName(user.name + " Gupta"))
    println("age: " + user.getAge)
    Ok(Json.toJson(user).toString)
  }

  def list(version: Option[String]): Action[AnyContent] = Action { implicit  request: Request[AnyContent] =>
    Ok("version: " + version)
  }

  def hello(name: String): Action[AnyContent] = Action {
    Ok("Hello " + name + "!")
  }

  // Redirect to /hello/Bob
  def helloBob: Action[AnyContent] = Action {
    Redirect(routes.HomeController.hello("Bob"))
  }

  class User(id: Long) {
    var userId: Long = id
    var name: String = ""
    var email: String = ""
    private var age: Int = 0

    def setName(name: String): Unit = {
      this.name = name
    }

    def setAge(age: Int): Unit = {
      this.age = age
    }

    def getAge: Int = {
      age
    }
  }
  
}
