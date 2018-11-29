package parser;

import entries.MiscEntry;
import entries.general.BibtexEntry;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import values.IBibtexValue;
import values.StringValue;

import static org.junit.Assert.*;

public class BibtexBibliographyTest {

    private static BibtexEntry entry1, entry2, entry3;
    private static IBibtexValue value1, value2;
    private BibtexBibliography bibtex;

    @BeforeClass
    public static void prepareData() {
        entry1 = new MiscEntry("entry1");
        entry2 = new MiscEntry("entry2");
        entry3 = new MiscEntry("entry3");
        value1 = new StringValue("1");
        value2 = new StringValue("2");
    }

    @Before
    public void prepareBibtex() {
        bibtex = new BibtexBibliography();
    }

    @Test
    public void addEntryTest() {
        int initialSize = bibtex.getAllEntries().size();
        bibtex.addEntry(entry1);
        bibtex.addEntry(entry2);
        bibtex.addEntry(entry3);
        assertEquals(initialSize + 3, bibtex.getAllEntries().size());
    }

    @Test
    public void overrideExistingEntryTest() {
        bibtex.addEntry(entry1);
        int initialSize = bibtex.getAllEntries().size();
        BibtexEntry other1 = new MiscEntry("entry1");
        BibtexEntry previous1 = bibtex.addEntry(other1);
        assertEquals(initialSize, bibtex.getAllEntries().size());
        assertEquals(entry1, previous1);
        assertNotEquals(other1, previous1);
    }

    @Test
    public void addValueTest() {
        int initialValues = bibtex.getAllValues().size();
        bibtex.addValue("value1", value1);
        bibtex.addValue("value2", value2);
        assertEquals(initialValues + 2, bibtex.getAllValues().size());
    }

    @Test
    public void containsEntryTest() {
        bibtex.addEntry(entry1);
        bibtex.addEntry(entry2);
        bibtex.addEntry(entry3);
        assertTrue(bibtex.containsEntry("entry1"));
        assertTrue(bibtex.containsEntry("entry2"));
        assertTrue(bibtex.containsEntry("entry3"));
        assertFalse(bibtex.containsEntry("entry4"));
    }

    @Test
    public void containsValueTest() {
        bibtex.addValue("value1", value1);
        assertTrue(bibtex.containsValue("value1"));
        assertFalse(bibtex.containsValue("value2"));
    }

    @Test
    public void getEntryTest() {
        bibtex.addEntry(entry1);
        BibtexEntry get1 = bibtex.getEntry(entry1.getId());
        assertEquals(entry1, get1);
    }

    @Test
    public void getValueTest() {
        bibtex.addValue("value1", value1);
        IBibtexValue get1 = bibtex.getValue("value1");
        assertEquals(value1, get1);
    }

    @Test
    public void getAllEntriesTest() {
        bibtex.addEntry(entry1);
        bibtex.addEntry(entry2);
        bibtex.addEntry(entry3);
        assertTrue(bibtex.getAllEntries().size() == 3);
    }

    @Test
    public void getAllValuesTest() {
        int initialSize = bibtex.getAllValues().size();
        bibtex.addValue("value1", value1);
        bibtex.addValue("value2", value2);
        assertTrue(bibtex.getAllValues().size() == initialSize + 2);
    }
}