package kotliners

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class Part04ScalaTest extends AnyFunSpec with Matchers{
  describe("scala.Double") {
    it("can not be null (but it can be in Kotlin)"){
      // Error:(9, 22) an expression of type Null is ineligible for implicit conversion
      // val d:Double = null
      val d:Double = null.asInstanceOf[Double]
      d shouldBe 0
    }
  }
}


