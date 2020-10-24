package org.geospatial.tools;

import org.geospatial.definitions.ShapeType;
import org.geospatial.geometry.Point;
import org.geospatial.util.Util;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.geospatial.util.SampleShapefileFeaturesPoints.samplePoint;
import static org.junit.Assert.*;


class ShapefileChildIOException extends Shapefile {

    ShapefileChildIOException(String path) {
        super(path);
    }

    @Override
    public void checkShapefileIsValid() throws IOException {
        throw new IOException();
    }
}


class ShapefileChild extends Shapefile {

    ShapefileChild(String path) {
        super(path);
    }

    @Override
    public void checkShapefileIsValid() {
    }

    @Override
    public DataInputStream getShapefileRecords() throws IOException {
        DataInputStream data = Util.getSampleShapefileDataStream();
        ShapefileHeader header = new ShapefileHeader(data);
        return data;
    }
}

public class ShapefileTest {

    @Test
    public void testShapefileConstructor() {
        String path = "path/to/file";
        try {
            new Shapefile(path);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Shapefile shp = new ShapefileChild(path);
        assertEquals(shp.path, path);
    }

    @Test
    public void testShapefileConstructorIOException() {
        String path = "path/to/file";
        new ShapefileChildIOException(path);
    }

    @Test
    public void testShapefileGetFeatures() {
        String path = "path/to/file";
        try {
            Shapefile shp = new ShapefileChild(path);
            ArrayList<Point> points = shp.getFeaturesPoints();
            assertTrue(points.size() > 0);
            assertEquals(points.get(0).x, samplePoint.x, 0.1);
            assertEquals(points.get(0).y, samplePoint.y, 0.1);
            assertEquals(points.get(0).id, samplePoint.id);
            assertEquals(points.get(0).shapeType, ShapeType.POINT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
