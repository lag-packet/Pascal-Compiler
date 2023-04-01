import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class PascalCompiler {

    public static void main(String[] args) {
        try {
            // Read the input file
            File inputFile = new File("input.pas");
            Scanner scanner = new Scanner(inputFile).useDelimiter("\\Z");
            String inputText = scanner.next();

            // Create a new instance of the syntax analyzer
            SyntaxAnalyzer lexer = new SyntaxAnalyzer(new StringReader(inputText));

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
