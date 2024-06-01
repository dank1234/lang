package com.dank1234.core.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lexer {
    private final String input;
    private int currentPos = 0;
    private int currentLine = 1;
    private int currentColumn = 1;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentPos < input.length()) {
            char currentChar = input.charAt(currentPos);
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
            } else if (Character.isLetter(currentChar)) {
                tokens.add(scanIdentifier());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(scanNumber());
            } else if (currentChar == '"') {
                tokens.add(scanString());
            } else {
                tokens.add(scanOperator());
            }
        }
        tokens.add(new Token(Type.EOF, "", currentLine, currentColumn));
        return tokens;
    }

    private void skipWhitespace() {
        while (currentPos < input.length() && Character.isWhitespace(input.charAt(currentPos))) {
            if (input.charAt(currentPos) == '\n') {
                currentLine++;
                currentColumn = 1;
            } else {
                currentColumn++;
            }
            currentPos++;
        }
    }

    private Token scanIdentifier() {
        int start = currentPos;
        while (currentPos < input.length() && Character.isLetterOrDigit(input.charAt(currentPos))) {
            currentPos++;
            currentColumn++;
        }
        String value = input.substring(start, currentPos);
        Type type = switch (value) {
            case "class" -> Type.CLASS;
            case "var" -> Type.VAR;
            case "const" -> Type.CONST;
            case "stat" -> Type.STAT;
            case "func" -> Type.FUNC;
            case "if" -> Type.IF;
            case "else" -> Type.ELSE;
            case "for" -> Type.FOR;
            case "while" -> Type.WHILE;
            case "return" -> Type.RETURN;
            case "break" -> Type.BREAK;
            case "continue" -> Type.CONTINUE;
            case "do" -> Type.DO;
            case "switch" -> Type.SWITCH;
            case "case" -> Type.CASE;
            case "default" -> Type.DEFAULT;
            case "int" -> Type.INT;
            case "double" -> Type.DOUBLE;
            case "long" -> Type.LONG;
            case "short" -> Type.SHORT;
            case "float" -> Type.FLOAT;
            case "byte" -> Type.BYTE;
            case "string" -> Type.STRING;
            case "bool" -> Type.BOOL;
            case "char" -> Type.CHAR;
            case "void" -> Type.VOID;
            default -> Type.IDENTIFIER;
        };
        return new Token(type, value, currentLine, currentColumn);
    }

    private Token scanNumber() {
        int start = currentPos;
        while (currentPos < input.length() && Character.isDigit(input.charAt(currentPos))) {
            currentPos++;
            currentColumn++;
        }
        String value = input.substring(start, currentPos);
        return new Token(Type.NUMBER, value, currentLine, currentColumn);
    }

    private Token scanString() {
        currentPos++;
        currentColumn++;
        int start = currentPos;
        while (currentPos < input.length() && input.charAt(currentPos) != '"') {
            currentPos++;
            currentColumn++;
        }
        String value = input.substring(start, currentPos);
        currentPos++; // Skip closing quote
        currentColumn++;
        return new Token(Type.STRING, value, currentLine, currentColumn);
    }

    private Token scanOperator() {
        char currentChar = input.charAt(currentPos);
        Type type = switch (currentChar) {
            case '+' -> Type.PLUS;
            case '-' -> Type.MINUS;
            case '*' -> Type.MULTIPLY;
            case '/' -> Type.DIVIDE;
            case '=' -> Type.ASSIGN;
            case '(' -> Type.L_PAR;
            case ')' -> Type.R_PAR;
            case '{' -> Type.L_BRACE;
            case '}' -> Type.R_BRACE;
            case '[' -> Type.L_BRACKET;
            case ']' -> Type.R_BRACKET;
            case ';' -> Type.SEMICOLON;
            case '.' -> Type.DOT;
            default -> throw new IllegalArgumentException(STR."Unknown operator: \{currentChar}");
        };
        currentPos++;
        currentColumn++;
        return new Token(type, Character.toString(currentChar), currentLine, currentColumn);
    }
}
