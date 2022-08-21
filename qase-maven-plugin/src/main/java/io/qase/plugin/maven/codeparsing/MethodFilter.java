package io.qase.plugin.maven.codeparsing;

import io.qase.plugin.maven.codeparsing.model.ClassInfo;
import io.qase.plugin.maven.codeparsing.criteria.MethodInfoCriteria;
import io.qase.plugin.maven.codeparsing.model.MethodInfo;

import java.util.Collection;

/**
 * @see ClassParser
 * */
public interface MethodFilter {

    Collection<MethodInfo> filterMethodsByCriteria(ClassInfo classInfo, MethodInfoCriteria methodInfoCriteria);
}
