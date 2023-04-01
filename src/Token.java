class Token {
    public static final Token PLUS = new Token(TokenType.PLUS, "+");
    public static final Token MINUS = new Token(TokenType.MINUS, "-");
    public static final Token TIMES = new Token(TokenType.TIMES, "*");
    public static final Token MODULO = new Token(TokenType.MODULO, "%");
    public static final Token DIVIDE = new Token(TokenType.DIVIDE, "/");
    public static final Token LPAREN = new Token(TokenType.LPAREN, "(");
    public static final Token RPAREN = new Token(TokenType.RPAREN, ")");
    public static final Token SEMICOLON = new Token(TokenType.SEMICOLON, ";");
    public static final Token COLON = new Token(TokenType.COLON, ":");
    public static final Token ASSIGN = new Token(TokenType.ASSIGN, ":=");
    public static final Token DOT = new Token(TokenType.DOT, ".");
    public static final Token COMMA = new Token(TokenType.COMMA, ",");
    public static final Token PROGRAM = new Token(TokenType.PROGRAM, "PROGRAM");
    public static final Token BEGIN = new Token(TokenType.BEGIN, "BEGIN");
    public static final Token END = new Token(TokenType.END, "END");
    public static final Token VAR = new Token(TokenType.VAR, "VAR");
    public static final Token INTEGER = new Token(TokenType.INTEGER, "INTEGER");
    public static final Token PROCEDURE = new Token(TokenType.PROCEDURE, "PROCEDURE");
    public static final Token REAL = new Token(TokenType.REAL, "REAL");
    public static final Token SINGLEQUOTE = new Token(TokenType.QUOTE, "\'");
    public static final Token LEFTDOUBLEQUOTE = new Token(TokenType.LEFTDOUBLEQUOTE, "“");
    public static final Token RIGHTDOUBLEQUOTE = new Token(TokenType.RIGHTDOUBLEQUOTE, "”");

    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return type + "(" + value + ")";
    }
}
