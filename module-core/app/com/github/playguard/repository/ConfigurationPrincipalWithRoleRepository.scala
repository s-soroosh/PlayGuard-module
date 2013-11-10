package com.github.playguard.repository

import com.github.playguard.model.{PrincipalWithRole, Principal, Role}
import org.slf4j
import org.slf4j.LoggerFactory
import play.Play
import scala.collection.JavaConversions._

case class ConfigurationPrincipalWithRole(name: String, password: String, enable: Boolean, roles: List[Role]) extends PrincipalWithRole

object ConfigurationPrincipalWithRoleRepository extends PrincipalRepository {
  private val logger: slf4j.Logger = LoggerFactory.getLogger(ConfigurationPrincipalRepository.getClass)
  private lazy val principals: List[PrincipalWithRole] = {
    logger.info("Loading conf based principals")
    Play.application.configuration.getConfigList("playGuard.Repository").toList.map(c => {
      ConfigurationPrincipalWithRole(c.getString("username"), c.getString("password"), true,
        c.getStringList("roles").map(r=> Role(r)).toList
      )
    })
  }


  def findPrincipal(username: String): Option[Principal] = principals.find(p => p.name == username)
}
