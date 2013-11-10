package encoding

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._


import play.api.{Application, GlobalSettings}
import play.api.test.{WithApplication, FakeApplication}
import com.github.playguard.encoding.{SHA1Encoder, MD5Encoder, PlainTextEncoder}

@RunWith(classOf[JUnitRunner])
class EncoderSpec extends Specification {

  "Plain Text Encoder" should {
    "return original text" in {
      PlainTextEncoder.encode("Text") === "Text"
    }
  }

  "MD5 Encoder" should {
    "return encoded with MD5" in {
      "9dffbf69ffba8bc38bc4e01abf4b1675" === MD5Encoder.encode("Text")
    }
  }


  "SHA1 Encoder" should {
    "return encoded with SHA1" in {
      "c3328c39b0e29f78e9ff45db674248b1d245887d" === SHA1Encoder.encode("Text")
    }
  }


}
