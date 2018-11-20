package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class InbookEntry extends BibtexEntry {


    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue editor;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue chapter;
    @BibtexConstraints(required = true)
    public IBibtexValue pages;
    @BibtexConstraints(required = true)
    public IBibtexValue publisher;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue series;
    public IBibtexValue type;
    public IBibtexValue address;
    public IBibtexValue edition;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public InbookEntry(String id) {
        super(id);
    }
}
