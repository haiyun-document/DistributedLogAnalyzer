package com.github.drashid;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import com.github.drashid.amqp.RabbitModule;
import com.github.drashid.inject.InjectorManager;
import com.github.drashid.redis.RedisConfig;
import com.github.drashid.redis.RedisModule;
import com.github.drashid.spout.RabbitSpout;
import com.github.drashid.spout.RedisSpout;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;


public class LocalRunner {

  public static class LocalRunnerModule extends AbstractModule{
    @Override
    protected void configure() {
      install(new RedisModule(new RedisConfig()));  
      install(new RabbitModule());
      requestStaticInjection(InjectorManager.class);
    }
  }

  public static void main(String[] args) {
    Guice.createInjector(new LocalRunnerModule());
    
    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("redisLogSpout", new RedisSpout());
    builder.setSpout("rabbitLogSpout", new RabbitSpout());

    Config conf = new Config();
    conf.setDebug(true);

    LocalCluster cluster = new LocalCluster();

    cluster.submitTopology("test", conf, builder.createTopology());
    Utils.sleep(100000);
    cluster.killTopology("test");
    cluster.shutdown();
  }
  
}
