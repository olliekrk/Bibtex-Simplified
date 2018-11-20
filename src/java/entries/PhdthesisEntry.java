package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;


public class PhdthesisEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue school;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue type;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public PhdthesisEntry(String id) {
        super(id);
    }
}
