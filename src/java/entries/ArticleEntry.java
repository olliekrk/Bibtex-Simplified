package entries;

import entries.general.BibtexEntry;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;

public class ArticleEntry extends BibtexEntry {
    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required=true)
    public IBibtexValue journal;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue pages;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;


    public ArticleEntry(String id) {
        super("article", id);
    }
}
