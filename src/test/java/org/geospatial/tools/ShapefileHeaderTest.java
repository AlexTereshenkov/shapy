package org.geospatial.tools;

import org.geospatial.definitions.ShapeType;
import org.geospatial.util.SampleShapefileProperties;
import org.geospatial.util.Util;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ShapefileHeaderTest {

    @Test
    public void testConstructPoint() throws IOException {
        SampleShapefileProperties props = new SampleShapefileProperties();
        DataInputStream data = Util.getSampleShapefileDataStream();
        ShapefileHeader shpHeader = new ShapefileHeader(data);
        assertEquals(shpHeader.fileCode, props.fileCode);
        assertEquals(shpHeader.fileLength, props.fileLength);
        assertEquals(shpHeader.version, props.version);
        assertEquals(shpHeader.shapeType, ShapeType.POINT);
        assertEquals(shpHeader.Xmin, props.Xmin, 0.1);
        assertEquals(shpHeader.Xmax, props.Xmax, 0.1);
        assertEquals(shpHeader.Ymin, props.Ymin, 0.1);
        assertEquals(shpHeader.Ymax, props.Ymax, 0.1);
        assertEquals(shpHeader.Mmin, props.Mmin, 0.1);
        assertEquals(shpHeader.Mmax, props.Mmax, 0.1);
        assertEquals(shpHeader.Zmin, props.Zmin, 0.1);
        assertEquals(shpHeader.Zmax, props.Zmax, 0.1);
    }
}
