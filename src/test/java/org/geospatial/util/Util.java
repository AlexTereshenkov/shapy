package org.geospatial.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Util {

    public static DataInputStream getSampleShapefileDataStream() {
        byte[] binaryData = ByteBuffer.allocate(SampleShapefileProperties.shpHeaderByteSize).
                putInt(SampleShapefileProperties.fileCode).
                putInt(SampleShapefileProperties.unused).
                putInt(SampleShapefileProperties.unused).
                putInt(SampleShapefileProperties.unused).
                putInt(SampleShapefileProperties.unused).
                putInt(SampleShapefileProperties.unused).
                putInt(SampleShapefileProperties.fileLength).order(ByteOrder.LITTLE_ENDIAN).
                putInt(SampleShapefileProperties.version).order(ByteOrder.LITTLE_ENDIAN).
                putInt(SampleShapefileProperties.shapeType).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Xmin).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Ymin).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Xmax).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Ymax).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Zmin).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Zmax).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Mmin).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileProperties.Mmax).order(ByteOrder.BIG_ENDIAN).
                putInt(SampleShapefileFeaturesPoints.samplePoint.id).order(ByteOrder.BIG_ENDIAN).
                putInt(SampleShapefileFeaturesPoints.samplePoint.contentSize).order(ByteOrder.LITTLE_ENDIAN).
                putInt(1).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileFeaturesPoints.samplePoint.x).order(ByteOrder.LITTLE_ENDIAN).
                putDouble(SampleShapefileFeaturesPoints.samplePoint.y).
                array();
        InputStream inputStream = new ByteArrayInputStream(binaryData);
        return new DataInputStream(inputStream);
    }
}
