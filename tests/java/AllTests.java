import entries.general.BibtexEntryFactoryTest;
import entries.general.BibtexEntryTest;
import entries.general.BibtexEntryTypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import values.MultipleValueTest;
import values.PersonValueTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BibtexEntryTest.class,
        BibtexEntryTypeTest.class,
        BibtexEntryFactoryTest.class,
        MultipleValueTest.class,
        PersonValueTest.class
})
public class AllTests {

}