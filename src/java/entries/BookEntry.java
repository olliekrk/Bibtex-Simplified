package entries;

import entries.general.BibtexEntry;
import parser.BibtexFieldConstraints;
import values.IBibtexValue;

public class BookEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue editor;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue publisher;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue volume;
    public IBibtexValue series;
    public IBibtexValue address;
    public IBibtexValue edition;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public BookEntry(String id) {
        super(id);
    }
}
