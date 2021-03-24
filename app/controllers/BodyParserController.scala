package controllers

import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}

/**
 * https://www.playframework.com/documentation/2.8.x/ScalaBodyParsers
 */
@Singleton
class BodyParserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Default body parser
//  def save = Action { request: Request[AnyContent] =>
//    val body: AnyContent          = request.body
//    val jsonBody: Option[JsValue] = body.asJson
//
//    // Expecting json body
//    jsonBody
//      .map { json =>
//        Ok("Got: " + (json \ "name").as[String])
//      }
//      .getOrElse {
//        BadRequest("Expecting application/json request body")
//      }
//  }

  def save = Action(parse.json) { request: Request[JsValue] =>
    Ok("Got: " + (request.body \ "name").as[String])
  }

  /**
   * Here is another example, which will store the request body in a file.
   */
  //  def save = Action(parse.file(to = new File("/tmp/upload"))) { request: Request[File] =>
//    Ok("Saved the request content to " + request.body)
//  }

//  val storeInUserFile = parse.using { request =>
//    request.session
//      .get("username")
//      .map { user =>
//        parse.file(to = new File("/tmp/" + user + ".upload"))
//      }
//      .getOrElse {
//        sys.error("You don't have the right to upload here")
//      }
//  }

  /**
   * Combining body parsers
   */
//  def save = Action(storeInUserFile) { request =>
//    Ok("Saved the request content to " + request.body)
//  }
}
