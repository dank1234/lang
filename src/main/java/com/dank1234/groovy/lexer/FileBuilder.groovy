package com.dank1234.groovy.lexer

import com.dank1234.core.tokenization.Token

class FileBuilder {
    private final String path
    private final String buildPath = "src/main/java/com/dank1234/build/lexer.tkn"
    FileBuilder(String path) {
        this.path = path
    }

    void createFile() {
        List<Token> tokens = new Lexer(new File(path).text).tokenize()

        new File(buildPath).withWriter { writer ->
            writer.writeLine('[')
            def i = 0;
            tokens.each { token ->
                writer.writeLine(tokens.get(i) == tokens.get(tokens.size() - 1)
                        as String ? token.toFile() : token.toFile()+',')
                i++
            }
            writer.writeLine(']')
        }
    }
}
