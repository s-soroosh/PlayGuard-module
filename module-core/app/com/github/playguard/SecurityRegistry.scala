package com.github.playguard

import play.api.mvc._
import com.github.playguard.repository.{DefaultPrincipalRepository, PrincipalRepository}
import com.github.playguard.authenticator.{SessionAuthenticator, Authenticator}
import com.github.playguard.encoding.{PlainTextEncoder, Encoder}
import com.github.playguard.action.{AuthenticateFailedStrategy, DefaultAuthenticateStrategy}

object SecurityRegistry {

  private var _principalRepository: PrincipalRepository = DefaultPrincipalRepository
  private var _forbidden: SimpleResult = play.api.mvc.Results.Forbidden("You can't access.")
  private var _authenticator: Authenticator = SessionAuthenticator
  private var _defaultRedirectResult: SimpleResult = Results.Ok("You logined successfully.")
  private var _encoder: Encoder = PlainTextEncoder
  private var _authenticateFailedStrategy:AuthenticateFailedStrategy = DefaultAuthenticateStrategy

  def principalRepository: PrincipalRepository = _principalRepository
  def forbidden: SimpleResult = _forbidden
  def encoder = _encoder
  def defaultRedirectResult = _defaultRedirectResult
  def authenticator: Authenticator = _authenticator
  def authenticateFailedStrategy = _authenticateFailedStrategy

  def authenticator_=(auth: Authenticator): Unit = _authenticator = auth
  def forbidden_=(result: SimpleResult): Unit = _forbidden = result
  def defaultRedirectResult_=(result: SimpleResult): Unit = _defaultRedirectResult = result
  def principalRepository_=(principalRepository: PrincipalRepository): Unit = _principalRepository = principalRepository
  def encoder_=(coder:Encoder)= _encoder = coder
  def authenticateFailedStrategy_=(strategy:AuthenticateFailedStrategy)= _authenticateFailedStrategy = strategy


}
