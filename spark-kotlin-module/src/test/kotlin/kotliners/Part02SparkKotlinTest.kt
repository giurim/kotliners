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
import java.io.Serializable


class Part02SparkKotlinTest : Spek({

    describe("Calling Spark UDF from Kotlin") {
        val spark = SparkSession.builder().config(SparkConf().setAppName("Test").setMaster("local[4]")).orCreate

        val numbers = spark.read()
            .option("header", true)
            .option("inferSchema", true)
            .csv("data/numbers.csv")

        it("is possible trough Kotlin lambdas extending the Java interface") {
            numbers.printSchema()

            numbers.select(
                col("c"),
                udf(UDF1 { x: String? -> x?.let { x + x } }, DataTypes.StringType.asNullable()).apply(col("c"))
            ).limit(2).show()
        }


        it("is not possible to directly implement a Scala lambda") {
                // Caused by: java.io.NotSerializableException: kotliners.Part02KotlinTest$1$1$2$f1$1
                //Serialization stack:
                //	- object not serializable (class: kotliners.Part02KotlinTest$1$1$2$f1$1, value: <function1>)

                // we cannot have to "implement" the Serializable interface here
                val f1 = object : AbstractFunction1<String, String>(),Serializable {
                    override fun apply(v1: String?): String? {
                        return v1?.let { it + it }
                    }
                }

                numbers.select(
                    col("c"),
                    udf(f1, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
        }

        it("this looks like it works with a simple lambda (this one compiles but fails)") {
            // scala.Predef$.assert(Predef.scala:156)
            assertThrows<Throwable> {
                numbers.select(
                    col("c"),
                    udf({ s: String? -> s?.let { it + it } }, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
            }
        }

        it("how about better types") {
            assertThrows<Throwable> {
                // scala.Predef$.assert(Predef.scala:156)
                val func: (s: String?) -> (String?) = { s -> s?.let { it + it } }
                numbers.select(
                    col("c"),
                    udf(func, DataTypes.StringType).apply(col("c"))
                ).limit(2).show()
            }
        }
    }
})
