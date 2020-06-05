import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

package object kotliners {
  def x(flattenDF: DataFrame): Unit = {
    val filterDF = flattenDF
      .filter(col("firstName") === "xiangrui" || col("firstName") === "michael")
      .sort(col("lastName").asc)
      .select("fistName", "address", "age")

  }

}
