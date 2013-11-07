import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._


import play.api.{Application, GlobalSettings}
import play.api.test.{WithApplication, FakeApplication}

@RunWith(classOf[JUnitRunner])
class ConfigurationPrincipalRepositorySpec extends Specification {

  val fakeApplicationWithGlobal = FakeApplication(additionalConfiguration = Map("playGuard.Repository" -> "[{\"username\":\"sajad\",\"password\":\"123\"}, {\"username\":\"soroosh\",\"password\":\"123456\"}]"), withGlobal = Some(new GlobalSettings() {
    override def onStart(app: Application): Unit = {
      println("Application server started.\n");
      super.onStart(app)
    }
  }))

  "Configuration Principal Repository" should {

    "find principal" in new WithApplication(fakeApplicationWithGlobal) {
      val p = com.github.playguard.repository.ConfigurationPrincipalRepository.findPrincipal("soroosh")
      p.get.password === "123456"


    }

    "return None when principal does not exist" in new WithApplication(fakeApplicationWithGlobal) {
      val p = com.github.playguard.repository.ConfigurationPrincipalRepository.findPrincipal("mehran")
      p === None
    }


  }
}