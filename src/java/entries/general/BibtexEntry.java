package entries.general;

import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.required;
import static entries.general.BibtexFieldConstraint.requiredMultiple;

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
        for (Field f : this.getClass().getDeclaredFields()) {
            if (f.getType().equals(IBibtexValue.class)) {
                String fieldName = f.getName();
                BibtexFieldConstraint constraint = constraintMap.get(fieldName);
                IBibtexValue value;
                try {
                    value = (IBibtexValue) f.get(this);
                } catch (IllegalAccessException e) {
                    //exception, unlikely to happen
                    e.printStackTrace();
                    return false;
                }
                if ((value == null) && (constraint.equals(required) || constraint.equals(requiredMultiple))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
