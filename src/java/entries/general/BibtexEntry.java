package entries.general;

import java.util.HashMap;
import java.util.Map;

public abstract class BibtexEntry implements BibtexVisitableElement {

    private final String id;

    //static map to decide whether that entry field is required / there are possible multiple values
    protected static Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();

    public BibtexEntry(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public Map<String, BibtexFieldConstraint> getConstraintMap() {
        return constraintMap;
    }

    public boolean validateEntry() {
        return true;
    }

    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
