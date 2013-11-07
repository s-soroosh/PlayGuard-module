package controllers

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.{Application, GlobalSettings}
import com.github.playguard.action.SessionSecureAction
import com.github.playguard.exception.AuthenticationException
import play.api.cache._
import com.github.playguard.Principal


@RunWith(classOf[JUnitRunner])
object SessionAuthenticationSpec extends Specification {


  def fakeApplicationWithGlobal = {
    FakeApplication(additionalConfiguration = Map("playGuard.Repository" -> "{\"username\":\"soroosh\",\"password\":\"123456\"}"
      , "application.secret" -> "`nx0SCrpc65bN57VZ^=vjhY3hY^vBja_tLcxytIpBuu>9PkEiJHy?E9Jgfq1Zy4/"), withGlobal = Some(new GlobalSettings() {
      override def onStart(app: Application): Unit = {
        println("Application server started.\n");
        super.onStart(app)
      }

    }))
  }


  "login should set user session when username and password is true" in new WithApplication(fakeApplicationWithGlobal) {
    val result = com.github.playguard.controllers.SessionAuthentication.login(FakeRequest()
      .withFormUrlEncodedBody(("username", "soroosh"), ("password", "123456")))

    println(result)
    status(result) must equalTo(OK)
    contentType(result) must beSome("text/plain")
    session(result).get(SessionSecureAction.UID) must not beNone
  }

  "login should set user cache when username and password is true" in new WithServer(fakeApplicationWithGlobal) {
    val result = com.github.playguard.controllers.SessionAuthentication.login(FakeRequest()
      .withFormUrlEncodedBody(("username", "soroosh"), ("password", "123456")))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/plain")

    Cache.get("Principal-" + session(result).get(SessionSecureAction.UID).get).get must beAnInstanceOf[Principal]
    session(result).get(SessionSecureAction.UID) must not beNone
  }

  "login should not set user session when username or password is false" in new WithApplication(fakeApplicationWithGlobal) {
    val result = com.github.playguard.controllers.SessionAuthentication.login(FakeRequest()
      .withFormUrlEncodedBody(("username", "soroosh"), ("password", "12346"))) should throwA[AuthenticationException]

  }


}
