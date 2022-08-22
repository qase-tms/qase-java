package io.qase.plugin.codeparsing.visitor;

import io.qase.plugin.codeparsing.CodeParsingConstants;
import io.qase.plugin.codeparsing.model.ClassInfo;
import io.qase.plugin.codeparsing.model.MethodInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassInfoVisitor extends ClassVisitor {

    private final ClassInfo classInfo;

    public ClassInfoVisitor(ClassInfo classInfo) {
        super(CodeParsingConstants.ASM_API_VERSION);
        this.classInfo = classInfo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        classInfo.setCanonicalName(name.replace('/', '.'));
    }

    @Override
    public MethodVisitor visitMethod(
        int access, String name, String desc, String signature, String[] exceptions
    ) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setMethodName(name);
        classInfo.addMethod(methodInfo);
        return new MethodInfoVisitor(methodInfo);
    }
}
