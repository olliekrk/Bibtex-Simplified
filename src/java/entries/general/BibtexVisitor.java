package entries.general;

import parser.BibtexBibliography;

import java.util.List;
import java.util.Map;

public interface BibtexVisitor {
    void visit(BibtexEntry bibtexEntry);

    void visit(BibtexBibliography bibtexBibliography);

    void visit(BibtexBibliography bibtexBibliography, Map<String, List<String>> filters);
}
