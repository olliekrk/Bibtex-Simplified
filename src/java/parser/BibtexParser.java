package parser;

import entries.general.BibtexEntry;
import exceptions.MissingRequiredEntryFieldException;
import exceptions.ParsingException;
import exceptions.UnknownEntryTypeException;
import values.IBibtexValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibtexParser {
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

    private static BibtexBibliography parseBibtex(Scanner scanner) {

        BibtexBibliography bibliography = new BibtexBibliography();

        final Pattern entryPattern = Pattern.compile("@(\\w+)\\s*\\{");

        while (scanner.findWithinHorizon(entryPattern, 0) != null) {
            String entryName = scanner.match().group(1).toLowerCase();
            String entryData = ParserUtilities.scanData(scanner);//skanuje do znaku }
            try {
                readEntry(entryName, entryData, bibliography);//jesli sie nie uda to exception
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bibliography;
    }

    private static void readEntry(String entryName, String entryData, BibtexBibliography bibliography) throws ParsingException, UnknownEntryTypeException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MissingRequiredEntryFieldException {
        if (entryName.equals("string")) {
            readString(entryName, entryData, bibliography);
            return;
        }
        if (entryName.equals("preamble") || entryName.equals("comment")) {
            return;
        }

        Class entryClass = BibtexEntryType.findEntryClass(entryName);
        if (entryClass == null) {
            throw new UnknownEntryTypeException();
        }

        String[] entryFields = ParserUtilities.splitUsingDelimiter(entryData, ',');
        if (entryFields.length == 0) {
            //pusty entry, exception
            throw new ParsingException();
        }

        String entryId = entryFields[0].trim();
        Pattern entryIdPattern = Pattern.compile("\\w+");
        if (!entryIdPattern.matcher(entryId).matches()) {
            throw new ParsingException();
            //pattern check, exception jesli Id nie pasuje do patternu na id
        }

        Map<String, IBibtexValue> entryValues = ParserUtilities.splitIntoValues(entryName, entryFields, bibliography);

        BibtexEntry entry = (BibtexEntry) entryClass.getConstructor(String.class).newInstance(entryId);

        entry.insertValues(entryValues);

        bibliography.addEntry(entry);
    }

    private static void readString(String entryName, String entryData, BibtexBibliography bibliography) throws ParsingException {
        String[] entryFields = ParserUtilities.splitUsingDelimiter(entryData, ',');
        if (entryFields.length != 1) {
            throw new ParsingException();
        }
        Pattern fieldValuePattern = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\S.*)\\s*");
        Matcher matcher = fieldValuePattern.matcher(entryFields[0]);
        if (!matcher.matches()) {
            throw new ParsingException();
        }

        bibliography.addValue(matcher.group(1), ParserUtilities.readValuesPart(entryName, matcher.group(2), bibliography));
    }

}
