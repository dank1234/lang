package com.dank1234.groovy

import com.dank1234.core.Token
import com.dank1234.groovy.util.FileUtil

class Lang {
    private String filePath;

    void runCode(String pathParam) {
        filePath = "src/main/java/com/dank1234/dpp/"+pathParam
        List<Token> tokens = new Lexer(FileUtil.readFileAsString(filePath)).tokenize()
        for(Token token : tokens) {
            println(token)
        }
    }
}
