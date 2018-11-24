package exceptions;

public class MissingClosingBracketException extends ParsingException  {
    public MissingClosingBracketException() {
        super("BiBTeX document is missing a closing bracket.");
    }
}
