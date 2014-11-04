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
package com.streamsets.pipeline.runner;

import com.streamsets.pipeline.api.StageException;

import java.util.List;

public class ObserverPipe extends Pipe {
  private final Observer observer;

  public ObserverPipe(StageRuntime stage, List<String> inputLanes, List<String> outputLanes, Observer observer) {
    super(stage, inputLanes, outputLanes);
    this.observer = observer;
  }

  @Override
  public void init() throws StageException {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void process(PipeBatch pipeBatch) throws PipelineRuntimeException {
    if (observer != null && observer.isObserving(getStage().getInfo())) {
      observer.observe(this, pipeBatch.getPipeLanesSnapshot(getInputLanes()));
    }
    for (int i = 0; i < getInputLanes().size(); i++) {
      pipeBatch.moveLane(getInputLanes().get(i), getOutputLanes().get(i));
    }
  }

}
