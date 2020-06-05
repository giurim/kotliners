package kotliners

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


class Part01KotlinTest : Spek({
    describe("The scala spark vararg functions") {
        val spark = SparkSession.builder().config(SparkConf().setAppName("Test").setMaster("local[4]")).orCreate

        val numbers = spark.read()
            .option("header", true)
            .option("inferSchema", true)
            .csv("data/numbers.csv")

        it("can be normal (columns)") {
            numbers.select(col("id"), col("a"), col("b"))
                .limit(1).show()
        }

        it("can be normal (strings)") {
            numbers.select("id", "a", "b")
                .limit(1).show()
        }


        it("can be used with an array, but it is complicated") {
            numbers.select(
                *listOf("id", "a", "b").map { col(it) }.toTypedArray()
            ).limit(1).show()
        }

        it("it can be weird to use it with a string array") {
            val columnsIneed = arrayOf("id", "a", "b")
            numbers.select(columnsIneed.first(), *columnsIneed.drop(1).toTypedArray())
                .limit(1).show()
        }
    }
})
