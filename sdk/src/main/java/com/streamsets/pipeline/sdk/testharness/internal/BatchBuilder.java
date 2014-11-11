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
package com.streamsets.pipeline.sdk.testharness.internal;

import com.streamsets.pipeline.api.Batch;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.runner.BatchImpl;
import com.streamsets.pipeline.runner.ErrorRecordSink;
import com.streamsets.pipeline.sdk.testharness.RecordProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to build Batches containing records
 * generated by the {@link com.streamsets.pipeline.sdk.testharness.RecordProducer}
 * argument.
 */
public class BatchBuilder {

  private final RecordProducer recordProducer;

  /*Source offset*/
  private String sourceOffset = null;
  /*Max records in a batch*/
  private int maxBatchSize = 0;

  public BatchBuilder(RecordProducer recordProducer) {
    this.recordProducer = recordProducer;
  }

  /**
   * Sets source off set property
   * @param sourceOffset
   */
  public void setSourceOffset(String sourceOffset) {
    this.sourceOffset = sourceOffset;
  }

  /**
   * Sets the maxBatchSize property
   * @param maxBatchSize
   */
  public void setMaxBatchSize(int maxBatchSize) {
    this.maxBatchSize = maxBatchSize;
  }

  /**
   * Builds a batch containing {@link #maxBatchSize} number of records.
   * Records are generated using the configured
   * {@link com.streamsets.pipeline.sdk.testharness.RecordProducer} instance
   *
   * @return the generated batch
   */
  public Batch build() {
    List<Record> records = new ArrayList<Record>();
    for(int i = 0; i < maxBatchSize; i++) {
      records.add(recordProducer.produce());
    }
    return new BatchImpl("instance", new SourceOffsetTrackerImpl(null), new ArrayList<String>(), records,
                         new ErrorRecordSink());
  }
}
