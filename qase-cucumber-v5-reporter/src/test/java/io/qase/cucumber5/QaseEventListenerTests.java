//package io.qase.cucumber5;
//
//
//import com.github.tomakehurst.wiremock.WireMockServer;
//import io.cucumber.core.cli.Main;
//import io.qase.api.utils.TestUtils;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
//import static io.qase.api.utils.TestUtils.useBulk;
//
//class QaseEventListenerTests {
//    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));
//
//    @BeforeAll
//    static void setUp() {
//        configureFor(8088);
//        wireMockServer.start();
//        TestUtils.setupQaseTestEnvironmentVariables(wireMockServer.port());
//    }
//
//    @AfterAll
//    static void tearDown() {
//        wireMockServer.stop();
//    }
//
//    @AfterEach
//    void resetRequests() {
//        wireMockServer.resetRequests();
//    }
//
//    @Test
//    void newCase() {
//        useBulk(false);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/new_case.feature"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"case\" : {\n" +
//                        "    \"title\" : \"Failed scenario\"\n" +
//                        "  },\n" +
//                        "  \"status\" : \"failed\",\n" +
//                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "  \"defect\" : true,\n" +
//                        "  \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "  \"steps\" : [ {\n" +
//                        "    \"position\" : 1,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"action\" : \"Given success step\"\n" +
//                        "  }, {\n" +
//                        "    \"position\" : 2,\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"attachments\" : \"${json-unit.ignore}\"," +
//                        "    \"action\" : \"Given failed step\"\n" +
//                        "  } ]\n" +
//                        "}")));
//    }
//
//    @Test
//    void bulk() {
//        useBulk(true);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/",
//                "--threads", "4"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"results\" : [ {\n" +
//                        "    \"case_id\" : 123,\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : true,\n" +
//                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "    \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"failed\",\n" +
//                        "      \"attachments\" : \"${json-unit.ignore}\"," +
//                        "      \"action\" : \"Given failed step\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case_id\" : 123,\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : true,\n" +
//                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "    \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"When timeout 3 seconds\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"failed\",\n" +
//                        "      \"attachments\" : \"${json-unit.ignore}\"," +
//                        "      \"action\" : \"Given failed step\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"Failed scenario\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : true,\n" +
//                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "    \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"failed\",\n" +
//                        "      \"attachments\" : \"${json-unit.ignore}\"," +
//                        "      \"action\" : \"Given failed step\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case_id\" : 123,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case_id\" : 123,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given timeout 5 seconds\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"success with Positive Examples\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"param\" : {\n" +
//                        "      \"a\" : \"\\\"1\\\"\",\n" +
//                        "      \"b\" : \"\\\"2\\\"\"\n" +
//                        "    },\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"1\\\"\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"2\\\"\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"success with Positive Examples\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"param\" : {\n" +
//                        "      \"a\" : \"\\\"3\\\"\",\n" +
//                        "      \"b\" : \"\\\"4\\\"\"\n" +
//                        "    },\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"3\\\"\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"4\\\"\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"success with Positive Examples\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"param\" : {\n" +
//                        "      \"a\" : \"\\\"5\\\"\",\n" +
//                        "      \"b\" : \"\\\"6\\\"\"\n" +
//                        "    },\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"5\\\"\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"6\\\"\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"success with Positive Examples\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"param\" : {\n" +
//                        "      \"a\" : \"\\\"7\\\"\",\n" +
//                        "      \"b\" : \"\\\"8\\\"\"\n" +
//                        "    },\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"7\\\"\"\n" +
//                        "    }, {\n" +
//                        "      \"position\" : 2,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step with parameter \\\"8\\\"\"\n" +
//                        "    } ]\n" +
//                        "  }, {\n" +
//                        "    \"case\" : {\n" +
//                        "      \"title\" : \"Success scenario\"\n" +
//                        "    },\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "    \"defect\" : false,\n" +
//                        "    \"steps\" : [ {\n" +
//                        "      \"position\" : 1,\n" +
//                        "      \"status\" : \"passed\",\n" +
//                        "      \"action\" : \"Given success step\"\n" +
//                        "    } ]\n" +
//                        "  } ]\n" +
//                        "}", true, false)));
//    }
//
//    @Test
//    void success() {
//        useBulk(false);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/success.feature"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"case_id\" : 123,\n" +
//                        "  \"status\" : \"passed\",\n" +
//                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "  \"defect\" : false,\n" +
//                        "  \"steps\" : [ {\n" +
//                        "    \"position\" : 1,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"action\" : \"Given success step\"\n" +
//                        "  } ]" +
//                        "}")));
//
//    }
//
//    @Test
//    void successWithTime() {
//        useBulk(false);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/success_with_time.feature"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"case_id\" : 123,\n" +
//                        "  \"status\" : \"passed\",\n" +
//                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "  \"defect\" : false,\n" +
//                        "  \"steps\" : [ {\n" +
//                        "    \"position\" : 1,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"action\" : \"Given timeout 5 seconds\"\n" +
//                        "  }, {\n" +
//                        "    \"position\" : 2,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"action\" : \"Given success step\"\n" +
//                        "  } ]\n" +
//                        "}")));
//    }
//
//    @Test
//    void failed() {
//        useBulk(false);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/failed.feature"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"case_id\" : 123,\n" +
//                        "  \"status\" : \"failed\",\n" +
//                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "  \"defect\" : true,\n" +
//                        "  \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "  \"steps\" : [ {\n" +
//                        "    \"position\" : 1,\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"attachments\" : \"${json-unit.ignore}\"," +
//                        "    \"action\" : \"Given failed step\"\n" +
//                        "  } ]\n" +
//                        "}")));
//    }
//
//    @Test
//    void failedWithTime() {
//        useBulk(false);
//        String[] args = new String[]{
//                "-g", "io.qase.cucumber5",
//                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
//                "classpath:features/failed_with_time.feature"
//        };
//        Main.run(args, Thread.currentThread().getContextClassLoader());
//
//        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
//                .withHeader("Token", equalTo("secret-token"))
//                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
//                .withRequestBody(equalToJson("{\n" +
//                        "  \"case_id\" : 123,\n" +
//                        "  \"status\" : \"failed\",\n" +
//                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
//                        "  \"defect\" : true,\n" +
//                        "  \"stacktrace\" : \"${json-unit.ignore}\",\n" +
//                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
//                        "  \"steps\" : [ {\n" +
//                        "    \"position\" : 1,\n" +
//                        "    \"status\" : \"passed\",\n" +
//                        "    \"action\" : \"When timeout 3 seconds\"\n" +
//                        "  }, {\n" +
//                        "    \"position\" : 2,\n" +
//                        "    \"status\" : \"failed\",\n" +
//                        "    \"attachments\" : \"${json-unit.ignore}\"," +
//                        "    \"action\" : \"Given failed step\"\n" +
//                        "  } ]\n" +
//                        "}")));
//    }
//}
