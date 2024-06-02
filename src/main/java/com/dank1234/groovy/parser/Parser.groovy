package com.dank1234.groovy.parser;

import com.dank1234.core.tokenization.Token
import com.dank1234.core.tokenization.Type
import com.dank1234.groovy.parser.scripts.Classes
import com.dank1234.groovy.parser.scripts.Methods
import com.dank1234.groovy.parser.scripts.Statements
import com.dank1234.groovy.parser.scripts.Variables

class Parser {
    public final Statements statements = new Statements(this)
    public final Variables variables = new Variables(this)
    public final Methods methods = new Methods(this)
    public final Classes classes = new Classes(this)

    final List<Token> tokens
    int currentPos = 0

    Parser(List<Token> tokens) {
        this.tokens = tokens
    }

    void parse() {
        println tokens
        while (!isAtEnd()) {
            classes.parseClass()
        }    
    }
    
    Object parseExpression() {
        return parseTerm()
    }
    Object parseTerm() {
        Object left = parseFactor()
        while (match(Type.PLUS, Type.MINUS)) {
            Token operator = previous()
            Object right = parseFactor()
            if (operator.type == Type.PLUS) {
                left = left + right
            } else if (operator.type == Type.MINUS) {
                left = left - right
            }
        }
        return left
    }
    Object parseFactor() {
        Object left = parsePrimary()
        while (match(Type.MULTIPLY, Type.DIVIDE)) {
            Token operator = previous()
            Object right = parsePrimary()
            if (operator.type == Type.MULTIPLY) {
                left = left * right
            } else if (operator.type == Type.DIVIDE) {
                left = left / right
            }
        }
        return left
    }
    Object parsePrimary() {
        if (match(Type.NUMBER)) {
            return Double.parseDouble(previous().value)
        } else if (match(Type.STRING)) {
            return previous().value
        } else if (match(Type.IDENTIFIER)) {
            String name = previous().value
            if (variables.variables.containsKey(name)) {
                return variables.variables[name]
            } else if (variables.constants.containsKey(name)) {
                return variables.constants[name]
            } else if (variables.staticVars.containsKey(name)) {
                return variables.staticVars[name]
            } else if (match(Type.L_PAR)) {
                return callMethod(name)
            } else {
                throw new IllegalArgumentException("Variable '$name' is not declared.")
            }
        } else if (match(Type.L_PAR)) {
            Object expr = parseExpression()
            consume(Type.R_PAR, "Expected ')' after expression.")
            return expr
        } else {
            throw new IllegalArgumentException("Unexpected token: ${peek().type}")
        }
    }
    Object callMethod(String methodName) {
        if (!methods.methods.containsKey(methodName)) {
            throw new IllegalArgumentException("Method '$methodName' is not declared.")
        }
        consume(Type.L_PAR, "Expected '(' after method name.")
        consume(Type.R_PAR, "Expected ')' after method call.")
        return methods[methodName].call()
    }

    String interpolateString(String value) {
        value.replaceAll(/\$\{(\w+)\}/) { match, varName ->
            if (variables.variables.containsKey(varName)) {
                return variables.variables[varName].toString()
            } else if (variables.constants.containsKey(varName)) {
                return variables.constants[varName].toString()
            } else if (variables.staticVars.containsKey(varName)) {
                return variables.staticVars[varName].toString()
            } else {
                throw new IllegalArgumentException("Variable '${varName}' is not declared.")
            }
        }
    }
    
    boolean match(Type... types) {
        for (Type type : types) {
            if (check(type)) {
                advance()
                return true
            }
            return false
        }
    }
    boolean check(Type type) {
        if (isAtEnd()) return false
        return peek().type == type
    }
    Token advance() {
        if (!isAtEnd()) currentPos++
        return previous()
    }
    boolean isAtEnd() {
        return peek().type == Type.EOF
    }
    Token peek() {
        return tokens[currentPos]
    }
    Token previous() {
        return tokens[currentPos - 1]
    }
    Token consume(Type type, String errorMessage) {
        if (check(type)) return advance()
        throw new IllegalArgumentException(errorMessage)
    }
    Token consumeType() {
        if (match(Type.INT, Type.FLOAT, Type.DOUBLE, Type.LONG, Type.SHORT, Type.BYTE, Type.STRING, Type.BOOL, Type.CHAR, Type.VOID)) {
            return previous()
        }
        throw new IllegalArgumentException("Expected type keyword.")
    }
    boolean checkIdentifier(String identifier) {
        if (isAtEnd()) return false
        return peek().type == Type.IDENTIFIER && peek().value == identifier
    }
}
