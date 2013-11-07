package com.github.playguard

import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A](req: Request[A], principal: Principal) extends WrappedRequest(req) {

}
