package parser;

import exceptions.InvalidEntryException;
import exceptions.MissingClosingBracketException;
import exceptions.ParsingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import values.IBibtexValue;

import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserUtilitiesTest {

    private final String validData =
            "inbook1,\n" +
                    "    author = \"a\",\n" +
                    "    title = \"t\",\n" +
                    "    publisher=\"p\",\n" +
                    "    pages=\"100\",\n" +
                    "    year = 2000,\n" +
                    "}";

    private final String invalidData =
            "inbook1,\n" +
                    "    author = \"a\",\n" +
                    "    title = \"t\",\n" +
                    "    publisher=\"p\",\n" +
                    "    pages=\"100\",\n" +
                    "    year = 2000,\n" +
                    "";

    private BibtexBibliography bibtex;

    @Before
    public void prepareBibtex() {
        bibtex = new BibtexBibliography();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void scanValidDataTest() throws MissingClosingBracketException {
        String data = ParserUtilities.scanData(new Scanner(validData));
        assertTrue(data.length() > 0);
    }

    @Test(expected = MissingClosingBracketException.class)
    public void scanMissingClosingBracketDataTest() throws MissingClosingBracketException {
        String data = ParserUtilities.scanData(new Scanner(invalidData));
    }

    @Test
    public void splitIntoFieldsTest() throws InvalidEntryException {
        String dataWithCommas =
                "inbook1,\n" +
                        "    author = \"a\",\n" +
                        "    title = \",t,t,t,t,\",\n" +
                        "    publisher=\"p\",\n" +
                        "    pages=\"100\",\n" +
                        "    year = 2000,\n";

        String[] fields1 = ParserUtilities.splitIntoFields("inbook", validData.substring(0, validData.length() - 1));
        String[] fields2 = ParserUtilities.splitIntoFields("inbook", dataWithCommas);
        assertEquals(6, fields1.length);
        assertEquals(6, fields2.length);
    }

    @Test
    public void splitIntoFieldsExceptionTest() throws InvalidEntryException {
        String dataWithEmptyFields = ",, ,  ,   ,    ,";
        exception.expect(InvalidEntryException.class);
        String[] result = ParserUtilities.splitIntoFields("inbook", dataWithEmptyFields);
    }

    @Test
    public void splitIntoValuesTest() throws ParsingException {
        String data = validData.substring(0, validData.length() - 1);
        String[] fields = ParserUtilities.splitIntoFields("inbook", data);
        Map<String, IBibtexValue> values = ParserUtilities.splitIntoValues(fields, bibtex);
        assertEquals("a", values.get("author").getString());
        assertEquals("t", values.get("title").getString());
        assertEquals("2000", values.get("year").getString());
        assertEquals(5, values.size());
    }

    @Test
    public void readFieldValueTest() throws ParsingException {
        IBibtexValue intValue = ParserUtilities.readFieldValue("   12345   ", bibtex);
        assertEquals("12345", intValue.getString());

        IBibtexValue stringValue1 = ParserUtilities.readFieldValue("\"A\"   #\"B\" # \"C\"#   \"D\"", bibtex);
        assertEquals("ABCD", stringValue1.getString());

        IBibtexValue stringValue2 = ParserUtilities.readFieldValue("   \"ABC\"   ", bibtex);
        assertEquals("ABC", stringValue2.getString());
    }

    @Test
    public void unknownStringReferenceTest() throws ParsingException {
        exception.expect(ParsingException.class);
        IBibtexValue value = ParserUtilities.readFieldValue(" ref1 # ref2", bibtex);
    }

    @Test
    public void invalidFieldValueTest() throws ParsingException {
        exception.expect(ParsingException.class);
        exception.expectMessage(new ParsingException("Parsed string value of:  is empty!").getMessage());
        IBibtexValue value = ParserUtilities.readFieldValue("", bibtex);
    }
}