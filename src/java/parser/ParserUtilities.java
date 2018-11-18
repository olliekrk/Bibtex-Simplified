package parser;

import exceptions.ParsingException;
import values.IBibtexValue;
import values.ToSumValue;
import values.NumberValue;
import values.StringValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtilities {
    public static String scanData(Scanner scanner) {
        int leftBrackets = 1;
        int rightBrackets = 0;

        StringBuilder data = new StringBuilder("");
        while (leftBrackets != rightBrackets) {
            char a = scanner.findWithinHorizon(".", 0).charAt(0);
            if (a == '{') leftBrackets++;
            if (a == '}') rightBrackets++;
            data.append(a);
        }
        String scannedData = data.toString();
        return scannedData.substring(0, scannedData.length() - 1);
    }

    //not sure if will work
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


    public static Map<String, IBibtexValue> splitIntoValues(String entryName, String[] entryFields, BibtexBibliography bibliography) throws ParsingException {
        Map<String, IBibtexValue> values = new HashMap<>();

        Pattern fieldValuePattern = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\S.*)\\s*");

        //[0] is entryId
        for (int i = 1; i < entryFields.length; i++) {
            String currentField = entryFields[i];
            Matcher matcher = fieldValuePattern.matcher(currentField);
            if (!matcher.matches()) {
                throw new ParsingException();
                //exception
            }
            values.put(matcher.group(1), readValuesPart(entryName, matcher.group(2), bibliography));
        }
        return values;
    }

    public static IBibtexValue readValuesPart(String entryName, String valuesPart, BibtexBibliography bibliography) throws ParsingException {
        String[] parts = splitUsingDelimiter(valuesPart, '#');
        IBibtexValue[] values = new IBibtexValue[parts.length];
        int index = 0;
        for (String part : parts) {
            values[index++] = readValue(entryName, part, bibliography);
        }
        if (values.length == 0) throw new ParsingException();
        if (values.length == 1) return values[0];
        return new ToSumValue(values);
    }

    private static IBibtexValue readValue(String entryName, String part, BibtexBibliography bibliography) throws ParsingException {
        Pattern anotherVariablePattern = Pattern.compile("\\w+");
        if (anotherVariablePattern.matcher(part).matches()) {
            if (!bibliography.containsValue(part))
                throw new ParsingException();
            return bibliography.getValue(part);
        }
        if (part.matches("\\d+"))
            return new NumberValue(Integer.parseInt(part));
        if (part.charAt(0) == '"') {
            return new StringValue(part.substring(1, part.length() - 1));
        }
        throw new ParsingException();
    }
}
