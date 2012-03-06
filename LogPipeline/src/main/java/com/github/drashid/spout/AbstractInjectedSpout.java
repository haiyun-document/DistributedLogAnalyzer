package com.github.drashid.spout;

import java.util.Map;
import com.github.drashid.inject.InjectorManager;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;

public abstract class AbstractInjectedSpout extends BaseRichSpout {

  private static final long serialVersionUID = -1L;

  public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context, SpoutOutputCollector collector) {
    InjectorManager.getInjector().injectMembers(this);
    _open(conf, context, collector);
  }

  protected abstract void _open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context, SpoutOutputCollector collector);
  
}
