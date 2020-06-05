package kotliners

import clojure.java.api.Clojure
import com.fasterxml.jackson.databind.ObjectMapper
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import scala.Tuple2
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class Part05KotlinTest : Spek({
    describe("Clojure maps") {
        val cljMap = Clojure.read("""{:key1 "value", :key2 { :key3 123 }}""") as Map<*, *>

        val clj2kotlinMap = cljMap.map { it.key to it.value }.toMap()

        it("looks ok if I print") {
            assertEquals("{:key1=value, :key2={:key3 123}}", clj2kotlinMap.toString())
        }
        it("looks ok if I serialize") {
            assertEquals("""{":key1":"value",":key2":{":key3":123}}""", ObjectMapper().writeValueAsString(clj2kotlinMap))
        }

        it("does not contain the key \":key1\"") {
            assertFalse(clj2kotlinMap.containsKey(":key1"))
        }

        it("has non-string keys") {
            assertTrue(clj2kotlinMap.keys.first() is clojure.lang.Keyword)
        }
    }

    val kotlinMap = mapOf("a" to 1, "b" to 2)
    val scalaMap = scala.collection.JavaConverters.mapAsScalaMap(kotlinMap)

    val javaMap = java.util.HashMap<String, Int>().apply {
        this.putAll(kotlinMap)
    }

    describe("Scala Map") {

        it("magically have the usual functionality") {
            val mappedMap = scalaMap.map { e -> Tuple2(e._1, e._2 + 1) }
            assertEquals(2, mappedMap.get("a").get())
        }

        it("it cannot be serializable with jackson without the kotlin module") {
            assertEquals("""{"empty":false,"traversableAgain":true}""",
                ObjectMapper().writeValueAsString(scalaMap))
        }
    }

    describe("Java Map") {

        it("is just a Kotlin Map") {
            val mappedMap = javaMap.map { e -> e.key to e.value + 1 }.toMap()
            assertEquals(2, mappedMap["a"])
        }
    }
})

