package parser;

import exceptions.InvalidEntryException;
import exceptions.MissingClosingBracketException;
import exceptions.ParsingException;
import exceptions.UnknownStringReferenceException;
import values.IBibtexValue;
import values.NumberValue;
import values.StringValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class with all static method used during parsing BibTeX files with {@link BibtexParser}
 */
public abstract class ParserUtilities {

    /**
     * Scans data from a scanner and returns it as a string to the part
     * when the first closing bracket '}' was encountered.
     *
     * @param scanner scanner containing data that has not yet been parsed
     * @return string data fragment which is between '{}' braces
     * @throws MissingClosingBracketException when BibTeX file which is parsed misses closing bracket(s)
     */
    public static String scanData(Scanner scanner) throws MissingClosingBracketException {

        Pattern entryDataPattern = Pattern.compile("([^}]*)}");
        if (scanner.findWithinHorizon(entryDataPattern, 0) != null)
            return scanner.match().group(1);
        else
            throw new MissingClosingBracketException();
    }

    /**
     * Method which splits string data of an entry of given type to a single fields.
     * Fields in BibTeX file are separated by commas.
     *
     * @param entryType type of entry which data is split
     * @param entryData data to be split
     * @return array of string fields representation in a form of: "field_name = field_value"
     * @throws InvalidEntryException when there has been problem with parsing that entry
     */
    public static String[] splitIntoFields(String entryType, String entryData) throws InvalidEntryException {
        List<String> fieldList = new ArrayList<>();
        Scanner scanner = new Scanner(entryData);
        int quotationMarks = 0;
        StringBuilder field = new StringBuilder();
        while (scanner.findWithinHorizon(".", 0) != null) {
            char c = scanner.match().group().charAt(0);
            switch (c) {
                case ',':
                    if (quotationMarks % 2 == 0) {
                        fieldList.add(field.toString().trim());
                        field = new StringBuilder();
                    }
                    break;
                case '"':
                    quotationMarks++;
                default:
                    field.append(c);
                    break;
            }
        }

        if (field.length() != 0) fieldList.add(field.toString());

        String[] result = fieldList.stream()
                .map(String::trim)
                .filter(a -> a.length() > 0)
                .toArray(String[]::new);

        if (result.length == 0)
            throw new InvalidEntryException(entryType, 0);

        return result;
    }

    /**
     * Method which converts entry fields stored in array in a form of: "field_name = field_value"
     * to their object representation. Returns map of parsed fields where keys are string fields' names
     * and values are {@link IBibtexValue} containing parsed values of those fields.
     *
     * @param entryFields  array of entry fields
     * @param bibliography bibliography which parser uses to refer to @STRING entries' values
     * @return map of parsed fields with fields' names as keys and fields' values as values
     * @throws ParsingException when there has been problem with converting string fields into object values
     */
    public static Map<String, IBibtexValue> splitIntoValues(String[] entryFields, BibtexBibliography bibliography) throws ParsingException {
        Map<String, IBibtexValue> values = new HashMap<>();

        Pattern fieldValuePattern = Pattern.compile("(\\w+)\\s*=\\s*(\\S.*)");

        String entryId = entryFields[0];
        //we omit index 0 because it contains entryId which is not a value
        for (int i = 1; i < entryFields.length; i++) {
            String currentField = entryFields[i];
            Matcher matcher = fieldValuePattern.matcher(currentField);
            if (!matcher.matches()) {
                //exception, field in entry is invalid
                throw new ParsingException("Field of an entry of ID: " + entryId + " could not be parsed: " + currentField + "");
            }
            String fieldName = matcher.group(1).trim().toLowerCase();

            //cross-referencing
            if (fieldName.equals("crossref")) {
                String refersTo = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1).toLowerCase();
                if (bibliography.containsEntry(refersTo))
                    values.put(fieldName, new StringValue(refersTo));
            } else {
                IBibtexValue fieldValue = readFieldValue(matcher.group(2), bibliography);
                values.put(fieldName, fieldValue);
            }
        }
        return values;
    }

    /**
     * Method which converts string representation of a entry's single field value to its object form.
     * Returns result of that conversion.
     *
     * @param part         data to be parsed
     * @param bibliography bibliography which parser uses to refer to @STRING entries' values
     * @return {@link IBibtexValue} created from given data
     * @throws ParsingException when there was a problem during parsing given data
     */
    public static IBibtexValue readFieldValue(String part, BibtexBibliography bibliography) throws ParsingException {

        part = part.trim();

        //if this is a number
        if (part.matches("\\d+"))
            return new NumberValue(Integer.parseInt(part));

        return readStrings(part, bibliography);
    }

    /**
     * Method which converts string representation of a entry's single field string value to its object form.
     * Returns result of that conversion.
     *
     * @param part         data to be parsed
     * @param bibliography bibliography which parser uses to refer to @STRING entries' values
     * @return {@link IBibtexValue} created from given data
     * @throws UnknownStringReferenceException when given data contained reference to an unknown @STRING entry
     * @throws InvalidEntryException           when there was a problem with parsing given data
     */
    public static IBibtexValue readStrings(String part, BibtexBibliography bibliography) throws ParsingException {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        Scanner scanner = new Scanner(part);
        int quotationMarks = 0;
        while (scanner.findWithinHorizon(".", 0) != null) {
            char c = scanner.match().group().charAt(0);
            switch (c) {
                case '#':
                    if (quotationMarks % 2 == 0) {
                        String val = value.toString().trim();
                        if (val.length() != 0) {
                            values.add(val);
                        }
                        value = new StringBuilder();
                    }
                    break;
                case '"':
                    quotationMarks++;
                default:
                    value.append(c);
            }
        }
        //necessary for last value
        String last = value.toString().trim();
        if (last.length() != 0) {
            values.add(last);
        }

        String finalValue = values.stream()
                .map(a -> {
                    if (a.charAt(0) == '"' && a.charAt(a.length() - 1) == '"')
                        return a.substring(1, a.length() - 1);
                    else {
                        if (bibliography.containsValue(a))
                            return bibliography.getValue(a).getString();
                        else
                            System.out.println(new UnknownStringReferenceException(a).getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .reduce("", ((a, b) -> a + b));

        if (finalValue.length() == 0) throw new ParsingException("Parsed string value of: " + part + " is empty!");
        return new StringValue(finalValue);
    }
}
