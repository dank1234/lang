package com.dank1234.core.lexer;

public enum Type {
    IDENTIFIER,

    CLASS, VAR,
    CONST, STAT,
    FUNC,

    IF, ELSE,
    FOR, WHILE,
    RETURN, BREAK,
    CONTINUE, DO,
    SWITCH, CASE,
    DEFAULT,

    INT, DOUBLE,
    LONG, SHORT,
    FLOAT,BYTE,
    STRING, BOOL,
    CHAR, VOID,

    L_PAR, R_PAR,
    L_BRACE, R_BRACE,
    L_BRACKET, R_BRACKET,
    SEMICOLON, DOT,

    PLUS, MULTIPLY,
    DIVIDE, MINUS,
    NUMBER, ASSIGN,

    EOF
}
