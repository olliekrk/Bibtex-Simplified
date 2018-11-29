package parser;

import exceptions.ParsingException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BibtexParserTest {

    private BibtexBibliography bib = new BibtexBibliography();
    private int valuesSize = bib.getAllValues().size();

    @Test
    public void readStringTest() throws ParsingException {
        BibtexParser.readString("hakuna = \"matata\"", bib);
        assertTrue(bib.containsValue("hakuna"));
        assertEquals("matata", bib.getValue("hakuna").getString());

        BibtexParser.readString("data_urodzin=\"12 - \"#mar", bib);
        assertTrue(bib.containsValue("data_urodzin"));
        assertEquals("12 - March", bib.getValue("data_urodzin").getString());

        BibtexParser.readString("zimowe_miesiace =dec# \" \"# jan #\" \" # feb", bib);
        assertTrue(bib.containsValue("zimowe_miesiace"));
        assertEquals("December January February", bib.getValue("zimowe_miesiace").getString());

        BibtexParser.readString("STOC-key = \"OX\"", bib);
        assertTrue(bib.containsValue("STOC-key"));
        assertEquals("OX", bib.getValue("STOC-key").getString());

        BibtexParser.readString("string=\"abc\"", bib);
        assertTrue(bib.containsValue("string"));
        assertEquals("abc", bib.getValue("string").getString());

        assertEquals(valuesSize+5, bib.getAllValues().size());
    }

}