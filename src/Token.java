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
    public static final Token FUNCTION = new Token(TokenType.FUNCTION, "FUNCTION");
    public static final Token IF = new Token(TokenType.IF, "IF");
    public static final Token THEN = new Token(TokenType.THEN, "THEN");
    public static final Token ELSE = new Token(TokenType.ELSE, "ELSE");
    public static final Token WHILE = new Token(TokenType.WHILE, "WHILE");
    public static final Token DO = new Token(TokenType.DO, "DO");
    public static final Token FOR = new Token(TokenType.FOR, "FOR");
    public static final Token TO = new Token(TokenType.TO, "TO");
    public static final Token DOWNTO = new Token(TokenType.DOWNTO, "DOWNTO");
    public static final Token ARRAY = new Token(TokenType.ARRAY, "ARRAY");
    public static final Token OF = new Token(TokenType.OF, "OF");
    public static final Token RECORD = new Token(TokenType.RECORD, "RECORD");
    public static final Token CASE = new Token(TokenType.CASE, "CASE");
    public static final Token REPEAT = new Token(TokenType.REPEAT, "REPEAT");
    public static final Token UNTIL = new Token(TokenType.UNTIL, "UNTIL");
    public static final Token CONST = new Token(TokenType.CONST, "CONST");
    public static final Token TYPE = new Token(TokenType.TYPE, "TYPE");
    public static final Token DIV = new Token(TokenType.DIV, "DIV");
    public static final Token MOD = new Token(TokenType.MOD, "MOD");
    public static final Token TRUE = new Token(TokenType.BOOLEAN_CONST, "TRUE");
    public static final Token FALSE = new Token(TokenType.BOOLEAN_CONST, "FALSE");
    public static final Token AND = new Token(TokenType.BOOLEAN_OPERATOR, "AND");
    public static final Token OR = new Token(TokenType.BOOLEAN_OPERATOR, "OR");
    public static final Token NOT = new Token(TokenType.BOOLEAN_OPERATOR, "NOT");
    public static final Token EOF = new Token(TokenType.EOF, "EOF");

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
