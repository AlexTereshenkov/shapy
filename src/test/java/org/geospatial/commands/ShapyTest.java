package org.geospatial.commands;

import org.geospatial.definitions.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ShapyTest {

    final PrintStream originalOut = System.out;
    final PrintStream originalErr = System.err;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        out.reset();
        err.reset();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testShapyNoArgs() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);
        int exitCode = cmd.execute();
        assertEquals(exitCode, 0);
        assertTrue(out.toString().contains("show-header"));
    }

    @Test
    public void testShapyGetHelp() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("--help");
        assertEquals(exitCode, 0);
        assertTrue(out.toString().contains("show-header"));
    }

    @Test
    public void testShapyGetVersion() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("--version");
        assertEquals(exitCode, 0);
        assertTrue(out.toString().contains("shapy 1.0"));
    }

    @Test
    public void testShapyShowHeaderValidShapefile() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("show-header", "src/test/java/resources/points.shp");
        assertEquals(exitCode, 0);
        assertTrue(out.toString().contains("version : 1000"));
    }

    @Test
    public void testShapyShowHeaderInvalidShapefile() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("show-header", "src/test/java/resources/points.dbf");
        assertEquals(exitCode, 1);
        assertTrue(err.toString().contains(Messages.InvalidShapefile));
    }

    @Test
    public void testShapyShowFeaturesValidShapefile() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("show-features", "src/test/java/resources/points.shp", "--limit", "3");
        assertEquals(exitCode, 0);
        String outAsString = out.toString();
        for (String val : new String[]{"objectid=1", "objectid=2", "objectid=3"}) {
            assertTrue(outAsString.contains(val));
        }
        assertFalse(outAsString.contains("objectid=4"));
    }

    @Test
    public void testShapyShowFeaturesValidShapefileLimitLargerThanFeaturesCount() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("show-features", "src/test/java/resources/points.shp", "--limit", "100");
        assertEquals(exitCode, 0);
    }

    @Test
    public void testShapyShowFeaturesInvalidShapefile() {
        Shapy shapy = new Shapy();
        CommandLine cmd = new CommandLine(shapy);

        int exitCode = cmd.execute("show-features", "src/test/java/resources/points.dbf", "--limit", "3");
        assertEquals(exitCode, 1);
        assertTrue(err.toString().contains(Messages.InvalidShapefile));
    }
}
