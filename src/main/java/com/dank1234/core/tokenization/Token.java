package com.dank1234.core.tokenization;

public record Token(Type type, String value, int line, int column) {
    @Override
    public String toString() {
        return STR."Token {\n\ttype=`\{type}`, \n\tvalue=`\{value}`, \n\tline=`\{line}`, \n\tcolumn=`\{column}`\n},";
    }
    public String toFile() {
        return STR."\t{\n\t\t\"type\": \"\{type}\", \n\t\t\"value\": \"\{value}\", \n\t\t\"line\": \"\{line}\", \n\t\t\"column\": \"\{column}\"\n\t}";
    }
}