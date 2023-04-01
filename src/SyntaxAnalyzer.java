import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

public class SyntaxAnalyzer {
    private final Reader reader;
    private char currentChar;
    private int currentLineNum = 1;
    private final Stack<Character> bracketStack = new Stack<>();

    public SyntaxAnalyzer(Reader reader) throws IOException {
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

        // Check for matching brackets, paren, quotes START OF SYNTAX ANALYSIS!
        if (currentChar == '(' || currentChar == '[' || currentChar == '{' || currentChar == '\''
                || currentChar == '\"') {
            bracketStack.push(currentChar);
        } else if (currentChar == ')' || currentChar == ']' || currentChar == '}' || currentChar == '\''
                || currentChar == '\"') {
            if (!bracketStack.isEmpty() && isMatchingBracket(bracketStack.peek(), currentChar)) {
                bracketStack.pop();
            } else {
                // Throw error for unmatching brackets.
                System.err.println("Non-matching opening with closing " + currentChar + " Line: "
                        + getCurrentLineNum());
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
    private boolean checkAssignmentStatement() throws IOException {
        // Read the next token, expecting the ASSIGN token (:=)
        Token token = getNextToken();
        if (token.getType() != TokenType.ASSIGN) {
            return false; // If the ASSIGN token is not found, the assignment statement is invalid
        }

        // Read the next token, which should be the start of an expression
        token = getNextToken();

        // Check if the expression is valid (assuming there's a separate method for this)
        boolean isValidExpression = checkExpression(token);

        return isValidExpression;
    }

    private boolean checkExpression(Token token) {
        // Implement checking logic for expressions here
        return true;
    }

    // Variable declaration syntax
    public boolean checkVariableDeclaration() throws IOException {
        Token token = getNextToken();
        if (token.getType() != TokenType.VAR) {
            return false;
        }

        token = getNextToken();
        if (token.getType() != TokenType.IDENTIFIER) {
            return false;
        }

        token = getNextToken();
        if (token.getType() != TokenType.COLON) {
            return false;
        }

        token = getNextToken();
        if (!isTypeToken(token)) {
            return false;
        }

        token = getNextToken();
        return token.getType() == TokenType.SEMICOLON;
    }

    // Check if the token is a type (e.g., INTEGER, REAL, BOOLEAN)
    private boolean isTypeToken(Token token) {
        return token.getType() == TokenType.INTEGER || token.getType() == TokenType.REAL
                || token.getType() == TokenType.BOOLEAN;
    }

    // Arithmetic operation syntax
    public boolean checkArithmeticOperation() throws IOException {
        Token token = getNextToken();
        if (!isArithmeticOperand(token)) {
            return false;
        }

        token = getNextToken();
        if (!isArithmeticOperator(token)) {
            return false;
        }

        token = getNextToken();
        return isArithmeticOperand(token);
    }

    // Check if the token is an arithmetic operand (e.g., an identifier or a number)
    private boolean isArithmeticOperand(Token token) {
        return token.getType() == TokenType.IDENTIFIER || token.getType() == TokenType.INTEGER_CONST
                || token.getType() == TokenType.REAL_CONST;
    }

    // Check if the token is an arithmetic operator (e.g., +, -, *, /)
    private boolean isArithmeticOperator(Token token) {
        return token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS
                || token.getType() == TokenType.TIMES || token.getType() == TokenType.DIVIDE;
    }

    // Boolean expression
    public boolean checkBooleanExpression() throws IOException {
        Token token = getNextToken();
        if (!isBooleanOperand(token)) {
            return false;
        }

        token = getNextToken();
        if (!isBooleanOperator(token)) {
            return false;
        }

        token = getNextToken();
        return isBooleanOperand(token);
    }

    private boolean isBooleanOperator(Token token) {
        return token.getType() == TokenType.BOOLEAN_OPERATOR;
    }

    // Check if the token is a boolean operand (e.g., an identifier or a boolean constant)
    private boolean isBooleanOperand(Token token) {
        return token.getType() == TokenType.IDENTIFIER || token.getType() == TokenType.BOOLEAN_CONST;
    }

    // If statement
    public boolean checkIfStatement() throws IOException {
        Token token = getNextToken();
        if (token.getType() != TokenType.IF) {
            return false;
        }

        token = getNextToken();
        if (!checkBooleanExpression()) {
            return false;
        }

        token = getNextToken();
        if (token.getType() != TokenType.THEN) {
            return false;
        }

        // Assuming checkStatement() handles checking the statement following THEN
        if (!checkStatement()) {
            return false;
        }

        token = getNextToken();
        if (token.getType() == TokenType.ELSE) {
            if (!checkStatement()) {
                return false;
            }
            token = getNextToken();
        }

        return token.getType() == TokenType.END;
    }

    // Check statement syntax
    // This is a simplified version, you may need to extend it to support various statement types.
    public boolean checkStatement() throws IOException {
        Token token = getNextToken();
        if (PascalKeywords.isKeyword(token.getValue())) {
            switch (PascalKeywords.valueOf(token.getValue())) {
                case IF:
                    return checkIfStatement();
                case WHILE:
                    return checkWhileLoop();
                case FOR:
                    return checkForLoop();
                case BEGIN:
                    // Assuming checkCompoundStatement() handles checking compound statements
                    return checkCompoundStatement();
                default:
                    return false;
            }
        } else if (token.getType() == TokenType.IDENTIFIER) {
            return checkAssignmentStatement();
        } else {
            return false;
        }
    }

    // Check compound statement syntax
    // This is a simplified version, you may need to extend it to support nested compound statements.
    public boolean checkCompoundStatement() throws IOException {
        boolean isValid = true;
        Token token = getNextToken();
        while (isValid && token.getType() != TokenType.END) {
            isValid = checkStatement();
            if (isValid) {
                token = getNextToken();
                if (token.getType() == TokenType.SEMICOLON) {
                    token = getNextToken();
                }
            }
        }
        return isValid;
    }

    // While loop
    public boolean checkWhileLoop() {
        return true;
    }

    // For loop
    public boolean checkForLoop() {
        return true;
    }

    // This will match the brackets, for PART 2 within the stack
    public boolean isMatchingBracket(char open, char close) {
        return (open == '{' && close == '}') ||
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
        } else if (identifier.equals("TRUE") || identifier.equals("FALSE")) {
            return new Token(TokenType.BOOLEAN_CONST, identifier);
        } else if (identifier.equals("AND") || identifier.equals("OR") || identifier.equals("NOT")) {
            return new Token(TokenType.BOOLEAN_OPERATOR, identifier);
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

    public int getCurrentLineNum() {
        return currentLineNum;
    }

    private void readNextChar() throws IOException {
        int nextChar = reader.read();
        currentChar = nextChar == -1 ? '\0' : (char) nextChar;

        if (currentChar == '\n') {
            currentLineNum++;
        }
    }
}