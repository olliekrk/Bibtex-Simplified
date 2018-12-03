package entries.general;

import entries.*;
import exceptions.UnknownEntryTypeException;

/**
 * Enumeration used for linking BibTeX format entry types with their corresponding classes.
 */
public enum BibtexEntryType {

    article(ArticleEntry.class),
    book(BookEntry.class),
    booklet(BookletEntry.class),
    inbook(InbookEntry.class),
    incollection(IncollectionEntry.class),
    inproceedings(InproceedingsEntry.class),
    conference(InproceedingsEntry.class),
    manual(ManualEntry.class),
    mastersthesis(MastersthesisEntry.class),
    misc(MiscEntry.class),
    phdthesis(PhdthesisEntry.class),
    techreport(TechreportEntry.class),
    unpublished(UnpublishedEntry.class);

    /**
     * Class linked with given entry type.
     */
    private Class<? extends BibtexEntry> entryClass;

    /**
     * Constructor used to create enum values and to assign entry class to them by default.
     *
     * @param entryClass class extending {@link BibtexEntry} linked to given entry type
     */
    BibtexEntryType(Class<? extends BibtexEntry> entryClass) {
        this.entryClass = entryClass;
    }

    /**
     * Getter for entryClass field.
     *
     * @return entry class linked to of given enum
     */
    private Class<? extends BibtexEntry> getEntryClass() {
        return entryClass;
    }

    /**
     * Method used to find class of BibTeX entry by its name (type).
     *
     * @param entryType string representation of BibTeX entry type
     * @return class extending {@link BibtexEntry} linked with given entry type
     * @throws UnknownEntryTypeException when there is no entry class linked with entry type given as an parameter
     */
    public static Class<? extends BibtexEntry> findEntryClass(String entryType) throws UnknownEntryTypeException {
        for (BibtexEntryType t : BibtexEntryType.values()) {
            if (entryType.toLowerCase().equals(t.name())) return t.getEntryClass();
        }
        throw new UnknownEntryTypeException(entryType);
    }

    /**
     * Method used to find BibTeX entry type name linked with given entry class.
     *
     * @param entryClass class linked with an entry which name has to be found
     * @return name of BibTex entry type as string if there was found a known entry type linked with that entry class,
     * otherwise returns "unknown" string
     */
    public static String findEntryType(Class<? extends BibtexEntry> entryClass) {
        for (BibtexEntryType t : BibtexEntryType.values()) {
            if (t.getEntryClass().equals(entryClass)) return t.name();
        }
        return "unknown";
    }
}
