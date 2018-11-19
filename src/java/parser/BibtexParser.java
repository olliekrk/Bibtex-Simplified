package parser;

import entries.general.BibtexEntry;
import entries.general.BibtexEntryFactory;
import entries.general.BibtexEntryType;
import exceptions.*;
import values.IBibtexValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class BibtexParser {

    //4 ways to input data

    public static BibtexBibliography parseFile(String path) throws FileNotFoundException {
        return parseBibtex(new Scanner(new File(path)));
    }

    public static BibtexBibliography parseFile(File file) throws FileNotFoundException {
        return parseBibtex(new Scanner(file));
    }

    public static BibtexBibliography parseData(InputStream inputStream) {
        return parseBibtex(new Scanner(inputStream));
    }

    public static BibtexBibliography parseData(String data) {
        return parseBibtex(new Scanner(data));
    }

    //one universal parsing method

    private static BibtexBibliography parseBibtex(Scanner scanner) {

        BibtexBibliography bibliography = new BibtexBibliography();

        final Pattern entrySignature = Pattern.compile("@(\\w+)\\s*\\{");

        while (scanner.findWithinHorizon(entrySignature, 0) != null) {
            String entryType = scanner.match().group(1).toLowerCase();
            String entryData;

            try {
                entryData = ParserUtilities.scanData(scanner);
            } catch (MissingClosingBracketException e) {
                //exception, notify, interrupts reading rest
                return bibliography;
            }

            try {
                readEntry(entryType, entryData, bibliography);
            } catch (Exception e) {
                //exception, notify that it was impossible to read that Entry and continue
                e.printStackTrace();
            }
        }

        return bibliography;
    }

    //method to interpret entry of any known type

    private static void readEntry(String entryType, String entryData, BibtexBibliography bibliography) throws ParsingException {
        if (entryType.equals("string")) {
            readString(entryData, bibliography);
            return;
        }
        if (entryType.equals("preamble") || entryType.equals("comment")) {
            return;
        }

        Class<? extends BibtexEntry> entryClass = BibtexEntryType.findEntryClass(entryType);
        if (entryClass == null) {
            throw new UnknownEntryTypeException();
        }

        //String[] entryFields = ParserUtilities.splitUsingDelimiter(entryData, ',');
        String[] entryFields = entryData.split(",");
        IntStream.range(0, entryFields.length).forEach(i -> entryFields[i] = entryFields[i].trim());

        if (entryFields.length == 0) {
            //exception, empty entry
            //consider throwing also entry containing only 1 id field
            throw new InvalidEntryException();
        }

        String entryId = entryFields[0];
        Pattern entryIdPattern = Pattern.compile("\\w+");
        if (!entryIdPattern.matcher(entryId).matches()) {
            //exception, invalid id of entry
            throw new InvalidEntryException();
        }

        Map<String, IBibtexValue> entryValues = ParserUtilities.splitIntoValues(entryFields, bibliography);

        BibtexEntry entry = BibtexEntryFactory.createEntry(entryClass, entryId, entryValues);
        if (entry != null) {
            bibliography.addEntry(entry);
        }
    }

    //method to interpret @string

    private static void readString(String entryData, BibtexBibliography bibliography) throws ParsingException {

        //String[] entryFields = ParserUtilities.splitUsingDelimiter(entryData, ',');
        String[] entryFields = entryData.split(",");

        if (entryFields.length != 1) {
            //exception, string may have only 1 value
            throw new InvalidEntryException();
        }

        String stringField = entryFields[0].trim();
        Pattern fieldValuePattern = Pattern.compile("(\\w+)\\s*=\\s*(\\S.*)");
        Matcher matcher = fieldValuePattern.matcher(stringField);

        if (!matcher.matches()) {
            //exception, invalid string entry
            throw new InvalidEntryException();
        }

        IBibtexValue value = null;

        try {
            value = ParserUtilities.readStringValuePart(matcher.group(2), bibliography);
        } catch (UnknownStringReferenceException e) {
            //exception, notify
            return;
        }

        if (!(value == null)) {
            bibliography.addValue(matcher.group(1), value);
        }
    }

}
