package entries.general;

public abstract class BibtexEntry {

    private final String id;

    public BibtexEntry( String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
