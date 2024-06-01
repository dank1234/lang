package com.dank1234.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class GroovyUtils {

    public static void runMain(final String file) {
        try {
            final String filePath = file.endsWith(".groovy") ?
                    STR."src/main/java/com/dank1234/groovy/\{file}" :
                    STR."src/main/java/com/dank1234/groovy/\{file}.groovy";

            final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
            final Class<?> groovyClass = groovyClassLoader.parseClass(filePath);
            final Object groovyObject = groovyClass.newInstance();
            groovyClass.getMethod("main").invoke(groovyObject);

        }catch(Exception _){}
    }

    public static Object runFunc(String file, String func, Object... args) {
        try {
            String filePath = file.endsWith(".groovy") ?
                    "src/main/java/com/dank1234/groovy/" + file :
                    "src/main/java/com/dank1234/groovy/" + file + ".groovy";

            GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
            Class<?> groovyClass = groovyClassLoader.parseClass(filePath);
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();

            Object[] methodArgs = new Object[args.length];
            Class<?>[] methodArgTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                methodArgs[i] = args[i];
                methodArgTypes[i] = args[i].getClass();
            }

            return groovyObject.invokeMethod(func, methodArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
