import parser.BibtexBibliography;
import parser.BibtexParserRunner;

public class Main {
    public static void main(String[] args) {
        BibtexBibliography bibtex = BibtexParserRunner.run(args);
    }
}
