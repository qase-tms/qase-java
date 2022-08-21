package io.qase.plugin.maven.codeparsing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.objectweb.asm.Opcodes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeParsingConstants {

    public static final int ASM_API_VERSION = Opcodes.ASM9;
}
