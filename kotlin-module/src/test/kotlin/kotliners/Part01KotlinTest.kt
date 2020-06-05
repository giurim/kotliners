package kotliners

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import scala.jdk.CollectionConverters

fun HasVararg.callTheClassicVararg(vararg args:String): String? {
    return this.callTheClassicVararg(
        CollectionConverters.ListHasAsScala(
            args.toList()
        ).asScala().toSeq()
    )
}

fun HasVararg.callTheClassicVarargPlus(vararg args:String): String? {
    if (args.isEmpty()){
        throw IllegalArgumentException("There should be at least one argument")
    }
    return this.callTheClassicVarargPlus(
        args.first(),
        CollectionConverters.ListHasAsScala(args.drop(1)).asScala().toSeq())

}



class Part01KotlinTest : Spek({
    describe("The scala spark vararg functions") {
        val hasVararg = HasVararg()
        it("works as seqs if not annotated and it is not very comfortable") {
            hasVararg.callTheClassicVararg(
                CollectionConverters.ListHasAsScala(
                    listOf("a", "b", "c")
                ).asScala().toSeq()
            )
        }

        it("works as seqs but it can be hidden with extensions") {
            hasVararg.callTheClassicVararg("a", "b", "c")
        }

        it("works as expected if it is annotated") {
            hasVararg.callTheVararg("a", "b", "c")
        }

        it("works as expected if it is annotated but has a single arg as well") {
            hasVararg.callTheVarargPlus("a", "b", "c")
        }

        it("works as expected when it is annotated and calling with an array") {
            val args = arrayOf("a", "b", "c")
            hasVararg.callTheVararg(*args)
        }

        it("works, and more simple than the Java version if it is annotated, has single arg and we call it with an array") {
            val args = arrayOf("a", "b", "c")
            val head = args.first()
            val tail = args.drop(1)
            hasVararg.callTheVarargPlus(head, *tail.toTypedArray())
        }

        it("does not matter if it is annotated, has single arg and we call it with an array trough an extension") {
            val args = arrayOf("a", "b", "c")
            hasVararg.callTheClassicVarargPlus(*args)
        }
    }
})
