package com.github.playguard.repository

import com.github.playguard.Principal
import org.slf4j.LoggerFactory
import org.slf4j
import play.api.libs.json._
import play.Play

case class ConfigurationPrincipal(name: String, password: String, enable: Boolean) extends Principal {
}

object ConfigurationPrincipalRepository extends PrincipalRepository {

  implicit object PrincipalFormat extends Format[Principal] {

    def reads(json: JsValue): JsResult[Principal] = {
      JsSuccess(ConfigurationPrincipal(
        (json \ "username").as[String]
        ,
        (json \ "password").as[String], true
      )
      )
    }

    def writes(user: Principal): JsValue = JsObject(Seq(
      "username" -> JsString(user.name),
      "password" -> JsString(user.password)
    ))

  }

  private val logger: slf4j.Logger = LoggerFactory.getLogger(ConfigurationPrincipalRepository.getClass)
  private lazy val principals: List[Principal] = {
    logger.info("Loading conf based principals")
    val jsConfig: JsValue = Json.parse(Play.application().configuration().getString("playGuard.Repository"))
    val result: List[Principal] = jsConfig.as[List[Principal]]
    result
  }

  def findPrincipal(username: String): Option[Principal] = principals.find(p => p.name == username)

}
