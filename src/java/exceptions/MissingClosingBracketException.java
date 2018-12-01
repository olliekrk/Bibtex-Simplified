package exceptions;

/**
 * Exception thrown by a {@link parser.ParserUtilities}
 * during parsing .bib file when that file is missing closing bracket '}'.
 */
public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException() {
        super("BiBTeX document is missing a closing bracket. Parsing was interrupted.");
    }
}
