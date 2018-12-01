package entries.general;

import exceptions.MissingRequiredEntryFieldException;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.required;
import static entries.general.BibtexFieldConstraint.requiredMultiple;

/**
 * Container class used for storing BibTeX entry
 *
 * @author Olgierd Królik
 * @see <a href="http://www.bibtex.org/Format/">BibTeX Description</a>
 */
public abstract class BibtexEntry implements BibtexVisitableElement {

    /**
     * Unique id of entry
     */
    private final String id;

    /**
     * Static map used to store maps of constraints of fields for every class.
     * Keys in this map are classes which extend {@link BibtexEntry} class.
     * Values are maps of constraints in which keys are field names and values are {@link BibtexFieldConstraint}.
     * This map is filled when classes which extend {@link BibtexEntry} are being loaded.
     *
     * @see BibtexFieldConstraint
     */
    protected static Map<Class<? extends BibtexEntry>, Map<String, BibtexFieldConstraint>> classConstraints = new HashMap<>();


    /**
     * Constructor used to create entry
     *
     * @param id unique id of an entry
     */
    public BibtexEntry(String id) {
        this.id = id;
    }

    /**
     * @return id of an this entry
     */
    public String getId() {
        return id;
    }

    /**
     * Method used to get map of constraints for class on which this method was called
     *
     * @return map of field constraints
     */
    public Map<String, BibtexFieldConstraint> getConstraintMap() {
        return classConstraints.get(this.getClass());
    }

    /**
     * Method used to validate whether entry on which this method was called is valid.
     * Entry can be invalid when it is not satisfying field constraints - when it is missing required fields.
     * If entry is invalid then it should not be created and such incident should be reported by throwing an exception.
     *
     * @throws MissingRequiredEntryFieldException when entry is invalid
     * @throws IllegalAccessException             unlikely to happen, because every field for which we use reflection to get access into is public
     * @see MissingRequiredEntryFieldException
     */
    public void validateEntry() throws MissingRequiredEntryFieldException, IllegalAccessException {
        Class entryClass = this.getClass();
        Map<String, BibtexFieldConstraint> constraintMap = this.getConstraintMap();

        String fieldName;
        BibtexFieldConstraint constraint;
        IBibtexValue value;

        for (Field f : entryClass.getDeclaredFields()) {
            if (f.getType().equals(IBibtexValue.class)) {

                fieldName = f.getName();
                constraint = constraintMap.get(fieldName);
                value = (IBibtexValue) f.get(this);

                if ((value == null) && (constraint.equals(required) || constraint.equals(requiredMultiple))) {
                    throw new MissingRequiredEntryFieldException(this.getId(), f.getName());
                }
            }
        }
    }

    /**
     * Method used for Visitor design pattern.
     *
     * @param visitor visitor which visits this entry
     * @see BibtexVisitor
     * @see BibtexVisitableElement
     * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Description of Visitor design pattern</a>
     */
    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
