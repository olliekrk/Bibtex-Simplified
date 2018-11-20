package parser;

import exceptions.InvalidEntryException;
import exceptions.MissingClosingBracketException;
import exceptions.ParsingException;
import exceptions.UnknownStringReferenceException;
import values.ComplexValue;
import values.IBibtexValue;
import values.NumberValue;
import values.StringValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ParserUtilities {

    //scans data till it encounters }
    public static String scanData(Scanner scanner) throws MissingClosingBracketException {

        Pattern entryDataPattern = Pattern.compile("([^}]+)}");
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

    // method to convert values stored as strings name="value" to map
    public static Map<String, IBibtexValue> splitIntoValues(String[] entryFields, BibtexBibliography bibliography) throws ParsingException, InvalidEntryException, UnknownStringReferenceException {
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
            String fieldName = matcher.group(1);
            IBibtexValue fieldValue = readFieldValue(matcher.group(2), bibliography);

            values.put(fieldName, fieldValue);
        }
        return values;
    }

    //method to get IBibtexValue from its string representation
    public static IBibtexValue readStringValuePart(String valuePart, BibtexBibliography bibliography) throws ParsingException {

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
        return new ComplexValue(values);
    }

    //method to get single IBibtexValue from its string representation
    private static IBibtexValue readFieldValue(String part, BibtexBibliography bibliography) throws ParsingException {

        part = part.trim();

        //if this is a number
        if (part.matches("\\d+"))
            return new NumberValue(Integer.parseInt(part));

        //if this is a simple string
        if (part.charAt(0) == '"' && part.charAt(part.length() - 1) == '"') {
            //return value without quotation marks " "
            return new StringValue(part.substring(1, part.length() - 1));
        }

        //if this is simple @string reference
        if (part.matches("[a-zA-Z]\\w+")) {
            if (!bibliography.containsValue(part)) {
                throw new UnknownStringReferenceException();
            }
            return bibliography.getValue(part);
        }

        if (part.contains("#")) {
            return readStringValuePart(part, bibliography);
        }

        //else exception, unknown field
        throw new ParsingException();
    }
}
