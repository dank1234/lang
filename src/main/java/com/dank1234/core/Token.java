package com.dank1234.core;

public record Token(Type type, String value, int line, int column) {
    @Override
    public String toString() {
        return STR."Token {\n\ttype=`\{type}`, \n\tvalue=`\{value}`, \n\tline=`\{line}`, \n\tcolumn=`\{column}`\n},";
    }
}