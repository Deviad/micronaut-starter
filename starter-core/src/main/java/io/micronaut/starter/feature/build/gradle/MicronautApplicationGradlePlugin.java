/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.build.gradle;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.build.gradle.GradlePlugin;
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.template.RockerWritable;
import io.micronaut.starter.feature.build.gradle.templates.micronautGradle;
import java.util.ArrayList;
import java.util.List;

public class MicronautApplicationGradlePlugin {

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private static final String ID = "io.micronaut.application";
        private static final String ARTIFACT_ID = "micronaut-gradle-plugin";

        private Dockerfile dockerfileNative;
        private Dockerfile dockerfile;
        private List<String> dockerBuildImages;
        private List<String> dockerBuildNativeImages;
        private BuildTool buildTool;

        public Builder buildTool(BuildTool buildTool) {
            this.buildTool = buildTool;
            return this;
        }

        public Builder dockerNative(Dockerfile dockerfileNative) {
            this.dockerfileNative = dockerfileNative;
            return this;
        }

        public Builder docker(Dockerfile dockerfile) {
            this.dockerfile = dockerfile;
            return this;
        }

        public Builder dockerBuildImage(String image) {
            if (dockerBuildImages == null) {
                dockerBuildImages = new ArrayList<>();
            }
            dockerBuildImages.add(image);
            return this;
        }

        public Builder dockerBuildNativeImage(String image) {
            if (dockerBuildNativeImages == null) {
                dockerBuildNativeImages = new ArrayList<>();
            }
            dockerBuildNativeImages.add(image);
            return this;
        }

        public GradlePlugin build() {
            return GradlePlugin.builder()
                    .id(ID)
                    .lookupArtifactId(ARTIFACT_ID)
                    .extension(new RockerWritable(micronautGradle.template(buildTool, dockerfile, dockerfileNative, dockerBuildImages, dockerBuildNativeImages)))
                    .build();
        }
    }

}
