package com.github.playguard.action

import play.api.mvc._
import scala.concurrent.Future
import play.api.cache.Cache
import play.api.Play.current
import com.github.playguard.{SecurityRegistry}
import com.github.playguard.exception.AuthenticationException
import com.github.playguard.controllers.SessionAuthentication
import com.github.playguard.model.{Principal, AuthenticatedRequest}


object SessionSecureAction extends ActionBuilder[AuthenticatedRequest] {
  val UID: String = "uid";

  def failedStrategy[A](request: Request[A]): SimpleResult = SecurityRegistry.authenticateFailedStrategy.failed(request)


  protected def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[SimpleResult]): Future[SimpleResult] = {

    request.session.get(UID).map(id => Cache.get("Principal-" + id).map(u => {
      u match {
        case p: Principal => {
          block(new AuthenticatedRequest(request, p))
        }
        case p: Any => {
          throw new IllegalStateException(String.format("Inconsistency in principal cache. value type must be:Principal but is:%s", p.getClass))
        }
      }
    }
    ).getOrElse(Future.successful(failedStrategy(request)))
    ).getOrElse(Future.successful(failedStrategy(request)))
  }
}
