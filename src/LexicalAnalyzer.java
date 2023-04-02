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


