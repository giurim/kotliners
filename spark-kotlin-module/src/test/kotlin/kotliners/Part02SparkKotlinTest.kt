package kotliners

import org.apache.spark.SparkConf
import org.apache.spark.SparkException
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.api.java.UDF1
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.DataTypes
import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import scala.runtime.AbstractFunction1
import kotlin.test.assertEquals


class Part02SparkKotlinTest : Spek({

    describe ("Calling a Scala lambda from Kotlin"){
        val f:Function1<String,String> = {s:String-> s + "output"}

        val result = NeedsLambda.callTheLambda(f, "input")

        assertEquals("inputoutput", result)
    }
    describe("Calling Spark UDF from Kotlin") {
        val spark = SparkSession.builder().config(SparkConf().setAppName("Test").setMaster("local[4]")).orCreate

        val numbers = spark.read()
            .option("header", true)
            .option("inferSchema", true)
            .csv("data/numbers.csv")

        it("is possible trough Kotlin lambdas in java style") {
            numbers.printSchema()

            numbers.select(
                col("c"),
                udf(UDF1 { x: String? -> x?.let { x + x } }, DataTypes.StringType.asNullable()).apply(col("c"))
            ).limit(2).show()
        }


        it("is not possible to directly implement Scala function") {

            assertThrows<SparkException> {
                // Caused by: java.io.NotSerializableException: kotliners.Part02KotlinTest$1$1$2$f1$1
                //Serialization stack:
                //	- object not serializable (class: kotliners.Part02KotlinTest$1$1$2$f1$1, value: <function1>)
                //	- element of array (index: 3)
                //	- array (class [Ljava.lang.Object;, size 4)
                val f1 = object : AbstractFunction1<String, String>() {
                    override fun apply(v1: String?): String? {
                        return v1?.let { it + it }
                    }
                }

                // e: /Users/gmora/wp/kotliners/kotliners-examples/kotlin-module/src/test/kotlin/kotliners/Part02KotlinTest.kt: (52, 51): Interface Function1 does not have constructors
                // scala 2.11.12
                // e: /Users/gmora/wp/kotliners/kotliners-examples/kotlin-module/src/test/kotlin/kotliners/Part02KotlinTest.kt: (36, 48): Object must be declared abstract or implement abstract member public abstract fun `apply$mcFI$sp`(p0: Int): Float defined in scala.Function1
                //val f2: Function1<String?, String?> = { s:String? -> s?.let { it + it } }

                numbers.select(
                    col("c"),
                    udf(f1, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
            }
        }
        //  java.lang.ClassCastException: kotliners.Part02KotlinTest$1$1$3$1 cannot be cast to scala.Function1
        it("this looks like it works with a simple lambda (this one compiles but fails)") {
            // kotliners.Part02KotlinTest$1$1$3$1 cannot be cast to scala.Function1
            assertThrows<ClassCastException> {
                numbers.select(
                    col("c"),
                    udf({ s: String? -> s?.let { it + it } }, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
            }
        }

        it("how about better types") {
            assertThrows<ClassCastException> {
                // kotliners.Part02KotlinTest$1$1$3$1 cannot be cast to scala.Function1
                val func: (s: String?) -> (String?) = { s -> s?.let { it + it } }
                numbers.select(
                    col("c"),
                    udf(func, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
            }
        }
    }
})
