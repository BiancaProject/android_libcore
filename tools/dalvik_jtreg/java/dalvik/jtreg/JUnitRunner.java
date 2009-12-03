/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dalvik.jtreg;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.TestSuiteLoader;

/**
 * Runs a JUnit test.
 */
public final class JUnitRunner extends TestRunner {

    private final junit.textui.TestRunner testRunner;
    private Test junitTest;

    public JUnitRunner() {
        final TestSuiteLoader testSuiteLoader = new TestSuiteLoader() {
            public Class load(String suiteClassName) throws ClassNotFoundException {
                return JUnitRunner.class.getClassLoader().loadClass(suiteClassName);
            }

            public Class reload(Class c) {
                return c;
            }
        };

        testRunner = new junit.textui.TestRunner() {
            @Override public TestSuiteLoader getLoader() {
                return testSuiteLoader;
            }
        };
    }

    @Override public void prepareTest() {
        junitTest = testRunner.getTest(className);
    }

    @Override public boolean test() {
        TestResult result = testRunner.doRun(junitTest);
        return result.wasSuccessful();
    }

    public static void main(String[] args) throws Exception {
        new JUnitRunner().run();
    }
}