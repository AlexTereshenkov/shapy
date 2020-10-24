package org.geospatial.tools;

import org.apache.commons.io.EndianUtils;
import org.geospatial.definitions.ShapeType;

import java.io.DataInputStream;
import java.io.IOException;

public class ShapefileHeader {
    int fileCode;
    int fileLength;
    int version;
    ShapeType shapeType;
    double Xmin;
    double Ymin;
    double Xmax;
    double Ymax;
    double Zmin;
    double Zmax;
    double Mmin;
    double Mmax;

    public ShapefileHeader(DataInputStream dataInputStream) throws IOException {
        this.fileCode = dataInputStream.readInt();
        // skipping reserved bytes as per spec
        dataInputStream.skip(20);
        this.fileLength = dataInputStream.readInt();
        this.version = EndianUtils.readSwappedInteger(dataInputStream);
        this.shapeType = ShapeType.values()[EndianUtils.readSwappedInteger(dataInputStream)];
        this.Xmin = EndianUtils.readSwappedDouble(dataInputStream);
        this.Ymin = EndianUtils.readSwappedDouble(dataInputStream);
        this.Xmax = EndianUtils.readSwappedDouble(dataInputStream);
        this.Ymax = EndianUtils.readSwappedDouble(dataInputStream);
        this.Zmin = EndianUtils.readSwappedDouble(dataInputStream);
        this.Zmax = EndianUtils.readSwappedDouble(dataInputStream);
        this.Mmin = EndianUtils.readSwappedDouble(dataInputStream);
        this.Mmax = EndianUtils.readSwappedDouble(dataInputStream);
    }
}
