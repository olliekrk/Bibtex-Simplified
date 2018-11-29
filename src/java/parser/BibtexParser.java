package parser;

import entries.general.BibtexEntry;
import entries.general.BibtexEntryFactory;
import entries.general.BibtexEntryType;
import exceptions.InvalidEntryException;
import exceptions.MissingClosingBracketException;
import exceptions.ParsingException;
import values.IBibtexValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibtexParser {

    //input data by giving path to .bib file as an argument

    public static BibtexBibliography parseFile(String path) throws FileNotFoundException {
        return parseBibtex(new Scanner(new File(path)));
    }

    //method which creates bibtex bibliography as a result

    private static BibtexBibliography parseBibtex(Scanner scanner) {

        BibtexBibliography bibliography = new BibtexBibliography();

        Pattern entrySignature = Pattern.compile("@(\\w+)\\s*\\{");

        while (scanner.findWithinHorizon(entrySignature, 0) != null) {
            String entryType = scanner.match().group(1).toLowerCase();
            String entryData;

            try {
                entryData = ParserUtilities.scanData(scanner);
            } catch (MissingClosingBracketException e) {
                //exception, notify, interrupts reading rest
                System.out.println(e.getMessage());
                return bibliography;
            }

            try {
                readEntry(entryType, entryData, bibliography);
            } catch (Exception e) {
                //exception, notify that it was impossible to read that Entry and continue
                System.out.println(e.getMessage());
            }
        }
        return bibliography;
    }

    //method to interpret entry of any known type

    public static void readEntry(String entryType, String entryData, BibtexBibliography bibliography) throws ParsingException {
        if (entryType.equals("string")) {
            readString(entryData, bibliography);
            return;
        }
        if (entryType.equals("preamble") || entryType.equals("comment")) {
            return;
        }

        Class<? extends BibtexEntry> entryClass = BibtexEntryType.findEntryClass(entryType);

        String[] entryFields = ParserUtilities.splitIntoFields(entryType, entryData);

        String entryId = entryFields[0];

        Pattern entryIdPattern = Pattern.compile("[a-zA-Z0-9].+");

        //exception, invalid id of entry
        if (!entryIdPattern.matcher(entryId).matches()) {
            throw new InvalidEntryException(entryType, entryId);
        }

        Map<String, IBibtexValue> entryValues = ParserUtilities.splitIntoValues(entryFields, bibliography);

        BibtexEntry entry = BibtexEntryFactory.createEntry(entryClass, entryId, entryValues);

        if (entry != null) {
            bibliography.addEntry(entry);
        }
    }

    //method to interpret @string

    public static void readString(String entryData, BibtexBibliography bibliography) throws ParsingException {

        String[] entryFields = ParserUtilities.splitIntoFields("string", entryData);
        if (entryFields.length != 1) {
            //exception, string may have only 1 value
            throw new InvalidEntryException("string","unknown",entryData);
        }

        String stringField = entryFields[0];
        Pattern fieldValuePattern = Pattern.compile("(\\S+)\\s*=\\s*(\\S.*)");
        Matcher matcher = fieldValuePattern.matcher(stringField);

        if (!matcher.matches()) {
            //exception, invalid string entry
            throw new InvalidEntryException("string","unknown",entryData);
        }

        IBibtexValue value = ParserUtilities.readStrings(matcher.group(2), bibliography);
        bibliography.addValue(matcher.group(1), value);
    }
}
