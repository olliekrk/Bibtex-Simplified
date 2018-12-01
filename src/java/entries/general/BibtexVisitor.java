package entries.general;

import parser.BibtexBibliography;

import java.util.List;
import java.util.Map;

/**
 * Interface which is a part of Visitor design pattern.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Description of Visitor design pattern</a>
 */
public interface BibtexVisitor {
    /**
     * Method to perform visit operation on single {@link BibtexEntry} object
     *
     * @param bibtexEntry entry to be visited
     */
    void visit(BibtexEntry bibtexEntry);

    /**
     * Method to perform visit operation on whole {@link BibtexBibliography} - on every entry of bibliography.
     *
     * @param bibtexBibliography bibliography to be visited
     */
    void visit(BibtexBibliography bibtexBibliography);

    /**
     * Method to perform visit operation on subset of entries of a {@link BibtexBibliography}.
     * Which entry belongs to this subset is dictated by filters.
     *
     * @param bibtexBibliography bibliography to be visited
     * @param filters            map in which keys are names of fields which are included in filter operation
     *                           and values are lists of acceptable values of those fields
     */
    void visit(BibtexBibliography bibtexBibliography, Map<String, List<String>> filters);
}
