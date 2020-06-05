package kotliners

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import shadowed.com.fasterxml.jackson.databind.ObjectMapper
// ERROR cannot import
import shadowed.com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule as rkm

class Part06KotlinTest : Spek({
    describe("Kotlin extension function") {
        val json = shadowed.com.fasterxml.jackson.databind.ObjectMapper()
        it("cannot be used from shaded lib") {
            val json = com.fasterxml.jackson.databind.ObjectMapper()
            json.rkm()
        }

        // ERROR: Unresolved reference: registerKotlinModule
        it("cannot be used from shaded lib as extension function") {
            val json = shadowed.com.fasterxml.jackson.databind.ObjectMapper()
            json.registerKotlinModule()
        }

        // ERROR:  Unresolved reference: ExtensionsKt
        it("cannot be used from shaded lib as class") {
            val json = shadowed.com.fasterxml.jackson.databind.ObjectMapper()
            shadowed.com.fasterxml.jackson.module.kotlin.ExtensionsKt.registerKotlinModule(json)
        }
    }
})
