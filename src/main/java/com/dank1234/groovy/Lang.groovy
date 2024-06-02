package com.dank1234.groovy

import com.dank1234.groovy.parser.Parser
import com.dank1234.groovy.util.FileUtil

class Lang {
    static void runCode(String pathParam) {
        def filePath = "src/main/java/com/dank1234/dpp/"+pathParam
        Lexer lexer = new Lexer(FileUtil.readFileAsString(filePath))
        Parser parser = new Parser(lexer.tokenize())
        parser.parse()
    }
}
