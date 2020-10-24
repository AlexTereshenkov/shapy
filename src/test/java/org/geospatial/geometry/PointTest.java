package org.geospatial.geometry;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PointTest {

    @Test
    public void testConstructPoint() throws IOException {
        Point p = new Point(10, 20, 10000, 1);
        assertEquals(p.x, 10, 0.001);
        assertEquals(p.y, 20, 0.001);
        assertEquals(p.contentSize, 10000, 0.001);
        assertEquals(p.id, 1);
        assertEquals(p.toString(), "Point(objectid=1, x=10.0, y=20.0)");
    }
}
