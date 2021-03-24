package db

import org.neo4j.driver.{AuthTokens, GraphDatabase, Result}

import scala.collection.mutable.ListBuffer

//import org.neo4j.driver.v1.{AuthTokens, GraphDatabase, StatementResult}

object DBFactory {
  case class User(name: String, last_name: String, age: Int, city: String)

  def insertRecord(user: User): Int = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"CREATE (user:Users {name:'${user.name}',last_name:'${user.last_name}',age:${user.age},city:'${user.city}'})"
    val result: Result = session.run(script)

    session.close()
    driver.close()
    result.consume().counters().nodesCreated()
  }

  def retrieveRecord(name: String) : (Boolean, Any)= {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"MATCH (a:Users) WHERE a.name = '$name' RETURN a.name AS name, a.last_name AS last_name, a.age AS age, a.city AS city"
    val result = session.run(script)
    val record_data = if (result.hasNext()) {
      val record = result.next()
      println(record.get("name").asString() + " " + record.get("last_name").asString() + " " + record.get("age").asInt() + " " + record.get("city").asString())
      (true, User(record.get("name").asString(), record.get("last_name").asString(), record.get("age").asInt(), record.get("city").asString()))
    }else{
      (false, s"$name not found.")
    }
    session.close()
    driver.close()
    record_data
  }

  def retrieveAllRecord(): (Boolean, Any) = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"MATCH (user:Users) RETURN user.name AS name, user.last_name AS last_name, user.age AS age, user.city AS city"
    val result: Result = session.run(script)
    val userList = ListBuffer[User]()
    val record_fetch = if (result.hasNext()) {
      val recordList = result.list()
      recordList.forEach{ record =>
        userList.addOne(User(record.get("name").asString(), record.get("last_name").asString(), record.get("age").asInt(), record.get("city").asString()))
        println("Record: " + record.get("name").asString() + " " + record.get("last_name").asString() + " " + record.get("age").asInt() + " " + record.get("city").asString())
    }
      userList.foreach{user =>
        println(user.name + " " + user.last_name + " " + user.age + " " + user.city)
      }
      (true, userList)
    }else{
      (false, "No data found")
    }
    session.close()
    driver.close()
    record_fetch
  }

  def updateRecord(name: String, newName: String): Boolean = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"MATCH (user:Users) where user.name ='$name' SET user.name = '$newName' RETURN user.name AS name, user.last_name AS last_name," +
      s" user.age AS age, user.city AS city"
    val result = session.run(script)
    session.close()
    driver.close()
    result.consume().counters().containsUpdates()
  }

  def deleteRecord(name: String): Int = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"MATCH (user:Users) where user.name ='$name' Delete user"
    val result = session.run(script)
    session.close()
    driver.close()
    result.consume().counters().nodesDeleted()
  }

  def createNodesWithRelation(user_name: String, userList: List[String], relation: String) = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val nameOfFriends = "\"" + userList.mkString("\", \"") + "\""
    val script = s"MATCH (user:Users {name: '$user_name'}) FOREACH (name in [$nameOfFriends] | CREATE (user)-[:$relation]->(:Users {name:name}))"
    val result = session.run(script)
    session.close()
    driver.close()
    result.consume().counters().relationshipsCreated()
  }

  def fetchFriendsOfFriends(user_name: String, relation: String): String = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session
    val script = s"MATCH (user:Users)-[:$relation]-(friend:Users)-[:$relation]-(foaf:Users) WHERE user.name = '$user_name' AND NOT (user)-[:$relation]-(foaf) RETURN foaf.name As name"
    val result = session.run(script)
    val name_of_friend_of_friend = if(result.hasNext()) {
      val record = result.next()
      record.get("name").asString()
    }else{
      "No friends found."
    }
    session.close()
    driver.close()
    name_of_friend_of_friend
  }
}
