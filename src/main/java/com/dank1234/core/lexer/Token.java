package com.dank1234.core.lexer;

public record Token(Type type, String value, int line, int column) {
    @Override
    public String toString() {
        return STR."Token{type=\{type}, value='\{value}\{'\''}, line=\{line}, column=\{column}\{'}'}";
    }
}