package org.geospatial.util;

import static org.geospatial.definitions.Messages.InvalidShapefile;

public class InvalidShapefileException extends Exception {
    public InvalidShapefileException() {
        super(InvalidShapefile);
    }
}
