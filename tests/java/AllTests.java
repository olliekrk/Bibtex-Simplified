import entries.general.BibtexEntryFactoryTest;
import entries.general.BibtexEntryTest;
import entries.general.BibtexEntryTypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import parser.BibtexBibliographyTest;
import parser.BibtexParserTest;
import parser.ParserUtilitiesTest;
import printer.BibtexPrintingVisitorTest;
import values.MultipleValueTest;
import values.PersonValueTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BibtexEntryTest.class,
        BibtexEntryTypeTest.class,
        BibtexEntryFactoryTest.class,
        MultipleValueTest.class,
        PersonValueTest.class,
        BibtexParserTest.class,
        BibtexBibliographyTest.class,
        ParserUtilitiesTest.class,
        BibtexPrintingVisitorTest.class,
        MainTest.class
})
public class AllTests {

}