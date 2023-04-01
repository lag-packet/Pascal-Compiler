enum PascalKeywords {
    PROGRAM(Token.PROGRAM, "PROGRAM"),
    BEGIN(Token.BEGIN, "BEGIN"),
    END(Token.END, "END"),
    VAR(Token.VAR, "VAR"),
    INTEGER(Token.INTEGER, "INTEGER"),
    REAL(Token.REAL, "REAL"),
    PROCEDURE(Token.PROCEDURE, "PROCEDURE");

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
}