def java_junit5_test(name, srcs, test_packages = [], deps = [], runtime_deps = [], **kwargs):
    FILTER_KWARGS = [
        "main_class",
        "use_testrunner",
        "args",
    ]

    for arg in FILTER_KWARGS:
        if arg in kwargs.keys():
            kwargs.pop(arg)

    if len(test_packages) == 0:
        fail("must specify non-empty 'test_packages'")

    junit_console_args = ["execute"]
    for test_package in test_packages:
            junit_console_args += ["--select-package", test_package]

    junit_console_args  += ["--disable-banner", "--fail-if-no-tests"]

    native.java_test(
        name = name,
        srcs = srcs,
        use_testrunner = False,
        main_class = "org.junit.platform.console.ConsoleLauncher",
        args = junit_console_args,
        deps = deps + [
             "@maven//:org_junit_jupiter_junit_jupiter_api",
             "@maven//:org_junit_jupiter_junit_jupiter_params",
             "@maven//:org_junit_jupiter_junit_jupiter_engine",
        ],
        runtime_deps = runtime_deps + [
             "@maven//:org_junit_platform_junit_platform_console_standalone",
        ],
        **kwargs
    )
