package com.github.playguard.action

import play.api.mvc.{Request, Results, SimpleResult}
import com.github.playguard.controllers.SessionAuthentication

trait AuthenticateFailedStrategy {
  def failed[A](request: Request[A]): SimpleResult
}


object DefaultAuthenticateStrategy extends AuthenticateFailedStrategy {
  def failed[A](request: Request[A]): SimpleResult = Results.Ok(views.html.login(SessionAuthentication.loginForm.bind(Map("returnUrl" -> request.path))))
}
