# Pascal Compiler

This repository contains a Java implementation of a compiler for the Pascal programming language. This is my CS course work (Principles of Programming Languages). The first part of the project was to implement the Lexical Analyzer, this part was done with a group member in the class.

## Overview

The Pascal Compiler project is a Java program that reads Pascal source code, tokenizes it, and performs syntax checks. It consists of three primary classes: `PascalCompiler`, `SyntaxAnalyzer`, and `LexicalAnalyzer`.

- `PascalCompiler` is the main class which reads an input file and creates an instance of the `SyntaxAnalyzer` to tokenize the input and perform syntax checks. It includes syntax checks for various Pascal constructs like `if` statements, variable declarations, arithmetic operations, and boolean expressions. The compiler can also check for matching parentheses and quotes, and ensure that `BEGIN` and `END` keywords match properly in the source code.

- `SyntaxAnalyzer` is responsible for checking the syntax of the tokenized Pascal code. This class includes various methods for checking the syntax of different Pascal constructs. These methods are used by the `PascalCompiler` class to verify the syntax of the code.

- `LexicalAnalyzer` is used to break down the input into individual tokens for further analysis by the `SyntaxAnalyzer` class. It reads the input character by character and creates a token when it recognizes a valid token in the input.

## Usage

Here is an example of how you might run a Java project from the command line:

```bash
# Compile the Java files
javac *.java

# Run the PascalCompiler class with the Pascal source code file as input
java PascalCompiler input.pas
```

Please note that the above is a general example and may not apply directly to this project. It is also possible that the project requires specific versions of Java, JDK11+.

For a more accurate usage guide and build instructions I recommend reaching out.

## Academic Honesty / Usage Policy
This repository is a part of a school project and is made public for the purpose of showcasing my course work. The project is not intended for use in future school projects. Any use of this code for academic purposes without proper citation is considered a breach of academic integrity rules at most institutions.

Please note that plagiarism is a serious offense and can result in severe consequences. This includes submitting this project as your own work or substantially incorporating it into your own project without proper acknowledgment. Before using any materials from this project, please consult your institution's academic honesty policy or your instructor.

If you wish to reference or use this project in any way, please ensure to provide proper attribution. This can usually be done in the form of a citation or a noticeable comment in your code.

Remember, the goal of school projects is to learn and develop your own skills.

## Contributing

There are no specific contributing guidelines for this project. If you're interested in contributing, consider opening an issue to discuss your proposed changes or submit a pull request directly.

## Contact

For any questions or concerns, please open an issue on the GitHub repository.

---
