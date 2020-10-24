package org.geospatial.commands;

import org.geospatial.tools.Shapefile;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;


@Command(name = "shapy", mixinStandardHelpOptions = true, version = "shapy 1.0",
        description = "Command line utility to inspect shapefiles.",
        subcommands = {ShowHeader.class, ShowFeatures.class})
class Shapy implements Callable<Integer> {
    public static void main(String... args) {
        int exitCode = new CommandLine(new Shapy()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }

}

@Command(name = "show-header", description = "Show shapefile header information.")
class ShowHeader implements Callable<Integer> {

    @Parameters(index = "0", description = "The shapefile to inspect.")
    private String path;

    @Override
    public Integer call() throws Exception {
        int exitCode = 0;
        try {
            Shapefile shp = new Shapefile(path);
            shp.showHeader();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            exitCode = 1;
        }
        return exitCode;
    }
}

@Command(name = "show-features", description = "Show shapefile features information.")
class ShowFeatures implements Callable<Integer> {

    @Parameters(index = "0", description = "The shapefile to inspect.")
    private String path;

    @CommandLine.Option(names = {"--limit"}, description = "The number of features to show.", defaultValue = "10")
    private Integer limit;

    @Override
    public Integer call() throws Exception {
        int exitCode = 0;
        try {
            Shapefile shp = new Shapefile(path);
            shp.showFeatures(limit);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            exitCode = 1;
        }
        return exitCode;
    }
}
