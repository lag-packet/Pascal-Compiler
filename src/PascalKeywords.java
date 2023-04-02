enum PascalKeywords {
    PROGRAM(Token.PROGRAM, "PROGRAM"),
    BEGIN(Token.BEGIN, "BEGIN"),
    END(Token.END, "END"),
    VAR(Token.VAR, "VAR"),
    INTEGER(Token.INTEGER, "INTEGER"),
    REAL(Token.REAL, "REAL"),
    PROCEDURE(Token.PROCEDURE, "PROCEDURE"),
    FUNCTION(Token.FUNCTION, "FUNCTION"),
    IF(Token.IF, "IF"),
    THEN(Token.THEN, "THEN"),
    ELSE(Token.ELSE, "ELSE"),
    WHILE(Token.WHILE, "WHILE"),
    DO(Token.DO, "DO"),
    FOR(Token.FOR, "FOR"),
    TO(Token.TO, "TO"),
    DOWNTO(Token.DOWNTO, "DOWNTO"),
    ARRAY(Token.ARRAY, "ARRAY"),
    OF(Token.OF, "OF"),
    RECORD(Token.RECORD, "RECORD"),
    CASE(Token.CASE, "CASE"),
    REPEAT(Token.REPEAT, "REPEAT"),
    UNTIL(Token.UNTIL, "UNTIL"),
    CONST(Token.CONST, "CONST"),
    TYPE(Token.TYPE, "TYPE"),
    DIV(Token.DIV, "DIV"),
    MOD(Token.MOD, "MOD"),
    AND(Token.AND, "AND"),
    OR(Token.OR, "OR"),
    NOT(Token.NOT, "NOT");

    private Token token;
    private String lexeme;

    PascalKeywords(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public Token getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public static boolean isKeyword(String s) {
        for (PascalKeywords keyword : values()) {
            if (keyword.getLexeme().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isType(String keyword) {
        return keyword.equals("INTEGER") || keyword.equals("REAL") || keyword.equals("CHAR") || keyword.equals("BOOLEAN");
    }

    public static boolean isRelationalOperator(String keyword) {
        return keyword.equals("=") || keyword.equals("<>") || keyword.equals("<") || keyword.equals(">") || keyword.equals("<=") || keyword.equals(">=");
    }

}