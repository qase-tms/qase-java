package io.qase.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import io.qase.guice.module.DefaultImplementationsModule;
import io.qase.guice.module.QaseModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Injectors {

    public static Injector createDefaultInjector() {
        return Guice.createInjector(combineQaseApiCoreModules());
    }

    public static Injector createDefaultInjectorWithOverridingModules(Module... customModules) {
        return Guice.createInjector(
            Modules.override(combineQaseApiCoreModules()).with(customModules)
        );
    }

    private static Module combineQaseApiCoreModules() {
        return Modules.combine(new QaseModule(), new DefaultImplementationsModule());
    }
}
