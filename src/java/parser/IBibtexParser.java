package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface IBibtexParser {

    BibtexBibliography parseFile(String path) throws FileNotFoundException;

    BibtexBibliography parseFile(File file) throws FileNotFoundException;

    BibtexBibliography parseData(InputStream inputStream);

    BibtexBibliography parseData(String Data);
}
