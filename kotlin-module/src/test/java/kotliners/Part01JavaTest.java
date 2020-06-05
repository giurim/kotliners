package kotliners;

import org.junit.jupiter.api.Test;
import scala.jdk.CollectionConverters;

import java.util.Arrays;

public class Part01JavaTest {

    HasVararg hasVararg = new HasVararg();

    @Test
    void classicVarargTest() {
        // not annotated
        hasVararg.callTheClassicVararg(
                CollectionConverters.ListHasAsScala(
                        Arrays.asList("a", "b", "c")
                ).asScala().toSeq()
        );
    }

    @Test
    void varargTest() {
        // annotated
        hasVararg.callTheVararg("a", "b", "c");
    }

    @Test
    void varargPlusTest() {
        // annotated, single arg + vararg
        hasVararg.callTheVarargPlus("a", "b", "c");
    }

    @Test
    void varargArrayTest() {
        // annotated call with array
        String[] args = {"a", "b", "c"};
        hasVararg.callTheVararg(args);
    }

    @Test
    void varargPlusArrayTest() {
        // annotated, single arg + vararg,  call with array
        String[] args = {"a", "b", "c"};
        String head = args[0];
        String[] tail = Arrays.copyOfRange(args, 1, args.length);
        hasVararg.callTheVarargPlus(head, tail);
    }
}
