package parser;

import exceptions.InvalidEntryException;
import exceptions.MissingClosingBracketException;
import exceptions.ParsingException;
import exceptions.UnknownStringReferenceException;
import values.ConcatenatedValue;
import values.IBibtexValue;
import values.NumberValue;
import values.StringValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtilities {

    //scans data till it encounters }
    public static String scanData(Scanner scanner) throws MissingClosingBracketException {

        Pattern entryDataPattern = Pattern.compile("(\\w+)}");
        if (scanner.findWithinHorizon(entryDataPattern, 0) != null)
            return scanner.match().group(1);
        else
            throw new MissingClosingBracketException();

        /*int leftBrackets = 1;
        int rightBrackets = 0;

        StringBuilder data = new StringBuilder("");
        while (leftBrackets != rightBrackets) {
            char a = scanner.findWithinHorizon(".", 0).charAt(0);
            if (a == '{') leftBrackets++;
            if (a == '}') rightBrackets++;
            data.append(a);
        }
        String scannedData = data.toString();
        return scannedData.substring(0, scannedData.length() - 1);*/
    }

    //necessary only if we assume entryData may be invalid
    public static String[] splitUsingDelimiter(String entryData, char delimiter) throws ParsingException {

        List<String> result = new ArrayList<>();
        Stack<Character> brackets = new Stack<>();

        int fromIndex = 0;
        for (int i = 0; i < entryData.length(); i++) {
            char c = entryData.charAt(i);
            switch (c) {
                case '"':
                    if (brackets.empty()) {
                        brackets.push(c);
                    } else if (brackets.peek() == c) {
                        brackets.pop();
                    }
                    break;
                case '{':
                    brackets.push(c);
                    break;
                case '}':
                    if (brackets.empty() || brackets.peek() == '"') {
                        throw new ParsingException();
                    } else if (brackets.peek() == '{') {
                        brackets.pop();
                    }
                    break;
            }
            if (c == delimiter) {
                if (brackets.empty()) {
                    result.add(entryData.substring(fromIndex, i).trim());
                    fromIndex = i + 1;
                }
            }
        }
        if (fromIndex < entryData.length())
            result.add(entryData.substring(fromIndex, entryData.length()).trim());
        return (String[]) result.toArray();
    }

    // method to convert values stored as strings name="value" to map
    public static Map<String, IBibtexValue> splitIntoValues(String entryType, String[] entryFields, BibtexBibliography bibliography) throws ParsingException, InvalidEntryException, UnknownStringReferenceException {
        Map<String, IBibtexValue> values = new HashMap<>();

        Pattern fieldValuePattern = Pattern.compile("(\\w+)\\s*=\\s*(\\S.*)");

        //we omit index 0 because it contains entryId which is not value
        for (int i = 1; i < entryFields.length; i++) {
            String currentField = entryFields[i];
            Matcher matcher = fieldValuePattern.matcher(currentField);
            if (!matcher.matches()) {
                //exception, field in entry is invalid
                throw new InvalidEntryException();
            }
            values.put(matcher.group(1), readFieldValuePart(matcher.group(2), bibliography));
        }
        return values;
    }

    //method to get IBibtexValue from its string representation
    public static IBibtexValue readFieldValuePart(String valuePart, BibtexBibliography bibliography) throws ParsingException, InvalidEntryException, UnknownStringReferenceException {
        //String[] parts = splitUsingDelimiter(valuePart, '#');
        String[] parts = valuePart.split("#");
        IBibtexValue[] values = new IBibtexValue[parts.length];

        int index = 0;
        for (String part : parts) {
            values[index++] = readFieldValue(part, bibliography);
        }
        if (values.length == 0) {
            //exception, empty field
            throw new InvalidEntryException();
        }
        if (values.length == 1) {
            return values[0];
        }
        return new ConcatenatedValue(values);
    }

    //method to get single IBibtexValue from its string representation
    private static IBibtexValue readFieldValue(String part, BibtexBibliography bibliography) throws ParsingException, UnknownStringReferenceException {

        part = part.trim();

        //if this is @string reference
        if (part.matches("[a-zA-Z]\\w+")) {
            if (!bibliography.containsValue(part))
                throw new UnknownStringReferenceException();
            return bibliography.getValue(part);
        }

        //if this is a number
        if (part.matches("\\d+"))
            return new NumberValue(Integer.parseInt(part));

        //if this is a string
        if (part.charAt(0) == '"') {
            return new StringValue(part.substring(1, part.length() - 1));
        }

        //else exception, unknown field
        throw new ParsingException();
    }
}
