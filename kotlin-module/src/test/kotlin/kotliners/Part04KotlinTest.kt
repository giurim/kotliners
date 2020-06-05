package kotliners

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull


class HasNullableDouble(
    val v: Double?
)

class Part04KotlinTest : Spek({
    val json = ObjectMapper()

    describe("a null when deserialized") {
        it("can be a zero") {
            val v: Double = json.readValue("null", Double::class.java)
            assertEquals(0.0, v)
        }

        it("cant be parsed if we set the type trough typereference") {
            assertThrows<IllegalStateException> {
                val v: Double = json.readValue("null", jacksonTypeRef<Double>())
            }
        }

        it("can be parsed to a null, depite T is non-null, but the return null-check fails") {
            // T must be non-nullable according to readValue-s type parameter, but it is not "enforced"
            // ERROR: readValue(content, jacksonTypeRef<T>()) must not be null
            assertThrows<IllegalStateException> {
                val v: Double? = json.readValue("null")
            }
        }

        it("can be a java.lang.Double null") {
            val v = json.readValue("null", java.lang.Double::class.java)
            // it is null even if the compiler thinks the value is not nullable
            assertNull(v)
        }


        it("can not be a kotlin Double? null") {
            //            compile error:
//            Kotlin: Type in class literal must not be nullable
//
//            val v = json.readValue("null", Double?::class.java)
//            assertNull(v)
        }

        it("can not be be a kotlin Double? null even if we try it hard") {
            //            compile error:
//            Kotlin: Type parameter bound for T in val <T : Any> T.javaClass: Class<T>
//            is not satisfied: inferred type Double? is not a subtype of Any
//            val  dNull:Double? = null
//            val v = json.readValue("null", dNull.javaClass)
//            assertNull(v)
        }

        it("can be a null if we manually construct a TypeReference") {
            val v: Double? = json.readValue("null", object : TypeReference<Double?>() {})
            assertNull(v)
        }

        it("is serialized to null by default, if no type info is given") {
            val v = json.readValue("[0.2,null]", List::class.java)
            assertNull(v[1])
        }

        it("can be a kotlin Double? null if it is wrapped") {
            val jsonString = """{"v":null}"""
            val v = json.registerKotlinModule().readValue(jsonString, HasNullableDouble::class.java)
            assertNull(v.v)
        }

        it("can be an element of an Array<Double>") {
            val jsonString = """[null]"""
            val v = json.readValue(jsonString, Array<Double>::class.java)
            assertNull(v[0])
            assertFailsWith<java.lang.NullPointerException> {
                val d: Double = v[0]
            }
        }

        it("be a scala.Double null") {
            val jsonString = """null"""
            val v = json.readValue(jsonString, scala.Double::class.java)
            assertNull(v)
        }
    }
})

