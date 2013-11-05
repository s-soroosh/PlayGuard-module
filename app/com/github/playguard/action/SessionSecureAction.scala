package com.github.playguard.action

import play.api.mvc._
import scala.concurrent.Future
import play.api.cache.Cache
import play.api.Play.current
import com.github.playguard.{Principal, SecurityRegistry, AuthenticatedRequest}
import com.github.playguard.exception.AuthenticationException
import com.github.playguard.controllers.SessionAuthentication


object SessionSecureAction extends ActionBuilder[AuthenticatedRequest] {
  val UID: String = "uid";


  protected def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[SimpleResult]): Future[SimpleResult] = {
    try {
      println(request.path)
      request.session.get(UID).map(id => Cache.get("Principal-" + id).map(u => {
        u match {
          case p: Principal => {
            block(AuthenticatedRequest(request, p))
          }
          case p: Any => {
            throw new IllegalStateException(String.format("Inconsistency in principal cache. value type must be:Principal but is:%s", p.getClass))
          }
        }
      }
      ).getOrElse(throw new IllegalStateException("Principal does not exist in cache"))
      ).getOrElse(throw new AuthenticationException("uid does not exist in user session"))
    }
    catch {
      case e: IllegalStateException => Future.successful(Results.Ok(views.html.login(SessionAuthentication.loginForm.bind(Map("returnUrl" -> request.path)))))
      case e: AuthenticationException => Future.successful(Results.Ok(views.html.login(SessionAuthentication.loginForm.bind(Map("returnUrl" -> request.path)))))
    }
  }

}
