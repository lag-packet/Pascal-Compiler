import java.io.*;
import java.util.Scanner;
import java.io.Reader;

/* Lexical analayzer for pascal
    @ Satar Hassni
 */

public class LexicalAnalyzer {
    private Reader reader;
    private char currentChar;

    public LexicalAnalyzer(Reader reader) throws IOException {
        this.reader = reader;
        this.currentChar = (char) reader.read();
    }

    public Token getNextToken() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            readNextChar();
        }

        if (currentChar == '{') {
            skipComment();
            return getNextToken();
        }

        if (Character.isLetter(currentChar)) {
            return readIdentifier();
        }

        if (Character.isDigit(currentChar)) {
            return readNumber();
        }
        switch (currentChar) {
            case '\'':
                readNextChar();
                return Token.SINGLEQUOTE;
            case '“':
                readNextChar();
                return Token.LEFTDOUBLEQUOTE;
            case '”':
                readNextChar();
                return Token.RIGHTDOUBLEQUOTE;
            case '+':
                readNextChar();
                return Token.PLUS;
            case '-':
                readNextChar();
                return Token.MINUS;
            case '*':
                readNextChar();
                return Token.TIMES;
            case '%':
                readNextChar();
                return Token.MODULO;
            case '/':
                readNextChar();
                return Token.DIVIDE;
            case '(':
                readNextChar();
                return Token.LPAREN;
            case ')':
                readNextChar();
                return Token.RPAREN;
            case ';':
                readNextChar();
                return Token.SEMICOLON;
            case ':':
                readNextChar();
                if (currentChar == '=') {
                    readNextChar();
                    return Token.ASSIGN;
                } else {
                    return Token.COLON;
                }
            case '.':
                readNextChar();
                return Token.DOT;
            case ',':
                readNextChar();
                return Token.COMMA;
            default:
                if (currentChar == '=') {
                    readNextChar();
                    return Token.ASSIGN;
                } else {
                    throw new IllegalArgumentException("Illegal character: " + currentChar);
                }
        }
    }

    private Token readIdentifier() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar)) {
            builder.append(currentChar);
            readNextChar();
        }
        String identifier = builder.toString().toUpperCase();
        if (PascalKeywords.isKeyword(identifier)) {
            return PascalKeywords.valueOf(identifier).getToken();
        } else {
            return new Token(TokenType.IDENTIFIER, identifier);
        }
    }

    private Token readNumber() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            builder.append(currentChar);
            readNextChar();
        }
        if (currentChar == '.') {
            builder.append(currentChar);
            readNextChar();
            while (Character.isDigit(currentChar)) {
                builder.append(currentChar);
                readNextChar();
            }
            return new Token(TokenType.REAL_CONST, builder.toString());
        } else {
            return new Token(TokenType.INTEGER_CONST, builder.toString());
        }
    }

    private void skipComment() throws IOException {
        while (currentChar != '}') {
            readNextChar();
            if (currentChar == '{') {
                skipComment();
            }
        }
        readNextChar();
    }

    private void readNextChar() throws IOException {
        int nextChar = reader.read();
        currentChar = nextChar == -1 ? '\0' : (char) nextChar;
    }
}

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

enum TokenType {
    PLUS, MINUS, TIMES, DIVIDE, LPAREN, RPAREN, SEMICOLON, COLON, ASSIGN, DOT, COMMA,
    INTEGER_CONST, REAL_CONST, IDENTIFIER, MODULO,
    PROGRAM, BEGIN, END, VAR, INTEGER, PROCEDURE, REAL, QUOTE, LEFTDOUBLEQUOTE, RIGHTDOUBLEQUOTE, EOF
}

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

    public static void main(String[] args) {
        try {
            // Read the input file
            File inputFile = new File("input.pas");
            Scanner scanner = new Scanner(inputFile).useDelimiter("\\Z");
            String inputText = scanner.next();

            // Create a new instance of the lexical analyzer
            LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(inputText));

            // Tokenize the input and print the tokens
            Token token;
            do {
                token = lexer.getNextToken();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

