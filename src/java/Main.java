import entries.general.BibtexEntry;
import parser.BibtexBibliography;
import parser.BibtexParser;
import printer.BibtexPrintingVisitor;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner("dfaniuofa}neioufwnocianeoicae}asdfasd}xx");
//        Pattern entryDataPattern = Pattern.compile("(\\w+)}");
//        String entryData = scanner.findWithinHorizon(entryDataPattern, 0);
//        System.out.println(scanner.match().group(1));
//
//        try {
//            throw new Exception("aha");
//        } catch (Exception e) {
//            System.out.println(e.getCause());
//        }
//
//        System.out.println("program idzie dalej");
//
//        String lol = "author = \"Daniel\"";
//        String entryx;
//        entryx = "author = \"Daniel D. Lincoll\", title = \"Semigroups of Recurrences\"editor = \"David J. Lipcoll and D. H. Lawrie and A. H. Sameh\",booktitle = \"High Speed Computer and Algorithm Organization\",number = 23,series = \"Fast Computers\",chapter = 3,type = \"Part\",pages = \"179--183\",publisher = \"Academic Press\",address = \"New York\",edition = \"Third\",month = sep,year = 1977,note = \"This is a full INCOLLECTION entry\"";
//        String[] entryFields = entryx.split(",");
//        for(String s: entryFields)
//            System.out.println(s);
//
//        String[] values = {"d", "ccccccc", "bbbbb", "aaa"};
//        String sum = Arrays.stream(values)
//                .reduce("", ((a, b) -> a + b));
//        System.out.println(sum);
//
//        String s = "ddddddddd";
//        String[] ts = s.split(",");
//        for (String t : ts) System.out.println(t);
//
//        IBibtexValue a = new NumberValue(22);
//        System.out.println(a.getClass().toString());
//
//        String part = "012345";
//        System.out.println((part.substring(1, part.length() - 1)));

//        System.out.println(BibtexEntryType.findEntryType(ArticleEntry.class));

//        BibtexEntry e = new ArticleEntry("121211");
//        BibtexPrintingVisitor v = new BibtexPrintingVisitor('~', 20, 20);
//        e.accept(v);

        String data = "@INBOOK{inbookcrossref,crossref = \"whole-set\",author=\"makapaczka\",pages=23,publisher=\"dziwaczka\",editor=\"kwaczka\",title = \"Fundamental Algorithms\",volume = 1,series = \"The Art of Computer Programming\",edition = \"Second\",year = \"1973\", type = \"Section\", chapter = \"1.2\", note = \"This is a cross-referencing INBOOK entry\",}";
        BibtexBibliography b = BibtexParser.parseData(data);
        BibtexPrintingVisitor v = new BibtexPrintingVisitor('+', 40, 40);
        System.out.println(b.getAllEntries().size());
        System.out.println(b.getAllValues().size());
        BibtexEntry entry = b.getEntry("inbookcrossref");
        if(entry!=null) v.visit(entry);

        v.visit(b);
    }
}
