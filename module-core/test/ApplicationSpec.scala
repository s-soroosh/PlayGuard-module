import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.{Configuration, Application, GlobalSettings}
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.Play

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {




  val fakeApplicationWithGlobal = FakeApplication(additionalConfiguration = Map("playGuard.Repository" -> "{\"username\":\"soroosh\",\"password\":\"123456\"}"), withGlobal = Some(new GlobalSettings() {
    override def onStart(app: Application): Unit = {
      println("Application server started.\n");
      super.onStart(app)
    }


  }))

 /* "Application" should {

    "send 404 on a bad request" in new WithApplication(fakeApplicationWithGlobal) {
      println("hehe")
      private val jsConfig: JsValue = Json.parse(Play.application().configuration().getString("playGuard.Repository"))

      //      println(jsConfig)
      val a = Json.fromJson(jsConfig).ge
      println("Config: " + a)

    }

    /* "render the index page" in new WithApplication {
       val home = route(FakeRequest(GET, "/")).get

       status(home) must equalTo(OK)
       contentType(home) must beSome.which(_ == "text/html")
       contentAsString(home) must contain("Your new application is ready.")
     }*/
  }*/
}
