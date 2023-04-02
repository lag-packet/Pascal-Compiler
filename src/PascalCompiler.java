import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class PascalCompiler {
    public static void main(String[] args) {
        try {
            // Read the input file
            File inputFile = new File("validprogram.pas");
            Scanner scanner = new Scanner(inputFile).useDelimiter("\\Z");
            String inputText = scanner.next();

            // Create a new instance of the syntax analyzer
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(new StringReader(inputText));

            // Tokenize the input and perform syntax checks
            Token token;
            do {
                token = syntaxAnalyzer.getCurrentToken();
                System.out.println(token);

                // Add syntax checks here, using the methods from the SyntaxAnalyzer class
                if (token.getType() == TokenType.IF) {
                    boolean isValidIfStatement = syntaxAnalyzer.checkIfStatement();

                    // Handle result of check
                    if (!isValidIfStatement) {
                        System.err.println("Invalid 'if' statement syntax");
                    } else {
                        System.out.println("Valid 'if' statement");
                    }
                } else if (token.getType() == TokenType.VAR) {
                    boolean isValidVarDeclaration = syntaxAnalyzer.checkVariableDeclaration();

                    // Handle result of check
                    if (!isValidVarDeclaration) {
                        System.err.println("Invalid variable declaration syntax");
                    } else {
                        System.out.println("Valid variable declaration");
                    }
                } else if (isArithmeticOperator(token)) {
                    boolean isValidArithmeticOperation = syntaxAnalyzer.checkArithmeticOperation();

                    // Handle result of check
                    if (!isValidArithmeticOperation) {
                        System.err.println("Invalid arithmetic operation syntax");
                    } else {
                        System.out.println("Valid arithmetic operation");
                    }
                } else if (isBooleanOperator(token)) {
                    boolean isValidBooleanExpression = syntaxAnalyzer.checkBooleanExpression();

                    // Handle result of check
                    if (!isValidBooleanExpression) {
                        System.err.println("Invalid boolean expression syntax");
                    } else {
                        System.out.println("Valid boolean expression");
                    }
                }

                // Add syntax checks for While loop and For loop as extra credit

                syntaxAnalyzer.consume();

            } while (token.getType() != TokenType.EOF);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isArithmeticOperator(Token token) {
        TokenType type = token.getType();
        return type == TokenType.PLUS || type == TokenType.MINUS || type == TokenType.TIMES || type == TokenType.DIVIDE;
    }

    private static boolean isBooleanOperator(Token token) {
        TokenType type = token.getType();
        return type == TokenType.AND || type == TokenType.OR;
    }
}
