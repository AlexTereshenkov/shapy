package org.geospatial.geometry;


import org.geospatial.definitions.ShapeType;

public class Point {
    public double x;
    public double y;
    public ShapeType shapeType;
    public int contentSize;
    public int id;

    public Point(double x, double y, int contentSize, int recordNumber) {
        this.id = recordNumber;
        this.x = x;
        this.y = y;
        this.shapeType = ShapeType.POINT;
        this.contentSize = contentSize;
    }

    public String toString() {
        return String.format("Point(objectid=%s, x=%s, y=%s)", this.id, this.x, this.y);
    }
}
