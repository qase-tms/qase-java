package io.qase.plugin.maven.codeparsing;

import io.qase.plugin.maven.codeparsing.model.ClassInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * @see MethodFilter
 * */
public interface ClassParser {

    ClassInfo parseCompiledClass(InputStream compiledClassInputStream) throws IOException;
}
