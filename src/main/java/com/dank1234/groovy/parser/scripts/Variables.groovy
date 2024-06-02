package com.dank1234.groovy.parser.scripts

import com.dank1234.core.Token
import com.dank1234.core.Type
import com.dank1234.groovy.parser.Parser

class Variables {
    public final Map<String, Object> variables = [:]
    public final Map<String, Object> constants = [:]
    public final Map<String, Object> staticVars = [:]

    final Parser parser
    Variables(Parser parser) {
        this.parser = parser
    }

    void parseVariableDeclaration() {
        Token typeToken = parser.consumeType()
        Token nameToken = parser.consume(Type.IDENTIFIER, "Expected variable name.")
        parser.consume(Type.ASSIGN, "Expected '=' after variable name.")
        Object value = parser.parseExpression()
        parser.consume(Type.SEMICOLON, "Expected ';' after variable declaration.")
        variables[nameToken.value] = value
    }
    void parseConstantDeclaration() {
        Token typeToken = parser.consumeType()
        Token nameToken = parser.consume(Type.IDENTIFIER, "Expected constant name.")
        parser.consume(Type.ASSIGN, "Expected '=' after constant name.")
        Object value = parser.parseExpression()
        parser.consume(Type.SEMICOLON, "Expected ';' after constant declaration.")
        constants[nameToken.value] = value
    }
    void parseStaticDeclaration() {
        Token typeToken = parser.consumeType()
        Token nameToken = parser.consume(Type.IDENTIFIER, "Expected static variable name.")
        parser.consume(Type.ASSIGN, "Expected '=' after static variable name.")
        Object value = parser.parseExpression()
        parser.consume(Type.SEMICOLON, "Expected ';' after static variable declaration.")
        staticVars[nameToken.value] = value
    }
}
