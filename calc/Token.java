/**
 * The <tt> Token </tt> class only exists because java doesn't have fat enums.
 * If java had fat enums like rust, swift, or haskell then I could move the number
 * field into the enum and move the helper functions into the enums namespace.
 **/
public class Token {
    private TokenKind tokenKind;
    private Long number;
    public Token(TokenKind tokenKind) { this(tokenKind,null); }
    public Token(TokenKind tokenKind, Long number) {
        this.tokenKind = tokenKind;
        this.number = number;
    }
    public TokenKind getTokenKind() { return this.tokenKind; }
    public Long getNumber() { return this.number; }
    public Integer precedence() {
        switch (this.tokenKind) {
        case LeftParen:
            return Integer.MIN_VALUE;
        case RightParen:
            return Integer.MAX_VALUE;
        case Mul: case Div: case Mod:
            return 2;
        case Sub: case Add:
            return 1;
        case Var: case Number:
            return 0;
        default:
            return 0;
        }
    }
    public boolean typeIs(TokenKind tk) {
        return this.tokenKind.ordinal() == tk.ordinal();
    }
    public boolean wasOperand() {
        switch (this.tokenKind) {
            case Var: case Number:
                return true;
            default:
                return false;
        }
    }
    public boolean wasOperator() {
        switch (this.tokenKind) {
            case Mul: case Div: case Mod:
            case Sub: case Add:
                return true;
            default:
                return false;
        }
    }
    @Override
    public String toString() {
        switch (this.tokenKind) {
            case LeftParen:
                return "(";
            case RightParen:
                return ")";
            case Mul:
                return "*";
            case Div:
                return "/";
            case Mod:
                return "%";
            case Sub:
                return "-";
            case Add:
                return "+";
            case Var:
                return "x";
            case Number:
                return this.number.toString();
            default:
                return null;
        }
    }
}
