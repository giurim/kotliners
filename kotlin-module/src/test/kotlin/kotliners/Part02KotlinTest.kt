package kotliners

import org.junit.Assert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import scala.Function1
import scala.runtime.AbstractFunction1


class Part02KotlinTest : Spek({
    val needsLambda = NeedsLambda()
    describe("Calling a scala lambda from Kotlin") {
        it("works with inline lambda") {
            val result = needsLambda.callTheLambda({ s: String -> s.trim() }, "  text")
            Assert.assertEquals("text", result)
        }

        it("works with lambda variable") {
            // note that type of function is inferred
            val f = Function1 { s: String -> s.trim() }
            val result = needsLambda.callTheLambda(f, "  text")
            Assert.assertEquals("text", result)
        }

        it("works with method reference") {
            val result = needsLambda.callTheLambda(String::trim, "  text")
            Assert.assertEquals("text", result)
        }

        it("works in Scala 2.11 too") {
            val f = object : AbstractFunction1<String, String>() {
                override fun apply(v1: String?) = v1?.trim()
            }
            val result = needsLambda.callTheLambda(f, "  text")
            Assert.assertEquals("text", result)
        }

        it("works in Scala 2.11 too with extensions") {
            // Kotlinify
            fun NeedsLambda.callTheLambdaKoltin(f: (String) -> String, s: String): String {
                val f1 = object : AbstractFunction1<String, String>() {
                    override fun apply(v1: String) = f(v1)
                }
                return this.callTheLambda(f1, s)
            }

            val result = needsLambda.callTheLambdaKoltin({ s: String -> s.trim() }, "  text")
            Assert.assertEquals("text", result)
        }


    }
})

