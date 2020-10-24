package org.geospatial.tools;

import org.apache.commons.io.EndianUtils;
import org.geospatial.geometry.Point;
import org.geospatial.util.InvalidShapefileException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.geospatial.definitions.Messages.NotFoundShapefile;


public class Shapefile {
    String path;
    public static int shapefileFileCode = 9994;

    public Shapefile(String path) {
        this.path = path;
        try {
            checkShapefileIsValid();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(NotFoundShapefile);
        } catch (InvalidShapefileException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataInputStream getDataInputStream() throws FileNotFoundException {
        final FileInputStream fileInputStream = new FileInputStream(new File(this.path));
        return new DataInputStream(new BufferedInputStream(fileInputStream));
    }

    private ShapefileHeader getShapefileHeader() throws IOException {
        DataInputStream dataInputStream = getDataInputStream();
        return new ShapefileHeader(dataInputStream);
    }

    protected DataInputStream getShapefileRecords() throws IOException {
        DataInputStream dataInputStream = getDataInputStream();
        // reading the file header bytes to move the marker to the records
        ShapefileHeader header = new ShapefileHeader(dataInputStream);
        return dataInputStream;
    }

    protected void checkShapefileIsValid() throws InvalidShapefileException, IOException {
        // mark the stream to read the first 4 bytes to get the file code and then reset
        DataInputStream dataInputStream = getDataInputStream();
        dataInputStream.mark(4);
        int fileCode = dataInputStream.readInt();
        if (fileCode != shapefileFileCode) {
            throw new InvalidShapefileException();
        }
        dataInputStream.reset();
    }

    protected ArrayList<Point> getFeaturesPoints() throws IOException {
        ArrayList<Point> points = new ArrayList<>();
        DataInputStream dataInputStream = getShapefileRecords();
        while (dataInputStream.available() > 0) {
            int recordNumber = dataInputStream.readInt();
            int contentLength = dataInputStream.readInt();
            int contentLengthInBytes = contentLength + contentLength - 4;

            int recordShapeType = EndianUtils.readSwappedInteger(dataInputStream);
            double x = EndianUtils.readSwappedDouble(dataInputStream);
            double y = EndianUtils.readSwappedDouble(dataInputStream);
            Point p = new Point(x, y, contentLengthInBytes, recordNumber);
            points.add(p);
        }
        return points;
    }

    public void showHeader() throws Exception {
        ShapefileHeader header = getShapefileHeader();
        Class cls = header.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(header);
            System.out.printf("%s : %s\n", field.getName(), value);
        }
    }

    public void showFeatures(Integer limit) throws IOException {
        ArrayList<Point> features = this.getFeaturesPoints();
        for (int i = 0; i < limit && i < features.size(); i++) {
            System.out.println(features.get(i));
        }
    }
}
