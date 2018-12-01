package values;

/**
 * Data container for BibTeX entries' fields' values.
 *
 * @see entries.general.BibtexEntry
 */
public interface IBibtexValue {

    /**
     * Method to convert object value of BibTeX field to its string representation.
     *
     * @return string representation of this value
     */
    String getString();
}
