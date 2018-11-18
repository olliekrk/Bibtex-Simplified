import java.util.Arrays;

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

        String[] values = {"d", "ccccccc", "bbbbb", "aaa"};
        String sum = Arrays.stream(values)
                .reduce("", ((a, b) -> a + b));
        System.out.println(sum);

        String s = "ddddddddd";
        String[] ts = s.split(",");
        for (String t : ts) System.out.println(t);
    }
}
