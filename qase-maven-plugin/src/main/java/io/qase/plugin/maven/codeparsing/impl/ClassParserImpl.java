package io.qase.plugin.maven.codeparsing.impl;

import io.qase.plugin.maven.codeparsing.model.ClassInfo;
import io.qase.plugin.maven.codeparsing.ClassParser;
import io.qase.plugin.maven.codeparsing.visitor.ClassInfoVisitor;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

public class ClassParserImpl implements ClassParser {

    private static final int DEFAULT_OPTIONS = 0;

    @Override
    public ClassInfo parseCompiledClass(InputStream compiledClassInputStream) throws IOException {
        ClassReader classReader = new ClassReader(compiledClassInputStream);
        ClassInfo classInfo = new ClassInfo();
        ClassInfoVisitor classInfoVisitor = new ClassInfoVisitor(classInfo);
        classReader.accept(classInfoVisitor, DEFAULT_OPTIONS);
        return classInfo;
    }
}
