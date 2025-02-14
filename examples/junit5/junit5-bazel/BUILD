load("//:junit5.bzl", "java_junit5_test")
load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name="test-lib",
    srcs = glob(["src/test/java/**/*.java"]),
    data = glob([
                "qase.config.json", # copy config to Bazsel sandbox
            ]),
    resources = glob([
                "src/test/java/resources/**/*",
            ]),
    visibility = ["//visibility:public"],
    deps= [
        "@maven//:org_junit_jupiter_junit_jupiter_api",
        "@maven//:org_junit_jupiter_junit_jupiter_params",
        "@maven//:org_junit_jupiter_junit_jupiter_engine",
        "@maven//:io_qase_qase_junit5_reporter",
        "@maven//:io_qase_qase_java_commons",
        "@maven//:org_aspectj_aspectjweaver",
        "@maven//:org_aspectj_aspectjrt",
    ],
)

java_junit5_test(
    name = "junit5-test",
    srcs = glob(["src/test/java/**/*.java"]),
    test_packages = ["example"],
    deps = [
        ":test-lib",
        "@maven//:io_qase_qase_junit5_reporter",
        "@maven//:io_qase_qase_java_commons",
        "@maven//:org_aspectj_aspectjweaver",
        "@maven//:org_aspectj_aspectjrt",
    ],
    runtime_deps = [],
    env = {
        # Applies aspects for the correct operation of steps in tests. Specify the path for your local repository.
        "JAVA_TOOL_OPTIONS": "-javaagent:/Users/gda/.m2/repository/org/aspectj/aspectjweaver/1.9.22/aspectjweaver-1.9.22.jar",
    },
)
