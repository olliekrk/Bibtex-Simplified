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

/**
 * Main class of BibTeX parser project.
 * Provides console interface to perform BibTeX file parsing using {@link BibtexParser}.
 *
 * @author Olgierd Królik
 * @see <a href="http://www.bibtex.org/Format/">BibTeX Description</a>
 */
public class Main {
    /**
     * Entrance to the program.
     * Provides several options user can apply to a BibTeX file parsing procedure.
     * <a>
     *     usage: bibtexparser [-c <category-list>] -f <path-to-file> [-h] [-n <authors-list>] [-s <sign>]
     * <p>
     *     List of options which are available to apply:
     * <p>
     *     -c,--categories <category-list>   list of entries' categories
     *     -f,--file <path-to-file>          path to a '.bib' file
     *     -h,--help                         displays help
     *     -n,--names <authors-list>         list of authors' names
     *     -s,--sign <sign>                  ASCII sign used to print tables
     * </a>
     *
     * @param args program arguments
     */
    public static void main(String[] args) {

        CommandLine cmd = loadCMD(args);

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

    /**
     * Method made with help of Commons CLI library.
     * It parses arguments user ran the program with.
     *
     * @param args program arguments
     * @return {@link CommandLine} prepared to perform BibTeX parsing operation
     */
    private static CommandLine loadCMD(String[] args) {
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
        String footer = "\nThis console BiBTeX parser was made by Olgierd Królik.\n\n";
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
        }

        return commandLine;
    }
}