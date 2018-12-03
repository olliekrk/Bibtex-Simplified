package parser;

import exceptions.InvalidEntryException;
import exceptions.ParsingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class BibtexParserTest {

    private BibtexBibliography bib = new BibtexBibliography();
    private int initialSize = bib.getAllValues().size();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void readStringTest() throws ParsingException {
        BibtexParser.readString("hakuna = \"matata\"", bib);
        assertTrue(bib.containsValue("hakuna"));
        assertEquals("matata", bib.getValue("hakuna").getString());

        BibtexParser.readString("data_urodzin=\"12 - \"#mar#\" - 1999\"", bib);
        assertTrue(bib.containsValue("data_urodzin"));
        assertEquals("12 - March - 1999", bib.getValue("data_urodzin").getString());

        BibtexParser.readString("zimowe_miesiace =dec# \" \"# jan #\" \" # feb", bib);
        assertTrue(bib.containsValue("zimowe_miesiace"));
        assertEquals("December January February", bib.getValue("zimowe_miesiace").getString());

        BibtexParser.readString("STOC-key = \"OX\"", bib);
        assertTrue(bib.containsValue("STOC-key"));
        assertEquals("OX", bib.getValue("STOC-key").getString());

        BibtexParser.readString("string=\"abc\"", bib);
        assertTrue(bib.containsValue("string"));
        assertEquals("abc", bib.getValue("string").getString());

        BibtexParser.readString("favourite-month = \"Favourite \" # feb # \" is.\"\n", bib);
        assertTrue(bib.containsValue("favourite-month"));
        assertEquals("Favourite February is.", bib.getValue("favourite-month").getString());

        assertEquals(initialSize + 6, bib.getAllValues().size());
    }

    @Test
    public void readEntryStringTest() throws ParsingException {
        BibtexParser.readEntry("string", "a1=\"abcdefghi\"", bib);
        assertTrue(bib.containsValue("a1"));
        assertFalse(bib.containsEntry("a1"));
    }

    @Test
    public void readEntryInvalidStringTest() throws ParsingException {
        exception.expect(InvalidEntryException.class);
        exception.expectMessage(new InvalidEntryException("string","unknown","a2").getMessage());
        BibtexParser.readEntry("string", "a2", bib);
        assertFalse(bib.containsValue("a2"));
    }

    @Test
    public void readEntryCommentAndPreambleTest() throws ParsingException {
        int initialSize = bib.getAllEntries().size();
        BibtexParser.readEntry("comment", "text123", bib);
        BibtexParser.readEntry("preamble", "text123", bib);
        assertEquals(initialSize, bib.getAllEntries().size());
    }

    @Test
    public void invalidEntryIdDetectionTest() throws ParsingException {
        exception.expect(InvalidEntryException.class);
        String type = "misc";
        String id = "-%$!^&";
        exception.expectMessage("Entry of type: @" + type + " has invalid ID: " + id + " and it was impossible to parse it.");
        BibtexParser.readEntry(type, id + ", author = Jessica J. Marks", bib);
    }

    @Test
    public void readEntryCorrectDataTest() throws ParsingException {
        String type = "article";
        String data = "article-full,\n" +
                "   author = \"Leslie A. Aamport\",\n" +
                "   title = \"The Gnats and Gnus Document Preparation System\",\n" +
                "   journal = \"G-Animal's Journal\",\n" +
                "   year = 1986,\n" +
                "   volume = 41,\n" +
                "   number = 7,\n" +
                "   pages = \"73+\",\n" +
                "   month = jul,\n" +
                "   note = \"This is a full ARTICLE entry\",\n";
        int initialSize = bib.getAllEntries().size();
        BibtexParser.readEntry(type, data, bib);
        assertTrue(bib.containsEntry("article-full"));
        assertFalse(bib.containsValue("article-full"));
        assertEquals(initialSize + 1, bib.getAllEntries().size());
    }

    @Test
    public void parseFileTest() throws FileNotFoundException {
        String path = "C:\\Users\\Olgierd\\Desktop\\OlgierdKrolik_PO1\\tests\\java\\testData.bib";
        BibtexBibliography bib2 = BibtexParser.parseFile(path);
        assertTrue(bib2.getAllEntries().size() > 0);
        assertTrue(bib2.getAllValues().size() > 0);

        path = "C:\\Users\\Olgierd\\Desktop\\non_existing_file.bib";
        exception.expect(FileNotFoundException.class);
        BibtexParser.parseFile(path);
    }
}