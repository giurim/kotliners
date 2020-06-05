//package kotliners;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.sql.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//public class Part01JavaTest {
//    SparkSession spark = SparkSession.builder().config(new SparkConf().setAppName("Test").setMaster("local[4]")).getOrCreate();
//
//    Dataset<Row> numbers = spark.read()
//            .option("header", true)
//            .option("inferSchema", true)
//            .csv("data/numbers.csv");
//
//    @Test
//    void varargtest() {
//        numbers.select("id", "a", "b").limit(1).show();
//    }
//
//    @Test
//    void varargtestArray() {
//        String[] colsToSelect = {"id", "a", "b"};
//
//        // Arrays.stream(colsToSelect).findFirst().get();
//        String head = colsToSelect[0];
//        // Arrays.stream(colsToSelect).skip(1).collect(Collectors.toList()).toArray(new String[]{});
//        String[] tail = Arrays.copyOfRange(colsToSelect, 1, colsToSelect.length);
//
//        numbers.select(head, tail).limit(1).show();
//    }
//
//    @Test
//    void varargtestArrayColumn() {
//        String[] colsToSelect = {"id", "a", "b"};
//        Column[] columns = Arrays.stream(colsToSelect).map(functions::col).collect(Collectors.toList()).toArray(new Column[]{});
//
//        numbers.select(columns).limit(1).show();
//    }
//}
