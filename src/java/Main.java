import entries.general.BibtexVisitor;
import org.apache.commons.cli.*;
import parser.BibtexBibliography;
import parser.BibtexParser;
import printer.BibtexPrintingVisitor;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        CommandLine cmd = prepareCMD(args);

        BibtexBibliography bibliography = null;

        try {
            bibliography = BibtexParser.parseFile((cmd.getOptionValue("file")));
        } catch (FileNotFoundException e) {
            System.out.println("Encountered a problem while opening a file located at: " + cmd.getOptionValue("file"));
            System.exit(2);
        }

        BibtexVisitor visitor;

        if (cmd.hasOption("sign")) {
            visitor = new BibtexPrintingVisitor(cmd.getOptionValue("sign").charAt(0), 50, 50);
        } else {
            visitor = new BibtexPrintingVisitor('@', 50, 50);
        }

        Map<String, List<String>> filters = new HashMap<>();

        if (cmd.hasOption("names")) {
            filters.put("names", Arrays.asList(cmd.getOptionValues("names")));
        }
        if (cmd.hasOption("categories")) {
            filters.put("categories", Arrays.asList(cmd.getOptionValues("categories")));
        }
        if (bibliography != null) {
            visitor.visit(bibliography, filters);
        }
    }

    private static CommandLine prepareCMD(String[] args) {
        Options options = new Options();

        Option file = Option.builder("f")
                .longOpt("file")
                .hasArg()
                .argName("path-to-file")
                .required()
                .desc("path to a '.bib' file")
                .build();

        Option names = Option.builder("n")
                .longOpt("names")
                .hasArgs()
                .argName("authors-list")
                .desc("list of authors' names")
                .valueSeparator(',')
                .build();

        Option categories = Option.builder("c")
                .longOpt("categories")
                .hasArgs()
                .argName("category-list")
                .desc("list of entries' categories")
                .valueSeparator(',')
                .build();

        Option sign = Option.builder("s")
                .longOpt("sign")
                .hasArg()
                .argName("sign")
                .desc("ASCII sign used to print tables")
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("displays help")
                .build();

        options.addOption(names)
                .addOption(categories)
                .addOption(sign)
                .addOption(help)
                .addOption(file);

        String header = "\nList of options which are available to apply:\n\n";
        String footer = "\nThis console BiBTeX parser was made by Olgierd Królik.";
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;

        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Inappropriate use of parser. Please follow syntax displayed below.\n");
            //prints help with a proper use of this parser
            helpFormatter.printHelp("bibtexparser", header, options, footer, true);
            System.exit(1);
            return null;
        }

        if (commandLine.hasOption("h")) {
            helpFormatter.printHelp("bibtexparser", header, options, footer, true);
            System.exit(0);
            return null; //commandLine
        }

        return commandLine;
    }
}