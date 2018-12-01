package values;

import exceptions.InvalidPersonException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Container for parsed data about people - i.e. authors and editors.
 */
public class PersonValue implements IBibtexValue {

    /**
     * Array of patterns used to recognize string representation of a person and to extract
     * first name, middle name / middle initial and last name parts.
     */
    private static final Pattern[] personPatterns = {
            Pattern.compile("([A-Z].*)\\s*\\|\\s*([A-Za-z]\\S*)"),
            Pattern.compile("([A-Z]\\S*)\\s+([A-Za-z]+\\S*)\\s+([A-Z]\\S*)"),
            Pattern.compile("([A-Z]\\S*)\\s+([A-Z]\\S*)")
    };

    private final String firstName;
    private final String middleName;
    private final String lastName;

    /**
     * Constructor method. Checks whether given person data matches any known pattern
     * from array of patterns. If so, creates the object based on this data.
     *
     * @param personData data containing name of the person
     * @throws InvalidPersonException when string data could not be recognized as a person
     */
    PersonValue(String personData) throws InvalidPersonException {
        Matcher m;
        String first = null;
        String mid = null;
        String last = null;
        for (int i = 0; i < personPatterns.length; i++) {
            m = personPatterns[i].matcher(personData);
            if (m.matches()) {
                if (i == 0) {
                    first = m.group(2);
                    last = m.group(1);
                }
                if (i == 1) {
                    first = m.group(1);
                    mid = m.group(2);
                    last = m.group(3);
                }
                if (i == 2) {
                    first = m.group(1);
                    last = m.group(2);
                }
                this.firstName = first == null ? null : first.trim();
                this.middleName = mid == null ? null : mid.trim();
                this.lastName = last == null ? null : last.trim();
                return;
            }
        }
        throw new InvalidPersonException(personData);
    }

    /**
     * Returns last name of a person.
     *
     * @return last name of a person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns whole information about person in given order: first name, middle name (if there is so), last name.
     *
     * @return string information about person in given order
     */
    @Override
    public String getString() {
        String mid = (middleName == null) ? " " : " " + middleName + " ";
        return firstName + mid + lastName;
    }
}
