package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class IncollectionEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue booktitle;
    @BibtexConstraints(required = true)
    public IBibtexValue publisher;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    @BibtexConstraints(multiple = true)
    public IBibtexValue editor;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue series;
    public IBibtexValue type;
    public IBibtexValue chapter;
    public IBibtexValue pages;
    public IBibtexValue address;
    public IBibtexValue edition;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public IncollectionEntry(String id) {
        super(id);
    }

    //public IBibtexValue author, title, booktitle, publisher, year, editor, volume, number, series, type, chapter, pages, address, edition, month, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("booktitle", required);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
    }
}
