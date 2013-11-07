package com.github.playguard.controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.cache._
import com.github.playguard.{Principal, SecurityRegistry}
import com.github.playguard.action.SessionSecureAction
import java.util.UUID
import play.api.Play.current
import views.html.login

object SessionAuthentication extends Controller {

  case class Login(username: String, password: String, returnUrl: Option[String])

  val loginForm = Form(
    mapping("username" -> nonEmptyText, "password" -> nonEmptyText, "returnUrl" -> optional(text))(Login.apply)(Login.unapply)
  )

  def showPage = Action {
    Ok(views.html.login(loginForm))
  }

  def login = Action {
    implicit request =>
      loginForm.bindFromRequest.fold(
        f => {
          BadRequest(views.html.login(f))
        },
        s => {
          println(s)
          val principal: Principal = SecurityRegistry.authenticator.authenticate(Map("username" -> s.username, "password" -> s.password))
          val userId: String = UUID.randomUUID().toString
          Cache.set("Principal-" + userId, principal)
          s.returnUrl.map(url =>
            Results.Redirect(url).withSession(session + (SessionSecureAction.UID -> userId))
          ).getOrElse(
            SecurityRegistry.defaultRedirectResult.withSession(session + (SessionSecureAction.UID -> userId)))
        }

      )
  }


}


