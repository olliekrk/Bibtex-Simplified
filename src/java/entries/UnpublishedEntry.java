package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class UnpublishedEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue note;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue key;

    public UnpublishedEntry(String id) {
        super(id);
    }
}
