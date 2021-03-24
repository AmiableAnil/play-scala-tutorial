package controllers

import db.DBFactory
import db.DBFactory.User
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.libs.Json

import javax.inject.{Inject, Singleton}

@Singleton
class Neo4JController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listAllRecord(): Action[AnyContent] = Action {
    val (isDataAvailable, data) = DBFactory.retrieveAllRecord()
    println("isDataAvailable = " + isDataAvailable)
    println("Data = " + data)

    Ok(Json.toJson(data).toString)
  }

  def insertRecord(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val requestBody = request.body.asJson
    val requestObject = Json.parse(requestBody.get("request").toString())
    println(DBFactory.insertRecord(User(name =requestObject.get("name").asText(),
      last_name = requestObject.get("lastName").asText(),
      age = requestObject.get("age").asInt(),
      city = requestObject.get("city").asText())))
//    println(DBFactory.insertRecord(User(name ="Nitin", last_name = "Puri", 36, "Delhi")))
//    println(DBFactory.insertRecord(User(name ="Jayaprakash", last_name = "N", 32, "Bengaluru")))
    Ok("Inserted " + "!")
  }

  def updateRecord(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val requestBody = request.body.asJson
    val requestObject = Json.parse(requestBody.get("request").toString())
    DBFactory.updateRecord(requestObject.get("name").asText(), requestObject.get("newName").asText())
    Ok("Record updated.")
  }

  def deleteRecord(name: String): Action[AnyContent] = Action {
    DBFactory.deleteRecord(name)
    Ok("Record Deleted")
  }

  def getRecord(name: String): Action[AnyContent] = Action {
    val (found, user) = DBFactory.retrieveRecord(name)
    if(found) {
      Ok(Json.toJson(user).toString)
    } else {
      Ok(user.toString)
    }
  }
}
