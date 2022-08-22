package io.qase.plugin.codeparsing.visitor;

import io.qase.plugin.codeparsing.CodeParsingConstants;
import io.qase.plugin.codeparsing.model.AnnotationInfo;
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
