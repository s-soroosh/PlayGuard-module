package encoding

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._


import play.api.{Application, GlobalSettings}
import play.api.test.{WithApplication, FakeApplication}
import com.github.playguard.encoding.PlainTextEncoder

@RunWith(classOf[JUnitRunner])
class PlanTextEncoderSpec extends Specification {

  "Encoder" should {
    "return original text" in {
         PlainTextEncoder.encode("Text") === "Text"
    }
  }

}
