import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

public class SyntaxAnalyzer {
    private Reader reader;
    private char currentChar;
    private Stack<Character> bracketStack = new Stack<>();

    public SyntaxAnalyzer(Reader reader) throws IOException {
        this.reader = reader;
        this.currentChar = (char) reader.read();
    }

    public Token getNextToken() throws Exception {
        while (Character.isWhitespace(currentChar)) {
            readNextChar();
        }

        if (currentChar == '{') {
            skipComment();
            return getNextToken();
        }

        // Check for matching brackets, paren, quotes START OF SYNTAX ANALYSIS!
        if (currentChar == '(' || currentChar == '[' || currentChar == '{' || currentChar == '\''
                || currentChar == '\"') {
            bracketStack.push(currentChar);
        } else if (currentChar == ')' || currentChar == ']' || currentChar == '}' || currentChar == '\''
                || currentChar == '\"') {
            if (!bracketStack.isEmpty() && isMatchingBracket(bracketStack.peek(), currentChar)) {
                bracketStack.pop();
            } else {
                // Throw syntax error exception for unmatching brackets.
                throw new Exception("Non-matching brackets/ paren / quotes syntax error:" + currentChar);
            }
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

    // Check syntax

    // Assignment statement syntax
    private boolean checkAssignmentStatement() {
        return true;
    }

    // Variable declaration syntax
    private boolean checkVariableDeclaration() {
        return true;
    }
    // Arithmetic operation syntax
    private boolean checkArithmeticOperation() {
        return true;
    }
    // Boolean expression
    private boolean checkBooleanExpression() {
        return true;
    }
    // If statement
    private boolean checkIfStatement() {
        return true;
    }
    // While loop
    private boolean checkWhileLoop() {
        return true;
    }
    // For loop
    private boolean checkForLoop() {
        return true;
    }
    // This will match the brackets, for PART 2 within the stack
    private boolean isMatchingBracket(char open, char close) {
        return  (open == '{' && close == '}') ||
                (open == '[' && close == ']') ||
                (open == '(' && close == ')') ||
                (open == '\'' && close == '\'') ||
                (open == '\"' && close == '\"');
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