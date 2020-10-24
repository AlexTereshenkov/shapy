package org.geospatial.definitions;

import static org.geospatial.tools.Shapefile.shapefileFileCode;

public class Messages {
    public static String InvalidShapefile = String.format(
            "Invalid shapefile: expecting %s as the file code header.", shapefileFileCode);
    public static String NotFoundShapefile = "The system cannot find this shapefile.";
}
