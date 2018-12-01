package entries.general;

/**
 * Interface which is a part of Visitor design pattern.
 *
 * @see BibtexVisitor
 */
public interface BibtexVisitableElement {
    /**
     * Method used to let {@link BibtexVisitor} visit this element.
     *
     * @param visitor visitor which performs visit operation on this element
     */
    void accept(BibtexVisitor visitor);
}
