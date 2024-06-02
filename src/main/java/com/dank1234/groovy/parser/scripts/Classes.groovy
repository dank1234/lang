package com.dank1234.groovy.parser.scripts

import com.dank1234.core.Type
import com.dank1234.groovy.parser.Parser

class Classes {
    final Parser parser
    Classes(Parser parser) {
        this.parser = parser
    }

    void parseClass() {
        parser.consume(Type.CLASS, "Expected 'class' keyword.")
        parser.consume(Type.IDENTIFIER, "Expected class name.")
        parser.consume(Type.L_BRACE, "Expected '{' after class name.")

        while (!parser.check(Type.R_BRACE) && !parser.isAtEnd()) {
            parseClassMember()
        }

        parser.consume(Type.R_BRACE, "Expected '}' after class body.")
    }
    void parseClassMember() {
        if (parser.match(Type.VAR)) {
            parser.variables.parseVariableDeclaration()
        }
        else if (parser.match(Type.CONST)) {
            parser.variables.parseConstantDeclaration()
        }
        else if (parser.match(Type.STAT)) {
            parser.variables.parseStaticDeclaration()
        }
        else if (parser.match(Type.FUNC)) {
            parser.methods.parseFunction()
        }
        else {
            throw new IllegalArgumentException("Unexpected token in class: ${parser.peek().type}")
        }
    }
}
