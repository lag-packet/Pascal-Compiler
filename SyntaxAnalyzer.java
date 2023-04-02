import java.io.Reader;
import java.io.IOException;

public class SyntaxAnalyzer {
    private LexicalAnalyzer lexer;
    private Token currentToken;

    public Token getCurrentToken() {
        return currentToken;
    }
    public SyntaxAnalyzer(Reader reader) throws IOException {
        this.lexer = new LexicalAnalyzer(reader);
        this.currentToken = lexer.getNextToken();
    }

    // Helper method to consume the current token and get the next token
    public void consume() throws IOException {
        currentToken = lexer.getNextToken();
    }

    // Methods for syntax checks will be added here
    public boolean checkIfStatement() throws IOException {
        consume(); // Consume 'if' keyword
        // Check for a boolean expression
        if (checkBooleanExpression()) {
            consume();
            if (currentToken.getType() == TokenType.THEN) {
                consume();
                return true;
            }
        }
        return false;
    }

    public boolean checkVariableDeclaration() throws IOException {
        consume(); // Consume 'var' keyword
        if (currentToken.getType() == TokenType.IDENTIFIER) {
            consume();
            if (currentToken.getType() == TokenType.COLON) {
                consume();
                if (PascalKeywords.isType(currentToken.getValue())) {
                    consume();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkArithmeticOperation() throws IOException {
        // Assume the current token is an arithmetic operator
        consume();
        if (currentToken.getType() == TokenType.IDENTIFIER || currentToken.getType() == TokenType.INTEGER_CONST || currentToken.getType() == TokenType.REAL_CONST) {
            consume();
            return true;
        }
        return false;
    }

    public boolean checkBooleanExpression() throws IOException {
        // Assuming that the current token is already a boolean operator (AND or OR)
        lexer.getNextToken(); // Move to the next token

        // Check if the next token is a valid operand (identifier, integer, or real constant)
        TokenType type = getCurrentToken().getType();
        if (type == TokenType.IDENTIFIER || type == TokenType.INTEGER_CONST || type == TokenType.REAL_CONST) {
            lexer.getNextToken(); // Move to the next token

            // Check if the next token is a boolean operator or a closing parenthesis
            type = getCurrentToken().getType();
            if (type == TokenType.AND || type == TokenType.OR || type == TokenType.RPAREN) {
                return true;
            }
        }

        return false;
    }


}
