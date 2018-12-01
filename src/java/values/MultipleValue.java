package values;

import exceptions.InvalidPersonException;
import exceptions.ParsingException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data container used to store BibTeX entries' fields' values which can have multiple values (i.e. people data).
 */
public class MultipleValue implements IBibtexValue {

    /**
     * Array used to store information about people assigned to certain BibTeX entry's field's value.
     */
    private final PersonValue[] values;

    /**
     * Constructor which splits given summary information about people into data corresponding
     * to single people. If data does not match any known person information format, then it ignores that data
     * and notifies about such incident.
     *
     * @param value data which will be split and converted to {@link PersonValue}
     * @throws ParsingException when there has not been any correct-format person data found
     * @see PersonValue
     */
    public MultipleValue(IBibtexValue value) throws ParsingException {

        this.values = Arrays
                .stream(value.getString().split(" and "))
                .map(String::trim)
                .map(personData -> {
                    try {
                        return new PersonValue(personData);
                    } catch (InvalidPersonException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(PersonValue[]::new);
        if (this.values.length == 0) {
            throw new InvalidPersonException(value);
        }
    }

    /**
     * Method which returns array of all people assigned to this value.
     *
     * @return array of all people assigned to this value
     */
    public PersonValue[] getValues() {
        return values;
    }

    /**
     * Method to get summary information about all people assigned to this value
     * in a form of string representation.
     *
     * @return string representation of information about people assigned to this value
     */
    @Override
    public String getString() {
        StringBuilder result = new StringBuilder();
        for (PersonValue person : values) {
            result.append(person.getString()).append("\n");
        }
        return result.toString();
    }

}
