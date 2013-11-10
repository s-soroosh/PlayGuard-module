package com.github.playguard.model

import play.api.mvc.Request

case class AuthorizedRequest[A](val req: Request[A], val principal: PrincipalWithRole) extends AuthenticatedRequest(req, principal) {

}

