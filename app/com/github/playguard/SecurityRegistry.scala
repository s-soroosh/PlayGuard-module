package com.github.playguard

import play.api.mvc._
import com.github.playguard.repository.{DefaultPrincipalRepository, PrincipalRepository}
import com.github.playguard.authenticator.{SessionAuthenticator, Authenticator}

object SecurityRegistry {

  private var _principalRepository: PrincipalRepository = DefaultPrincipalRepository
  private var _forbidden: SimpleResult = play.api.mvc.Results.Forbidden("You can't access.")
  var _authenticator: Authenticator = SessionAuthenticator
  var _defaultRedirectResult: SimpleResult = Results.Ok("You logined successfully.")

  def principalRepository: PrincipalRepository = _principalRepository

  def principalRepository_=(principalRepository: PrincipalRepository): Unit = _principalRepository = principalRepository

  def forbidden: SimpleResult = _forbidden

  def forbidden_=(result: SimpleResult): Unit = _forbidden = result

  def authenticator: Authenticator = _authenticator

  def authenticator_=(auth: Authenticator): Unit = _authenticator = auth

  def defaultRedirectResult = _defaultRedirectResult

  def defaultRedirectResult_=(result: SimpleResult): Unit = _defaultRedirectResult = result


}
