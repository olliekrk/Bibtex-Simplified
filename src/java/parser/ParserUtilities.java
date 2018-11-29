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

public abstract class ParserUtilities {

    //scans data till it encounters }
    public static String scanData(Scanner scanner) throws MissingClosingBracketException {

        Pattern entryDataPattern = Pattern.compile("([^}]*)}");
        if (scanner.findWithinHorizon(entryDataPattern, 0) != null)
            return scanner.match().group(1);
        else
            throw new MissingClosingBracketException();
    }

    //method to split string data into string fields
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

    // method to convert values stored as strings name="value" to map
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
            String fieldName = matcher.group(1);
            IBibtexValue fieldValue = readFieldValue(matcher.group(2), bibliography);
            values.put(fieldName, fieldValue);
        }
        return values;
    }

    //method to get single IBibtexValue from its string representation
    public static IBibtexValue readFieldValue(String part, BibtexBibliography bibliography) throws ParsingException {

        part = part.trim();

        //if this is a number
        if (part.matches("\\d+"))
            return new NumberValue(Integer.parseInt(part));

        return readStrings(part, bibliography);
    }

    //method to get IBibtexValue from its string representation
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

        if (finalValue.length() == 0) throw new InvalidEntryException("string", "unknown", part);
        return new StringValue(finalValue);
    }
}
