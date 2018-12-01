package values;

/**
 * Container used to store BibTeX entries' fields' values which are integers.
 */
public class NumberValue implements IBibtexValue {
    /**
     * integer value of this {@link IBibtexValue}
     */
    private final int value;

    public NumberValue(int value) {
        this.value = value;
    }

    /**
     * Method which returns stored value as a string.
     *
     * @return stored value as a string
     */
    @Override
    public String getString() {
        return Integer.toString(value);
    }
}
