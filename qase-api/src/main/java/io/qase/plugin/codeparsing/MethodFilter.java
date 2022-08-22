package io.qase.plugin.codeparsing;

import io.qase.plugin.codeparsing.model.ClassInfo;
import io.qase.plugin.codeparsing.criteria.MethodInfoCriteria;
import io.qase.plugin.codeparsing.model.MethodInfo;

import java.util.Collection;

/**
 * @see ClassParser
 * */
public interface MethodFilter {

    Collection<MethodInfo> filterMethodsByCriteria(ClassInfo classInfo, MethodInfoCriteria methodInfoCriteria);
}
