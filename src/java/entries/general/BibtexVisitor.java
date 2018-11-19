package entries.general;

import parser.BibtexBibliography;

public interface BibtexVisitor {
    void visit(BibtexEntry bibtexEntry);

    void visit(BibtexBibliography bibtexBibliography);
}
