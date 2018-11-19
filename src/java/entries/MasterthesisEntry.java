package entries;

import entries.general.BibtexEntry;
import parser.BibtexFieldConstraints;
import values.IBibtexValue;

public class MasterthesisEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue school;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue type;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public MasterthesisEntry(String id) {
        super(id);
    }
}
