package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class BookletEntry extends BibtexEntry {

    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(multiple = true)
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
