## Overview

`shapy` is a toy project that showcases the very basic usage of the [picocli](https://picocli.info/) 
framework for creating powerful and elegant Java console applications.

This cli application is capable of reading a shapefile binary file according to the 
[ESRI Shapefile Technical Description](https://www.esri.com/library/whitepapers/pdfs/shapefile.pdf) 
and outputting its header information and features coordinates.

Working with shapefiles for years using other people's shapefile reader implementations, 
I thought that it would be interesting to write my own one in some other language than Python.
To keep the boilerplate code simple, only point shapefiles are supported.
`shapy` doesn't provide functionality to interact with the features attributes 
which are stored in [dBase](https://www.dbase.com/) format.
Implementing support for other geometries would be trivial and so would be reading bytes out of `.dbf` files.  

Reading shapefile involves reading bytes sequentially which is quite straightforward to do using 
[DataInputStream](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/DataInputStream.html).
Because the integers and double-precision integers in a shapefile binary file can be stored both in 
little endian and in big endian byte order, an external utility - 
[EndianUtils](https://commons.apache.org/proper/commons-io/javadocs/api-2.7/org/apache/commons/io/EndianUtils.html) - 
was used to deal with different endian systems.
 
Unit tests are written using the [JUnit4](https://junit.org/junit4/) framework 
and [JaCoCo](https://www.eclemma.org/jacoco/) library is used for generating HTML code coverage reports.

## Build

The `picocli` documentation provides multiple options on 
[how to package the cli application for distribution](https://picocli.info/#_packaging_your_application).
To avoid providing class paths to the `picocli`, other dependencies, and the `shapy`'s compiled classes
when calling the cli application, a Maven plugin 
[Appassembler](https://www.mojohaus.org/appassembler/appassembler-maven-plugin/usage-program.html) came in handy.
It can be used to generate a single `.jar` artifact which contains the application's code as well as all dependencies
artifacts:

```bash
$ mvn clean compile assembly:single
$ java -jar target/shapy-1.0-SNAPSHOT-jar-with-dependencies.jar show-header "C:\GIS\test_data\sites.shp"
```

The path to artifact can be simplified by using an alias if you have access to Bash:

```bash
$ alias shapy="java -jar target/shapy-1.0-SNAPSHOT-jar-with-dependencies.jar"
$ shapy show-header "C:\GIS\test_data\sites.shp"
```

The full build command (with tests and code coverage reporting):

```bash
$ mvn clean compile test assembly:single
$ alias shapy="java -jar target/shapy-1.0-SNAPSHOT-jar-with-dependencies.jar"
$ shapy show-header "C:\GIS\test_data\sites.shp"
```

## Usage

```
Usage: shapy [-hV] [COMMAND]
Command line utility to inspect shapefiles.
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  show-header    Show shapefile header information.
  show-features  Show shapefile features information.

Usage: shapy show-header <path>
Show shapefile header information.
      <path>   The shapefile to inspect.

Usage: shapy show-features [--limit=<limit>] <path>
Show shapefile features information.
      <path>            The shapefile to inspect.
      --limit=<limit>   The number of features to show.
```