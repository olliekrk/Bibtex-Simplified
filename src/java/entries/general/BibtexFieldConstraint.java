package entries.general;


/**
 * Enumeration used to set constraints on BibTeX entry fields.
 */
public enum BibtexFieldConstraint {
    /**
     * no constrains
     */
    none,
    /**
     * field is required
     */
    required,
    /**
     * field can have multiple values
     */
    multiple,
    /**
     * field is both required and can have multiple values
     */
    requiredMultiple,
    /**
     * field is required unless there is present another corresponding alternative field
     */
    alternative,
    /**
     * field is both alternative and can have multiple values
     */
    alternativeMultiple
}
