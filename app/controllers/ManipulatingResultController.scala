package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.{Inject, Singleton}

/**
 * https://www.playframework.com/documentation/2.8.x/ScalaResults
 */
@Singleton
class ManipulatingResultController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def contentType(contentType: String): Action[AnyContent] = Action {
    var result = Ok("")

    contentType match {
      case "xml" =>
        //        result = Ok(<message>Hello World!</message>).as("application/xml")
        result = Ok(<message>Hello World!</message>).as(XML)
      case "html" =>
        //        result = Ok(<h1>Hello World!</h1>).as("text/html")
        result = Ok(<h1>Hello World!</h1>).as(HTML)
      case "text" => {
//        result = Ok("Hello World!").as("text/plain")
        result = Ok("Plan text").as(TEXT)
      }
      case "json" => {
        result = Ok("{\"key\": \"value\"}").as(JSON)
      }
      case default =>
        result = Ok("You gave me: " + default + "\nUse /xml, /html, /text or /json")
    }

    result
  }

  def headers(): Action[AnyContent] = Action {
    var result = Ok("Headers")
    result = Ok("Hello World!").withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xx", CONTENT_TYPE -> JSON)

    result
  }

}
