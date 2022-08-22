package io.qase.plugin.codeparsing;

import io.qase.plugin.codeparsing.model.ClassInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * @see MethodFilter
 * */
public interface ClassParser {

    ClassInfo parseCompiledClass(InputStream compiledClassInputStream) throws IOException;
}
