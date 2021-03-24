package controllers

import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RestApiController @Inject() (ws: WSClient, ec: ExecutionContext, cc: ControllerComponents) extends AbstractController(cc) {

  def getApi() = Action {
//    val request: WSRequest = ws.url("https://www.google.com")
//    val futureResponse: Future[WSResponse] = request.get()
//    val futureResult: Future[String] = futureResponse.map { response =>
//      (response.json \ "person" \ "name").as[String]
//    }
//    Ok(futureResult.toString)
    Ok
  }
}
