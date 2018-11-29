package entries.general;

import entries.BookEntry;
import org.junit.Test;
import values.IBibtexValue;
import values.NumberValue;
import values.StringValue;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BibtexEntryFactoryTest {

    private final String id = "book1";
    private final String msg = "Created entry which should not be created!";
    private Map<String, IBibtexValue> values = new HashMap<>();

    @Test
    public void createEntryTest() {
        //only ID test
        assertEquals(msg, null, BibtexEntryFactory.createEntry(BookEntry.class, id, values));

        values.put("title", new StringValue("Seminumerical Algorithms"));
        values.put("volume", new NumberValue(2));
        values.put("series", new StringValue("The Art of Computer Programming"));
        values.put("publisher", new StringValue("Addison|Weasley"));
        values.put("address", new StringValue("Reading, Massachusetts"));
        values.put("edition", new StringValue("Second"));
        values.put("month", new StringValue("10~January"));
        values.put("year", new NumberValue(1981));
        values.put("note", new StringValue("This is a full BOOK entry"));

        //missing both alternative author and editor fields test
        assertEquals(msg, null, BibtexEntryFactory.createEntry(BookEntry.class, id, values));

        values.remove("title");
        values.put("author", new StringValue("Donald E. Knuth"));

        //missing required field test
        assertEquals(msg, null, BibtexEntryFactory.createEntry(BookEntry.class, id, values));

        values.put("title", new StringValue("Seminumerical Algorithms"));

        //valid entry test
        assertNotNull(msg, BibtexEntryFactory.createEntry(BookEntry.class, id, values));
    }
}