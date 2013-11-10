package repository


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
import com.typesafe.config.ConfigFactory
import java.io.File
import com.github.playguard.repository.ConfigurationPrincipalWithRole
import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class ConfigurationPrincipalWithRoleRepositorySpec extends Specification {
  def fakeApplicationWithGlobal = {
    val conf: Map[String, AnyRef] = new play.Configuration(ConfigFactory.parseFile(new File("conf/application.conf"))).asMap().toMap
    FakeApplication(additionalConfiguration = conf, withGlobal = Some(new GlobalSettings() {
      override def onStart(app: Application): Unit = {
        super.onStart(app)
      }
    }))
  }

  "find principal" in new WithApplication(fakeApplicationWithGlobal) {
    val p = com.github.playguard.repository.ConfigurationPrincipalWithRoleRepository.findPrincipal("soroosh")

    p.map(a => a match {
      case r: ConfigurationPrincipalWithRole => {
        r.password must beEqualTo("123456")
        r.roles.size must beEqualTo(1)
      }
      case o => throw new Error
    })
  }

  "return None when principal does not exist" in new WithApplication(fakeApplicationWithGlobal) {
    val p = com.github.playguard.repository.ConfigurationPrincipalWithRoleRepository.findPrincipal("mehran")
    p must beEqualTo(None)
  }
}
