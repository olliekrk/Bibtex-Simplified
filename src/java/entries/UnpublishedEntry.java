package entries;

import entries.general.BibtexEntry;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;

public class UnpublishedEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue note;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue key;

    public UnpublishedEntry(String id) {
        super("unpublished", id);
    }
}
