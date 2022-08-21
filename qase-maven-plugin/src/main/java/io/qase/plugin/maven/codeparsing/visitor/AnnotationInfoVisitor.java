package io.qase.plugin.maven.codeparsing.visitor;

import io.qase.plugin.maven.codeparsing.model.AnnotationInfo;
import io.qase.plugin.maven.codeparsing.CodeParsingConstants;
import org.objectweb.asm.AnnotationVisitor;

public class AnnotationInfoVisitor extends AnnotationVisitor {

    private final AnnotationInfo annotationInfo;

    public AnnotationInfoVisitor(AnnotationInfo annotationInfo) {
        super(CodeParsingConstants.ASM_API_VERSION);
        this.annotationInfo = annotationInfo;
    }

    @Override
    public void visit(String name, Object value) {
        annotationInfo.addAttribute(name, value);
    }
}
