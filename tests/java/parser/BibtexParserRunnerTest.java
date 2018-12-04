package parser;

import org.junit.Ignore;
import org.junit.Test;

public class BibtexParserRunnerTest {

    private static final String path = "C:\\Users\\Olgierd\\Desktop\\OlgierdKrolik_PO1\\tests\\java\\testData.bib";

    @Test
    public void onlyPathTest() {
        BibtexParserRunner.run(new String[]{"-f", path});
    }

    @Test
    public void filtersAndSignTest() {
        BibtexParserRunner.run(new String[]{"-f", path, "-s", "&", "-n", "Knuth", "Aamport", "Kvnth", "Lincoll", "-c", "inbook", "article", "booklet", "book"});
    }

    @Test
    public void helpTest() {
        BibtexParserRunner.run(new String[]{"-h", "-f", path});
    }

    @Ignore
    @Test
    public void inappropriateUseTest() {
        BibtexParserRunner.run(new String[]{"-h", "-f", path, "-g", "-k"});
    }
}