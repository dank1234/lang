package com.dank1234.groovy.parser.scripts

import com.dank1234.core.tokenization.Type
import com.dank1234.groovy.parser.Parser

class Statements {
    final Parser parser
    Statements(Parser parser) {
        this.parser = parser
    }

    Object returnValue = null

    private void parseStatement() {
        if (parser.checkIdentifier("println")) {
            parsePrintStatement()
        } else if (parser.match(Type.RETURN)) {
            parseReturnStatement()
        } else {
            parseExpressionStatement()
        }
    }
    private void parsePrintStatement() {
        parser.advance()
        parser.consume(Type.L_PAR, "Expected '(' after 'println'.")
        String value = parser.parseExpression()
        parser.consume(Type.R_PAR, "Expected ')' after expression.")
        parser.consume(Type.SEMICOLON, "Expected ';' after 'println'.")
        println(parser.interpolateString(value))
    }
    private void parseReturnStatement() {
        returnValue = parser.parseExpression()
        parser.consume(Type.SEMICOLON, "Expected ';' after return value.")
    }
    private void parseExpressionStatement() {
        parser.parseExpression()
        parser.consume(Type.SEMICOLON, "Expected ';' after expression.")
    }
}
