package entries.general;

public interface BibtexBibliographyVisitableElement {
    void accept(BibtexBibliographyVisitor visitor);
}
