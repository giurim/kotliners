package kotliners;

import org.junit.Test;

class Part06JavaTest {
    @Test
    void extensionFunctionTest() {
        shadowed.com.fasterxml.jackson.databind.ObjectMapper json = new shadowed.com.fasterxml.jackson.databind.ObjectMapper();
        shadowed.com.fasterxml.jackson.module.kotlin.ExtensionsKt.registerKotlinModule(json);
    }
}

