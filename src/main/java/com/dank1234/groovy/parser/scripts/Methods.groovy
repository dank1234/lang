package com.dank1234.groovy.parser.scripts

import com.dank1234.core.Token
import com.dank1234.core.Type
import com.dank1234.groovy.parser.Parser

class Methods {
    final Parser parser
    Methods(Parser parser) {
        this.parser = parser
    }

    final Map<String, Closure> methods = [:]
    void parseFunction() {
        Token returnType = parser.consumeType()
        Token nameToken = parser.consume(Type.IDENTIFIER, "Expected function name.")
        parser.consume(Type.L_PAR, "Expected '(' after function name.")
        parser.consume(Type.R_PAR, "Expected ')' after function parameters.")
        parser.consume(Type.L_BRACE, "Expected '{' before function body.")

        List<Token> bodyTokens = []
        while (!parser.check(Type.R_BRACE) && !parser.isAtEnd()) {
            bodyTokens << parser.advance()
        }

        parser.consume(Type.R_BRACE, "Expected '}' after function body.")

        Map<String, Object> capturedVariables = new HashMap<>(parser.variables.variables)
        Map<String, Object> capturedConstants = new HashMap<>(parser.variables.constants)
        Map<String, Object> capturedStaticVars = new HashMap<>(parser.variables.staticVars)

        methods[nameToken.value] = { ->
            Parser methodParser = new Parser(bodyTokens)
            methodParser.variables.variables.putAll(capturedVariables)
            methodParser.variables.constants.putAll(capturedConstants)
            methodParser.variables.staticVars.putAll(capturedStaticVars)
            methodParser.methods.methods.putAll(methods)
            methodParser.parse()
            return methodParser.statements.returnValue
        }
    }
}
