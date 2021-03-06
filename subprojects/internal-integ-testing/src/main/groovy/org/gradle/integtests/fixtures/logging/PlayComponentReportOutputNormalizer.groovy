/*
 * Copyright 2018 the original author or authors.
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
package org.gradle.integtests.fixtures.logging

import org.gradle.api.JavaVersion
import org.gradle.samples.executor.ExecutionMetadata
import org.gradle.samples.test.normalizer.OutputNormalizer

/**
 * This normalizer converts native component paths and environment
 * information to look like macOS because samples output currently shows
 * the output for macOS and is not yet capable of adapting dynamically
 * to different toolchains.
 */
class PlayComponentReportOutputNormalizer implements OutputNormalizer {
    @Override
    String normalize(String commandOutput, ExecutionMetadata executionMetadata) {
        return commandOutput.replace("Java SE " + JavaVersion.current().majorVersion, "Java SE 8")
    }
}
