package com.dank1234;

import com.dank1234.groovy.Lang;
import com.dank1234.groovy.lexer.FileBuilder;

public class Main {
    public void main() {
        Lang.runCode("main.dpp");
        new FileBuilder("src/main/java/com/dank1234/dpp/main.dpp").createFile();
    }
}