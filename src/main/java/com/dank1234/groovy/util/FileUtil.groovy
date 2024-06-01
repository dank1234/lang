package com.dank1234.groovy.util

class FileUtil {
    static String readFileAsString(String filePath) {
        return new File(filePath).text
    }
}

