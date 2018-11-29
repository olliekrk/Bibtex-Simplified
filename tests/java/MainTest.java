import org.junit.Ignore;
import org.junit.Test;

public class MainTest {

    private static final String path = "C:\\Users\\Olgierd\\Desktop\\OlgierdKrolik_PO1\\tests\\java\\testData.bib";

    @Test
    public void onlyPathTest() {
        Main.main(new String[]{"-f", path});
    }

    @Test
    public void filtersAndSignTest() {
        Main.main(new String[]{"-f", path, "-s", "&", "-n", "Knuth", "Aamport", "Kvnth", "Lincoll", "-c", "inbook", "article", "booklet", "book"});
    }

    @Test
    public void helpTest() {
        Main.main(new String[]{"-h", "-f", path});
    }

    @Ignore
    @Test
    public void inappropriateUseTest() {
        Main.main(new String[]{"-h", "-f", path, "-g", "-k"});
    }
}