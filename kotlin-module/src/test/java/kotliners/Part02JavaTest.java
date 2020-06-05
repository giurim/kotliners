package kotliners;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class Part02JavaTest {

    NeedsLambda needsLambda = new NeedsLambda();
    @Test
    void lambdaTest() {
        String result = needsLambda.callTheLambda(s -> s.trim(), "  text");
        assertEquals("text", result);
    }

    @Test
    void inlineLambdaTest() {
        scala.Function1<String, String> f = s -> s.trim();
        String result = needsLambda.callTheLambda(f, "  text");
        assertEquals("text", result);
    }

    @Test
    void methodRefTest() {
        String result = needsLambda.callTheLambda(String::trim, "  text");
        assertEquals("text", result);
    }
}
