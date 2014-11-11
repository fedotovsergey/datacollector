/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.runner.preview;

import com.codahale.metrics.MetricRegistry;
import com.streamsets.pipeline.runner.StageOutput;
import com.streamsets.pipeline.validation.Issues;

import java.util.List;
import java.util.Locale;

public class PreviewPipelineOutput {
  private final Issues issues;
  private final MetricRegistry metrics;
  private final List<List<StageOutput>> batchesOutput;
  private String sourceOffset;
  private String newSourceOffset;
  private Locale locale;

  public PreviewPipelineOutput(Issues issues, PreviewPipelineRunner runner) {
    this.issues = issues;
    this.metrics = runner.getMetrics();
    this.batchesOutput = runner.getBatchesOutput();
    this.sourceOffset = runner.getSourceOffset();
    this.newSourceOffset = runner.getNewSourceOffset();
  }

  public void setLocale(Locale locale) {
    issues.setLocale(locale);
  }

  public Issues getIssues() {
    return issues;
  }

  public MetricRegistry getMetrics() {
    return metrics;
  }

  public List<List<StageOutput>> getBatchesOutput() {
    for (List<StageOutput> batchOutput : batchesOutput) {
      for (StageOutput stageOutput : batchOutput) {
        stageOutput.setLocale(locale);
      }
    }
    return batchesOutput;
  }

  public String getSourceOffset() {
    return sourceOffset;
  }

  public String getNewSourceOffset() {
    return newSourceOffset;
  }

}
