package org.geospatial.definitions;

public enum ShapeType {
    NULL(0), POINT(1), POLYLINE(3);

    private final int shapeTypeInt;

    private ShapeType(int shapeTypeInt) {
        this.shapeTypeInt = shapeTypeInt;
    }

}
