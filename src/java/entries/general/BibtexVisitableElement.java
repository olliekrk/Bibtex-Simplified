package entries.general;

public interface BibtexVisitableElement {
    void accept(BibtexVisitor visitor);
}
