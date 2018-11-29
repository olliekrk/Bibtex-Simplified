package entries.general;

import entries.*;
import exceptions.UnknownEntryTypeException;
import org.junit.Test;

import static entries.general.BibtexEntryType.findEntryClass;
import static entries.general.BibtexEntryType.findEntryType;
import static org.junit.Assert.assertEquals;

public class BibtexEntryTypeTest {

    private final String[] entryTypes = {
            "article",
            "book",
            "booklet",
            "inbook",
            "incollection",
            "inproceedings",
            "manual",
            "mastersthesis",
            "misc",
            "phdthesis",
            "techreport",
            "unpublished"
    };
    private final Class[] entryClasses = {
            ArticleEntry.class,
            BookEntry.class,
            BookletEntry.class,
            InbookEntry.class,
            IncollectionEntry.class,
            InproceedingsEntry.class,
            ManualEntry.class,
            MastersthesisEntry.class,
            MiscEntry.class,
            PhdthesisEntry.class,
            TechreportEntry.class,
            UnpublishedEntry.class
    };

    @Test
    public void findEntryClassTest() {
        for (int i = 0; i < entryClasses.length; i++) {
            try {
                assertEquals("Method does not work for: " + entryClasses[i].getName(), findEntryClass(entryTypes[i]), entryClasses[i]);
            } catch (UnknownEntryTypeException e) {
                e.getMessage();
            }
        }
    }

    @Test(expected = UnknownEntryTypeException.class)
    public void findEntryClassBlankTypeTest() throws UnknownEntryTypeException {
        findEntryClass("");
    }

    @Test(expected = UnknownEntryTypeException.class)
    public void findEntryClassUnknownTypeTest() throws UnknownEntryTypeException {
        findEntryClass("unknowntype1");
    }

    @Test
    public void findEntryTypeTest() {
        for (int i = 0; i < entryClasses.length; i++)
            assertEquals("Method does not work for: " + entryClasses[i].getName(), findEntryType(entryClasses[i]), entryTypes[i]);
    }

    @Test
    public void findEntryTypeUnknownTest() {
        assertEquals("Method does not work for: " + BibtexEntry.class.getName(), findEntryType(BibtexEntry.class), "unknown");
    }
}