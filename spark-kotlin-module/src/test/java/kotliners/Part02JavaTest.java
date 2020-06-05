//package kotliners;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.assertEquals;
//
//public class Part02JavaTest {
//
//    ///Users/gmora/wp/kotliners/kotliners-examples/kotlin-module/src/test/java/kotliners/Part02JavaTest.java:11: error: incompatible types: Function1 is not a functional interface
//    //        Object result = NeedsLambda.callTheLambda((Object s)-> s.toString() + "output", "input");
//    @Test
//    void lambdatest() {
//
//        scala.Function1<String, String> f = s -> s.trim();
//        String result = NeedsLambda.callTheLambda(f, "  text");
//        assertEquals("text", result);
//    }
//
//    @Test
//    void methodreftest() {
//        scala.Function1<String, String> f = String::trim;
//        String result = NeedsLambda.callTheLambda(f, "  text");
//        assertEquals("text", result);
//    }
//
//
//}
