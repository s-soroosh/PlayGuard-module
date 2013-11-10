package com.github.playguard.model

import play.api.mvc.{Request, WrappedRequest}
import com.github.playguard.model.Principal

class AuthenticatedRequest[A](req: Request[A], principal: Principal) extends WrappedRequest(req)
