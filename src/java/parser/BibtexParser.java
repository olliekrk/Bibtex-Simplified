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

/**
 * Class used for parsing .bib files and creating {@link BibtexBibliography} object as a result of parsing.
 * All methods in this class are static.
 */
public class BibtexParser {

    /**
     * Main parsing method.
     * Returns new {@link BibtexBibliography} created by reading .bib file that is located under given path.
     *
     * @param path path to a .bib file
     * @return bibliography created on given file
     * @throws FileNotFoundException when there is a problem finding .bib file under given path
     */
    public static BibtexBibliography parseFile(String path) throws FileNotFoundException {
        return parseBibtex(new Scanner(new File(path)));
    }

    /**
     * Method which returns new {@link BibtexBibliography} based on data from scanner.
     *
     * @param scanner scanner which contains BibTeX data
     * @return bibliography created on given data
     */
    private static BibtexBibliography parseBibtex(Scanner scanner) {

        BibtexBibliography bibliography = new BibtexBibliography();

        Pattern entrySignature = Pattern.compile("@(\\w+)\\s*\\{");

        while (scanner.findWithinHorizon(entrySignature, 0) != null) {
            String entryType = scanner.match().group(1).toLowerCase();
            String entryData;

            try {
                entryData = ParserUtilities.scanData(scanner);
            } catch (MissingClosingBracketException e) {
                //exception, notify, interrupt reading rest
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

    /**
     * Method used to create single {@link BibtexEntry} based on given data.
     * If entry is valid it adds created entry to bibliography.
     * If there was any problem with parsing that entry, it notifies about it and does not add that entry to bibliography.
     *
     * @param entryType    type of the entry
     * @param entryData    content of the entry
     * @param bibliography bibliography to which entry will be eventually added
     * @throws ParsingException when there occurs any problem with the data
     */
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

    /**
     * Method used to create single @STRING entry from given data.
     * If @STRING entry is valid, this method adds it to bibliography.
     *
     * @param entryData    string data to be parsed
     * @param bibliography bibliography to which @STRING will be eventually added
     * @throws ParsingException when there occurs any problem with the data
     */
    public static void readString(String entryData, BibtexBibliography bibliography) throws ParsingException {

        String[] entryFields = ParserUtilities.splitIntoFields("string", entryData);
        if (entryFields.length != 1) {
            //exception, string may have only 1 value
            throw new InvalidEntryException("string", "unknown", entryData);
        }

        String stringField = entryFields[0];
        Pattern fieldValuePattern = Pattern.compile("(\\S+)\\s*=\\s*(\\S.*)");
        Matcher matcher = fieldValuePattern.matcher(stringField);

        if (!matcher.matches()) {
            //exception, invalid string entry
            throw new InvalidEntryException("string", "unknown", entryData);
        }

        IBibtexValue value = ParserUtilities.readStrings(matcher.group(2), bibliography);
        bibliography.addValue(matcher.group(1), value);
    }
}
