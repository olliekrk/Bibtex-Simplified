package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

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

    //public IBibtexValue author,title,journal,year,volume,number,pages,month,note,key;

    public ArticleEntry(String id) {
        super(id);
    }

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("journal", required);
        constraintMap.put("year", required);
    }

}
