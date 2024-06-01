package com.dank1234;

import com.dank1234.core.lexer.Lexer;
import com.dank1234.core.lexer.Token;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Main {
    private static final String filePath = "src/main/java/com/dank1234/dpp/main.dpp";

    public void main() {
        try {
            System.out.println();
            String content = new String(Files.readAllBytes(new File(filePath).toPath()));
            Lexer lexer = new Lexer(content);
            List<Token> tokens = lexer.tokenize();
            for (Token token : tokens) {
                System.out.println(token);
            }
        }catch(Exception _){}
    }
}