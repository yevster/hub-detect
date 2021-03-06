/**
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.hub.detect.codelocation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
// used in 2.0.0
public class CodeLocationNameProvider3 extends CodeLocationNameProvider {
    @Override
    public String generateBomToolName(final CodeLocationName codeLocationName) {
        final String finalSourcePathPiece = detectFileManager.extractFinalPieceFromPath(codeLocationName.getSourcePath());
        final String projectName = codeLocationName.getProjectName();
        final String projectVersionName = codeLocationName.getProjectVersionName();
        final String prefix = codeLocationName.getPrefix();
        final String suffix = codeLocationName.getSuffix();
        final String codeLocationTypeString = codeLocationName.getCodeLocationType().toString().toLowerCase();
        final String bomToolTypeString = codeLocationName.getBomToolType() == null ? "" : codeLocationName.getBomToolType().toString().toLowerCase();

        return createCommonName(finalSourcePathPiece, projectName, projectVersionName, prefix, suffix, codeLocationTypeString, bomToolTypeString);
    }

    @Override
    public String generateScanName(final CodeLocationName codeLocationName) {
        final String cleanedTargetPath = cleanScanTargetPath(codeLocationName);
        final String projectName = codeLocationName.getProjectName();
        final String projectVersionName = codeLocationName.getProjectVersionName();
        final String prefix = codeLocationName.getPrefix();
        final String suffix = codeLocationName.getSuffix();
        final String codeLocationTypeString = codeLocationName.getCodeLocationType().toString().toLowerCase();
        final String bomToolTypeString = "";

        return createCommonName(cleanedTargetPath, projectName, projectVersionName, prefix, suffix, codeLocationTypeString, bomToolTypeString);
    }

    private String createCommonName(final String pathPiece, final String projectName, final String projectVersionName, final String prefix, final String suffix, final String codeLocationType, final String bomToolType) {
        String name = String.format("%s/%s/%s", pathPiece, projectName, projectVersionName);
        if (StringUtils.isNotBlank(prefix)) {
            name = String.format("%s/%s", prefix, name);
        }
        if (StringUtils.isNotBlank(suffix)) {
            name = String.format("%s/%s", name, suffix);
        }

        String endPiece = codeLocationType;
        if (StringUtils.isNotBlank(bomToolType)) {
            endPiece = String.format("%s/%s", bomToolType, endPiece);
        }

        name = String.format("%s %s", name, endPiece);
        return name;
    }

}
