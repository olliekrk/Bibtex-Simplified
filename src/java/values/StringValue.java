package values;

/**
 * Container for string values of BibTeX entries' fields.
 */
public class StringValue implements IBibtexValue {
    /**
     * String representation of stored value.
     */
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    /**
     * Returns stored string value.
     *
     * @return stored string value
     */
    @Override
    public String getString() {
        return value;
    }
}
