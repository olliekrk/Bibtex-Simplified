package entries;

import entries.general.BibtexEntry;
import parser.BibtexFieldConstraints;
import values.IBibtexValue;

public class BookletEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(multiple = true)
    public IBibtexValue author;
    public IBibtexValue howpublished;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue note;
    public IBibtexValue key;

    public BookletEntry(String id) {
        super(id);
    }
}
