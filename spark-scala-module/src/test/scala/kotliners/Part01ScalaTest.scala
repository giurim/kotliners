//package kotliners
//
//import org.apache.spark.SparkConf
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.sql.functions.col
//import org.scalatest.funspec.AnyFunSpec
//
//
//class Part01ScalaTest extends AnyFunSpec {
//  describe("The scala spark vararg functions") {
//    val spark = SparkSession.builder().config(new SparkConf().setAppName("Test").setMaster("local[4]")).getOrCreate()
//
//    val numbers = spark.read
//      .option("header", true)
//      .option("inferSchema", true)
//      .csv("data/numbers.csv")
//
//
//    it("is a bit complicated to use it with a string seq") {
//      val columnsToSelect = Seq("id", "a", "b")
//      numbers.select(
//        columnsToSelect.head, columnsToSelect.tail: _*
//      ).limit(1).show()
//    }
//
//    it("is easy to use it with a col seq") {
//      numbers.select(
//        Seq("id", "a", "b").map(col): _*
//      ).limit(1).show()
//    }
//  }
//}
//
//
