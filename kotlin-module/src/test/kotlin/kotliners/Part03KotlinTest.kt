package kotliners

import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.ByteArrayOutputStream
import java.io.NotSerializableException
import java.io.ObjectOutputStream

class Part03KotlinTest : Spek({
    fun serialize(obj: Any): ByteArray {
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(obj)
        oos.close()
        return bos.toByteArray()
    }

    describe("Java/Spark serialization") {
        it("cannot serialize a sublist") {
            assertThrows<NotSerializableException> {
                serialize(listOf("a", "b", "c").subList(1, 1))
            }
        }

        it("cannot serialize a reversed list") {
            assertThrows<NotSerializableException> {
                serialize(listOf("a", "b", "c").asReversed())
            }
        }

        it("cannot serialize a list created from a primitive array with `asList`") {
            // kotlin.collections.ArraysKt___ArraysJvmKt$asList$3
            assertThrows<NotSerializableException> {
                serialize(IntArray(1) { 1 }.asList())
            }
        }

        it("can serialize a list created from a non-primitive array with `asList`") {
            serialize(arrayOf("a", "b").asList())
        }

        it("can serialize a list created from a primitive array with `toList`") {
            serialize(IntArray(1) { 1 }.toList())
        }

        it("can serialize a list reversed with another method") {
            serialize(listOf("a", "b", "c").reversed())
        }

    }
})

