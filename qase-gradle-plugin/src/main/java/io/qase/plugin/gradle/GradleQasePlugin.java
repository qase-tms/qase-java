package io.qase.plugin.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

import static io.qase.plugin.QasePluginExecutableTemplate.QASE_TEMPLATE_NAME;

public class GradleQasePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks()
            .register(QASE_TEMPLATE_NAME, QaseTest.class)
            .configure(qaseTest -> qaseTest.finalizedBy(JavaPlugin.TEST_TASK_NAME)
            .dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME));
    }
}
