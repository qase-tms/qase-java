# [Qase TestOps](https://qase.io) reporters for Java

Monorepo with [Qase TestOps](https://qase.io) reporters for Java testing frameworks.

For all of our reporters, there are two versions:

* The latest v4 series, either already released or in the beta stage.
* The v3 series, stable and receiving only bugfixes.

If you're just starting, pick v4.
If your project is using a v3 reporter, check out the reporter's readme for the migration guide.

| Name                     | Package name                | v4 series                                                                                      | v3 series                                                                                   |
|:-------------------------|:----------------------------|:-----------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **Qase Java Reporters**  |
| Cucumber3                | `qase-cucumber-v3-reporter` | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber-v3-reporter#readme) | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-cucumber3-jvm#readme) |
| Cucumber4                | `qase-cucumber-v4-reporter` | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber-v4-reporter#readme) | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-cucumber4-jvm#readme) |
| Cucumber5                | `qase-cucumber-v5-reporter` | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber-v5-reporter#readme) | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-cucumber5-jvm#readme) |
| Cucumber6                | `qase-cucumber-v6-reporter` | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber-v6-reporter#readme) | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-cucumber6-jvm#readme) |
| Cucumber7                | `qase-cucumber-v7-reporter` | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber-v7-reporter#readme) | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-cucumber7-jvm#readme) |
| Junit4                   | `qase-junit4-reporter`      | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-junit4-reporter#readme)      | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-junit4#readme)        |
| Junit5                   | `qase-junit5-reporter`      | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-junit5-reporter#readme)      | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-junit5#readme)        |
| TestNG                   | `qase-testng-reporter`      | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-testng-reporter#readme)      | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-testng#readme)        |
| **Qase Java SDK**        |
| Common functions library | `qase-java-commons`         | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-java-commons#readme)         | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-api#readme)           |
| Java API client          | `qase-api-client`           | [✅ released](https://github.com/qase-tms/qase-java/tree/main/qase-api-client#readme)           | [🗿deprecated](https://github.com/qase-tms/qase-java/tree/master/qase-api#readme)           |

What each status means:

* The "✅ released" reporters are stable and well-tested versions.
  They will receive more new features as well as bugfixes, should we find bugs.

* The "🧪 open beta" reporters are in active development and rigorous testing.
  It's completely usable (and much more fun to use than v3), but there can be some bugs and minor syntax changes.
  When starting a new test project, the "🧪 open beta" versions are the recommended choice.
  For existing projects, we recommend planning a migration — see the migration section in each
  reporter's readme and try out the new features.

* The "🧰 closed beta" reporters are in active development, and
  can still have major bugs and future syntax changes.
  However, we encourage experimenting with them.
  Your feedback is always welcome.

* The v3 series reporters in the "🗿stable" or "🗿deprecated" status only get some fixes, but no new features.
