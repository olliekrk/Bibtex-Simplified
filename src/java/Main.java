import org.apache.commons.cli.*;
import parser.BibtexBibliography;
import parser.BibtexParser;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        String data = "@STRING{STOCkey = \"OXsingleletterstoc\"}@INBOOK{inbookcrossref,PoleNieuwzglednione=123,crossref = \"whole-set\",publisher=\"dziwaczka\",editor=\"kwaczka | to | niezla | taczka | Alfred B. Bekon\",title = \"Fundamental Algorithms\",volume = 1,series = \"The Art of Computer Programming\",edition = \"Second\" # \" \" # stockey ,year = \"1973\", type = \"Section\", chapter = \"1.2\", note = \"This is a cross-referencing INBOOK entry\",}       ";
//
//        BibtexBibliography b = BibtexParser.parseData(data);
//        System.out.println(b.getAllEntries().size());
//        System.out.println(b.getAllValues().size());
//
//        BibtexPrintingVisitor v = new BibtexPrintingVisitor('=', 40, 40);
//        v.visit(b);

        //ABOVE SHOULD WORK
        CommandLine cmd = parseArguments(args);
        try {
            BibtexBibliography bibliography = BibtexParser.parseFile(cmd.getOptionValue("file"));
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem to open a file located at: " + cmd.getOptionValue("file"));
            System.exit(2);
        }


    }

    private static CommandLine parseArguments(String[] args) {
        Options options = new Options();

        //todo
        //może lepiej użyć Option.Builder
        //to jest opis opcji, a nie to co sie wyswietla, zmienic
        options.addOption(new Option("n", "names", true, "Input authors (editors) names: "));
        options.addOption(new Option("c", "categories", true, "Input categories: "));
        options.addOption(new Option("s", "sign", true, "Input sign to build table: "));
        options.addOption(new Option("h", "help", false, "help on using this parser"));
        options.addOption(new Option("f", "file", true, "path to a file which will be parsed"));
        //...

        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            //todo
            helpFormatter.printHelp("xoxo", options);
            System.exit(1);
            return null;
        }

        if (commandLine.hasOption('h')) {
            helpFormatter.printHelp("xoxo", options);
            System.exit(0);
            return null;
        }

        return commandLine;
    }
}