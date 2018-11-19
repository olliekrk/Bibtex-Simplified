package entries;

import entries.general.BibtexEntry;
import parser.BibtexFieldConstraints;
import values.IBibtexValue;

public class IncollectionEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue booktitle;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue publisher;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue year;
    @BibtexFieldConstraints(multiple = true)
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
}
