package entries;

import entries.general.BibtexEntry;
import parser.BibtexFieldConstraints;
import values.IBibtexValue;

public class MiscEntry extends BibtexEntry {

    @BibtexFieldConstraints(multiple = true)
    public IBibtexValue author;
    public IBibtexValue title;
    public IBibtexValue howpublished;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue note;
    public IBibtexValue key;


    public MiscEntry(String id) {
        super(id);
    }
}
