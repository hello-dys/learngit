/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    // Testing strategy

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testPoem() throws IOException {
        final String input = "Seek to explore new and exciting synergies!";
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/test.txt"));
        Assert.assertEquals("Seek to explore strange new life and exciting synergies!", poet.poem(input));
    }

    @Test
    public void testToString() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/test.txt"));
        Assert.assertEquals("""
                GraphPoet {
                \tVertices: [new, worlds, explore, and, to, civilizations, seek, strange, life, out]
                \tEdges:
                \t\tto->explore:1
                \t\texplore->strange:1
                \t\tstrange->new:1
                \t\tnew->worlds:1
                \t\tto->seek:1
                \t\tseek->out:1
                \t\tout->new:1
                \t\tnew->life:1
                \t\tlife->and:1
                \t\tand->new:1
                \t\tnew->civilizations:1
                }""", poet.toString());
    }
}
