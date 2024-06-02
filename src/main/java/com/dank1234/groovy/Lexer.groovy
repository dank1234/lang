package com.dank1234.groovy

import com.dank1234.core.Token
import com.dank1234.core.Type

class Lexer {
    private final String input
    private int currentPos = 0
    private int currentLine = 1
    private int currentColumn = 1

    Lexer(String input) {
        this.input = input
    }

    List<Token> tokenize() {
        List<Token> tokens = []
        while (currentPos < input.length()) {
            char currentChar = input[currentPos] as char
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace()
            } else if (currentChar == '/' && peekNextChar() == '/') {
                skipSingleLineComment()
            } else if (currentChar == '/' && peekNextChar() == '*') {
                skipMultiLineComment()
            } else if (Character.isLetter(currentChar)) {
                tokens << scanIdentifier()
            } else if (Character.isDigit(currentChar)) {
                tokens << scanNumber()
            } else if (currentChar == '"') {
                tokens << scanString()
            } else {
                tokens << scanOperator()
            }
        }
        tokens << new Token(Type.EOF, "", currentLine, currentColumn)
        return tokens
    }

    private void skipWhitespace() {
        while (currentPos < input.length() && Character.isWhitespace(input[currentPos] as char)) {
            if (input[currentPos] == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
            currentPos++
        }
    }

    private void skipSingleLineComment() {
        while (currentPos < input.length() && input[currentPos] != '\n') {
            currentPos++
            currentColumn++
        }
        if (currentPos < input.length() && input[currentPos] == '\n') {
            currentLine++
            currentColumn = 1
            currentPos++
        }
    }

    private void skipMultiLineComment() {
        currentPos += 2 // Skip the /* characters
        currentColumn += 2
        while (currentPos < input.length() && !(input[currentPos] == '*' && peekNextChar() == '/')) {
            if (input[currentPos] == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
            currentPos++
        }
        if (currentPos < input.length()) {
            currentPos += 2 // Skip the */ characters
            currentColumn += 2
        }
    }

    private char peekNextChar() {
        return currentPos + 1 < input.length() ? input[currentPos + 1] as char : '\u0000'
    }

    private Token scanIdentifier() {
        int start = currentPos
        while (currentPos < input.length() && Character.isLetterOrDigit(input[currentPos] as char)) {
            currentPos++
            currentColumn++
        }
        String value = input.substring(start, currentPos)
        Type type = [
                "class"   : Type.CLASS,
                "var"     : Type.VAR,
                "const"   : Type.CONST,
                "stat"    : Type.STAT,
                "func"    : Type.FUNC,
                "if"      : Type.IF,
                "else"    : Type.ELSE,
                "for"     : Type.FOR,
                "while"   : Type.WHILE,
                "return"  : Type.RETURN,
                "break"   : Type.BREAK,
                "continue": Type.CONTINUE,
                "do"      : Type.DO,
                "switch"  : Type.SWITCH,
                "case"    : Type.CASE,
                "default" : Type.DEFAULT,
                "int"     : Type.INT,
                "double"  : Type.DOUBLE,
                "long"    : Type.LONG,
                "short"   : Type.SHORT,
                "float"   : Type.FLOAT,
                "byte"    : Type.BYTE,
                "string"  : Type.STRING,
                "bool"    : Type.BOOL,
                "char"    : Type.CHAR,
                "void"    : Type.VOID
        ].get(value, Type.IDENTIFIER)
        new Token(type, value, currentLine, currentColumn)
    }

    private Token scanNumber() {
        int start = currentPos
        while (currentPos < input.length() && Character.isDigit(input[currentPos] as char)) {
            currentPos++
            currentColumn++
        }
        String value = input.substring(start, currentPos)
        new Token(Type.NUMBER, value, currentLine, currentColumn)
    }

    private Token scanString() {
        currentPos++
        currentColumn++
        int start = currentPos
        while (currentPos < input.length() && input[currentPos] != '"') {
            currentPos++
            currentColumn++
        }
        String value = input.substring(start, currentPos)
        currentPos++ // Skip closing quote
        currentColumn++
        new Token(Type.STRING, value, currentLine, currentColumn)
    }

    private Token scanOperator() {
        char currentChar = input[currentPos] as char
        Type type
        if (currentChar == '+') {
            type = Type.PLUS
        } else if (currentChar == '-') {
            type = Type.MINUS
        } else if (currentChar == '*') {
            type = Type.MULTIPLY
        } else if (currentChar == '/') {
            type = Type.DIVIDE
        } else if (currentChar == '=') {
            type = Type.ASSIGN
        } else if (currentChar == '(') {
            type = Type.L_PAR
        } else if (currentChar == ')') {
            type = Type.R_PAR
        } else if (currentChar == '{') {
            type = Type.L_BRACE
        } else if (currentChar == '}') {
            type = Type.R_BRACE
        } else if (currentChar == '[') {
            type = Type.L_BRACKET
        } else if (currentChar == ']') {
            type = Type.R_BRACKET
        } else if (currentChar == ';') {
            type = Type.SEMICOLON
        } else if (currentChar == '.') {
            type = Type.DOT
        } else {
            throw new IllegalArgumentException("Unknown operator: $currentChar")
        }
        currentPos++
        currentColumn++
        new Token(type, currentChar.toString(), currentLine, currentColumn)
    }
}
