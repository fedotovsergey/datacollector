/**
 * (c) 2014 StreamSets, Inc. All rights reserved. May not
 * be copied, modified, or distributed in whole or part without
 * written consent of StreamSets, Inc.
 */
package com.streamsets.pipeline.configurablestage;

import com.streamsets.pipeline.api.Stage;

import java.util.List;

public abstract class DStage<C extends Stage.Context> implements Stage<C> {
  private Stage<C> stage;

  public Stage<C> getStage() {
    return stage;
  }

  abstract Stage<C> createStage();

  @Override
  public final List<ConfigIssue> init(Info info, C context) {
    if(stage == null) {
      stage = createStage();
    }
    return stage.init(info, context);
  }

  @Override
  public final void destroy() {
    stage.destroy();
  }

}
