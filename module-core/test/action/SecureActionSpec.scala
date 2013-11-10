package action

import play.api.{Application, GlobalSettings}
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.mvc._
import com.github.playguard.action.SessionSecureAction
import java.util.UUID
import play.api.cache._
import com.github.playguard.controllers.SessionAuthentication
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success
import ExecutionContext.Implicits.global
import com.github.playguard.model.Principal


@RunWith(classOf[JUnitRunner])
object SecureActionSpec extends Specification {
  val msg: String = "Everything is OK just for authenticated users"

  object TestController extends Controller {
    def sec = SessionSecureAction {
      Ok(msg)
    }
  }

  def fakeApplicationWithGlobal = {
    FakeApplication(additionalConfiguration = Map("playGuard.Repository" -> "{\"username\":\"soroosh\",\"password\":\"123456\"}"
      , "application.secret" -> "`nx0SCrpc65bN57VZ^=vjhY3hY^vBja_tLcxytIpBuu>9PkEiJHy?E9Jgfq1Zy4/"), withGlobal = Some(new GlobalSettings() {
      override def onStart(app: Application): Unit = {
        println("Application server started.\n");
        super.onStart(app)
      }
    }))
  }

  "action should return forbidden when user session is empty" in new WithApplication(fakeApplicationWithGlobal) {
    private val result: Future[SimpleResult] = TestController.sec(FakeRequest())
    status(result) must equalTo(OK)
    result onComplete {
      case Success(r) => r.toString() === Results.Ok(views.html.login(SessionAuthentication.loginForm)).toString()

    }

  }

  "action should call action block when user is authenticated successfully" in new WithApplication(fakeApplicationWithGlobal) {
    val id = UUID.randomUUID().toString
    Cache.set("Principal-" + id, new Principal() {
      def name: String = "soroosh"

      def password: String = "123456"

      def enable: Boolean = true
    })
    val result = TestController.sec(FakeRequest().withSession((SessionSecureAction.UID, id)))

    status(result) must equalTo(OK)
    contentAsString(result) must contain(msg)


  }

  "Login view should contains returlUrl" in new WithApplication(fakeApplicationWithGlobal) {
    val form = SessionAuthentication.loginForm.bind(Map("returnUrl" -> "haha"))
    val html = views.html.login(form)

    contentAsString(html) must contain("haha")



  }


}
