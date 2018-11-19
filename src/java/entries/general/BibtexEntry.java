package entries.general;

public abstract class BibtexEntry implements BibtexVisitableElement {

    private final String id;

    public BibtexEntry(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
