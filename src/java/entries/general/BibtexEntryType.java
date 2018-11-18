package entries.general;

import entries.*;

public enum BibtexEntryType {

    article(ArticleEntry.class),
    book(BookEntry.class),
    booklet(BookletEntry.class),
    inbook(InbookEntry.class),
    incollection(IncollectionEntry.class),
    inproceedings(InproceedingsEntry.class),
    manual(ManualEntry.class),
    masterthesis(MasterthesisEntry.class),
    misc(MiscEntry.class),
    phdthesis(PhdthesisEntry.class),
    techreport(TechreportEntry.class),
    unpublished(UnpublishedEntry.class);

    private Class entryClass;

    BibtexEntryType(Class entryClass) {
        this.entryClass = entryClass;
    }

    public Class getEntryClass() {
        return entryClass;
    }

    public static Class findEntryClass(String entryName) {
        for (BibtexEntryType t : BibtexEntryType.values()) {
            if (entryName.toLowerCase().equals(t.name())) return t.getEntryClass();
        }
        return null;
    }
}
