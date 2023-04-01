import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class PascalCompiler {

    private static boolean isArithmeticOperator(Token token) {
        return token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS
                || token.getType() == TokenType.TIMES || token.getType() == TokenType.DIVIDE;
    }

    private static boolean isBooleanOperator(Token token) {
        return token.getType() == TokenType.AND || token.getType() == TokenType.OR;
    }

    public static void main(String[] args) {
        try {
            // Read the input file
            File inputFile = new File("validprogram.pas");
            Scanner scanner = new Scanner(inputFile).useDelimiter("\\Z");
            String inputText = scanner.next();

            // Create a new instance of the syntax analyzer
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(new StringReader(inputText));

            // Tokenize the input and print the tokens
            Token token;
            do {
                token = syntaxAnalyzer.getNextToken();

                // Helper methods based on the token LOGIC FOR SYNTAX CHECK HERE
                if (token.getType() == TokenType.IF) {
                    boolean isValidIfStatement = syntaxAnalyzer.checkIfStatement();

                    // Handle result of check
                    if (!isValidIfStatement) {
                        System.err.println("Invalid 'if' statement syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid 'if' statement at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                } else if (token.getType() == TokenType.VAR) {
                    boolean isValidVarDeclaration = syntaxAnalyzer.checkVariableDeclaration();

                    // Handle result of check
                    if (!isValidVarDeclaration) {
                        System.err.println("Invalid variable declaration syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid variable declaration at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                } else if (isArithmeticOperator(token)) {
                    boolean isValidArithmeticOperation = syntaxAnalyzer.checkArithmeticOperation();

                    // Handle result of check
                    if (!isValidArithmeticOperation) {
                        System.err.println("Invalid arithmetic operation syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid arithmetic operation at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                } else if (isBooleanOperator(token)) {
                    boolean isValidBooleanExpression = syntaxAnalyzer.checkBooleanExpression();

                    // Handle result of check
                    if (!isValidBooleanExpression) {
                        System.err.println("Invalid boolean expression syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid boolean expression at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                } else if (token.getType() == TokenType.WHILE) {
                    boolean isValidWhileLoop = syntaxAnalyzer.checkWhileLoop();

                    // Handle result of check
                    if (!isValidWhileLoop) {
                        System.err.println("Invalid while loop syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid while loop at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                } else if (token.getType() == TokenType.FOR) {
                    boolean isValidForLoop = syntaxAnalyzer.checkForLoop();

                    // Handle result of check
                    if (!isValidForLoop) {
                        System.err.println("Invalid for loop syntax at line: " + syntaxAnalyzer.getCurrentLineNum());
                    } else {
                        System.out.println("Valid for loop at line: " + syntaxAnalyzer.getCurrentLineNum());
                    }
                }
                System.out.println(token); // Lexical Token parsed.
            } while (token.getType() != TokenType.EOF);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
