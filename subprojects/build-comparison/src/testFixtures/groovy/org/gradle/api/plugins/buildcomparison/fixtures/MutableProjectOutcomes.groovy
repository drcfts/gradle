/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.plugins.buildcomparison.fixtures

import org.gradle.api.internal.ConfigureUtil
import org.gradle.tooling.model.DomainObjectSet
import org.gradle.tooling.model.internal.outcomes.GradleBuildOutcome
import org.gradle.tooling.model.internal.outcomes.GradleFileBuildOutcome
import org.gradle.tooling.model.internal.outcomes.ProjectOutcomes

class MutableProjectOutcomes implements ProjectOutcomes {
    String name
    String description
    String path
    MutableProjectOutcomes parent
    DomainObjectSet<MutableProjectOutcomes> children = new MutableDomainObjectSet()
    File projectDirectory
    DomainObjectSet<? extends GradleBuildOutcome> outcomes = new MutableDomainObjectSet()

    MutableProjectOutcomes createChild(String childName, Closure c = {}) {
        def mpo = new MutableProjectOutcomes()
        mpo.parent = this
        mpo.name = childName
        mpo.path = parent ? "$path:$childName" : ":$childName"
        mpo.projectDirectory = new File(projectDirectory, childName)
        mpo.description = "project $mpo.path"

        children << mpo
        ConfigureUtil.configure(c, mpo)
    }

    GradleFileBuildOutcome addFile(String archivePath, String typeIdentifier = null, String taskName = archivePath) {
        def outcome = new GradleFileBuildOutcome() {

            String getId() {
                archivePath
            }

            String getDescription() {
                "fake outcome $archivePath"
            }

            File getFile() {
                new File(projectDirectory, archivePath)
            }

            String getTaskPath() {
                taskName ? "$path:$taskName" : ''
            }

            String getTypeIdentifier() {
                typeIdentifier
            }
        }
        outcomes << outcome
        outcome
    }
}
