package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class InproceedingsEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue booktitle;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    @BibtexConstraints(multiple = true)
    public IBibtexValue editor;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue series;
    public IBibtexValue pages;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue organization;
    public IBibtexValue publisher;
    public IBibtexValue note;
    public IBibtexValue key;

    public InproceedingsEntry(String id) {
        super(id);
    }

    //public IBibtexValue author, title, booktitle, year, editor, volume, number, series, pages, address, month, organization, publisher, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("booktitle", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
    }
}
