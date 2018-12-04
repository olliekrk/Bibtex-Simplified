package printer;

import entries.general.BibtexVisitor;
import org.junit.BeforeClass;
import org.junit.Test;
import parser.BibtexBibliography;
import parser.BibtexParser;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibtexPrintingVisitorTest {

    private static BibtexBibliography bibtex;
    private static BibtexVisitor visitor;
    private static Map<String, List<String>> filters = new HashMap<>();

    @BeforeClass
    public static void prepareVisitor() throws FileNotFoundException {
        bibtex = BibtexParser.parseFile("C:\\Users\\Olgierd\\Desktop\\OlgierdKrolik_PO1\\tests\\java\\testData.bib");
        visitor = new BibtexPrintingVisitor('*', 40, 80);
        List<String> names = Arrays.asList("Knuth", "Aamport", "Lincoll", "Knvth");
        List<String> categories = Arrays.asList("inbook", "incollection", "book", "article", "booklet");
        filters.put("names", names);
        filters.put("categories", categories);
    }

    @Test
    public void visitBibtexBibliographyTest() {
        visitor.visit(bibtex);
        visitor.visit(bibtex, filters);
    }
}