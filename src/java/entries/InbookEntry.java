package entries;

import entries.general.BibtexEntry;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;

public class InbookEntry extends BibtexEntry {


    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue editor;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue chapter;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue pages;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue publisher;
    @BibtexFieldConstraints(required = true)
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
        super("inbook", id);
    }
}
