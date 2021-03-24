package controllers

import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc.{AbstractController, ControllerComponents, Request, ResponseHeader, Result}

import javax.inject.{Inject, Singleton}

/**
 * https://www.playframework.com/documentation/2.8.x/ScalaActions
 */
@Singleton
class ActionController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

//  def echo = Action {
//    Ok("Hello world")
//  }

//  def echo = Action { request =>
//    Ok("Got request [" + request + "]")
//  }

  // It is often useful to mark the request parameter as implicit so it can be implicitly used by other APIs that need it:
  def echo = Action { implicit request =>
    anotherMethod("Some para value")
    Ok("Got request [" + request + "]")
  }

  def anotherMethod(p: String)(implicit request: Request[_]) = {
    // do something that needs access to the request
    println("anotherMethod: " + request)
  }

//  def echo = Action(parse.json) { implicit request =>
//    Ok("Got request [" + request + "]")
//  }

  // ==== Simple Result ====
//  def index = Action {
//    Ok("Hello world!")

  // Here are several examples to create various results:
//  val ok           = Ok("Hello world!")
//  val notFound     = NotFound
//  val pageNotFound = NotFound(<h1>Page not found</h1>)
//  val badRequest   = BadRequest(views.html.form(formWithErrors))
//  val oops         = InternalServerError("Oops")
//  val anyStatus    = Status(488)("Strange response type")
  // All of these helpers can be found in the play.api.mvc.Results trait and companion object.
//  }

//  def index = Action {
//    Result(
//      header = ResponseHeader(200, Map.empty),
//      body = HttpEntity.Strict(ByteString("Hello world!!!!!"), Some("text/plain"))
//    )
//  }

  // ==== Redirect ====
//  def index = Action {
//    Redirect("/api/list")
//  }

//  def index = Action {
//    Redirect("/api/list", MOVED_PERMANENTLY)
//  }

  // ==== Dummy page====
  def index() = TODO
}
