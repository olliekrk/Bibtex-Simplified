package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class ArticleEntry extends BibtexEntry {
    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue journal;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue pages;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public ArticleEntry(String id) {
        super(id);
    }
}
