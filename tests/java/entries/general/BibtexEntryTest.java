package entries.general;

import entries.InbookEntry;
import exceptions.MissingRequiredEntryFieldException;
import org.junit.Before;
import org.junit.Test;
import values.NumberValue;
import values.StringValue;

public class BibtexEntryTest {

    private static InbookEntry entry;

    @Before
    public void prepareEntry() {
        InbookEntry inbook = new InbookEntry("inbook1");
        inbook.author = new StringValue("John Lenon and Yoko Ono");
        inbook.editor = new StringValue("The Beatles");
        inbook.title = new StringValue("How to play a guitar guide");
        inbook.chapter = new StringValue("Prelude");
        inbook.pages = new NumberValue(199);
        inbook.publisher = new StringValue("PWN");
        inbook.year = new NumberValue(2018);
        inbook.address = new StringValue("Wadowice, Polska Południowa");
        entry = inbook;
    }

    @Test(expected = MissingRequiredEntryFieldException.class)
    public void validateEntryWhenRequiredFieldisMissingTest() throws MissingRequiredEntryFieldException, IllegalAccessException {
        entry.title = null;
        entry.validateEntry();
    }

    @Test(expected = MissingRequiredEntryFieldException.class)
    public void validateEntryWhenBothAlternativeFieldsAreMissingTest() throws MissingRequiredEntryFieldException, IllegalAccessException {
        entry.author = null;
        entry.editor = null;
        entry.validateEntry();
    }
}