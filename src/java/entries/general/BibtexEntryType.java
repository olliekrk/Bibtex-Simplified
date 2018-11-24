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

    private Class<? extends BibtexEntry> entryClass;

    BibtexEntryType(Class<? extends BibtexEntry> entryClass) {
        this.entryClass = entryClass;
    }

    public Class<? extends BibtexEntry> getEntryClass() {
        return entryClass;
    }

    public static Class<? extends BibtexEntry> findEntryClass(String entryName) {
        for (BibtexEntryType t : BibtexEntryType.values()) {
            if (entryName.toLowerCase().equals(t.name())) return t.getEntryClass();
        }
        return null;
    }

    public static String findEntryType(Class<? extends BibtexEntry> entryClass) {
        for (BibtexEntryType t : BibtexEntryType.values()) {
            if (t.getEntryClass().equals(entryClass)) return t.name();
        }
        return "UNKNOWN";
    }
}
